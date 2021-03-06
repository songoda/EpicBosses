package com.songoda.epicbosses.skills.custom;

import com.songoda.core.compatibility.CompatibleMaterial;
import com.songoda.core.compatibility.ServerVersion;
import com.songoda.epicbosses.EpicBosses;
import com.songoda.epicbosses.api.BossAPI;
import com.songoda.epicbosses.holder.ActiveBossHolder;
import com.songoda.epicbosses.managers.BossPanelManager;
import com.songoda.epicbosses.managers.BossSkillManager;
import com.songoda.epicbosses.panel.skills.custom.custom.MaterialTypeEditorPanel;
import com.songoda.epicbosses.skills.CustomSkillHandler;
import com.songoda.epicbosses.skills.Skill;
import com.songoda.epicbosses.skills.custom.cage.CageLocationData;
import com.songoda.epicbosses.skills.custom.cage.CagePlayerData;
import com.songoda.epicbosses.skills.elements.CustomCageSkillElement;
import com.songoda.epicbosses.skills.elements.SubCustomSkillElement;
import com.songoda.epicbosses.skills.interfaces.ICustomSettingAction;
import com.songoda.epicbosses.skills.interfaces.IOtherSkillDataElement;
import com.songoda.epicbosses.skills.types.CustomSkillElement;
import com.songoda.epicbosses.utils.Debug;
import com.songoda.epicbosses.utils.NumberUtils;
import com.songoda.epicbosses.utils.ObjectUtils;
import com.songoda.epicbosses.utils.ServerUtils;
import com.songoda.epicbosses.utils.itemstack.converters.MaterialConverter;
import com.songoda.epicbosses.utils.panel.base.ClickAction;
import com.songoda.epicbosses.utils.panel.base.ISubVariablePanelHandler;
import com.songoda.epicbosses.utils.time.TimeUnit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 06-Nov-18
 */
public class Cage extends CustomSkillHandler {

    private static final MaterialConverter MATERIAL_CONVERTER = new MaterialConverter();

    private static final Map<Location, CageLocationData> cageLocationDataMap = new HashMap<>();
    private static final List<UUID> playersInCage = new ArrayList<>();

    private static Method setBlockDataMethod;

    private final MaterialTypeEditorPanel flatTypeEditor, wallTypeEditor, insideTypeEditor;
    private BossPanelManager bossPanelManager;
    private EpicBosses plugin;

    public Cage(EpicBosses plugin) {
        this.plugin = plugin;
        this.bossPanelManager = plugin.getBossPanelManager();

        this.flatTypeEditor = getFlatTypeEditor();
        this.wallTypeEditor = getWallTypeEditor();
        this.insideTypeEditor = getInsideTypeEditor();
    }

    public static Map<Location, CageLocationData> getCageLocationDataMap() {
        return Cage.cageLocationDataMap;
    }

    public static List<UUID> getPlayersInCage() {
        return Cage.playersInCage;
    }

    @Override
    public boolean doesUseMultiplier() {
        return false;
    }

    @Override
    public IOtherSkillDataElement getOtherSkillData() {
        return new CustomCageSkillElement("IRON_BLOCK", "IRON_BARS", "AIR", 5);
    }

    @Override
    public List<ICustomSettingAction> getOtherSkillDataActions(Skill skill, CustomSkillElement customSkillElement) {
        List<ICustomSettingAction> clickActions = new ArrayList<>();
        ItemStack clickStack = CompatibleMaterial.STONE_PRESSURE_PLATE.getItem();
        ItemStack duration = CompatibleMaterial.CLOCK.getItem();
        ClickAction flatAction = (event -> this.flatTypeEditor.openFor((Player) event.getWhoClicked(), skill, customSkillElement));
        ClickAction wallAction = (event -> this.wallTypeEditor.openFor((Player) event.getWhoClicked(), skill, customSkillElement));
        ClickAction insideAction = (event -> this.insideTypeEditor.openFor((Player) event.getWhoClicked(), skill, customSkillElement));

        clickActions.add(BossSkillManager.createCustomSkillAction("Flat Type Editor", getFlatTypeCurrent(customSkillElement), clickStack.clone(), flatAction));
        clickActions.add(BossSkillManager.createCustomSkillAction("Wall Type Editor", getWallTypeCurrent(customSkillElement), clickStack.clone(), wallAction));
        clickActions.add(BossSkillManager.createCustomSkillAction("Inside Type Editor", getInsideTypeCurrent(customSkillElement), clickStack.clone(), insideAction));
        clickActions.add(BossSkillManager.createCustomSkillAction("Cage Duration", NumberUtils.get().formatDouble(getCurrentDuration(customSkillElement)), duration.clone(), getDurationAction(skill, customSkillElement)));

        return clickActions;
    }

    @Override
    public void castSkill(Skill skill, CustomSkillElement customSkillElement, ActiveBossHolder activeBossHolder, List<LivingEntity> nearbyEntities) {
        nearbyEntities.forEach(livingEntity -> {
            UUID uuid = livingEntity.getUniqueId();

            if (getPlayersInCage().contains(uuid)) return;

            getPlayersInCage().add(uuid);

            Location teleportLocation = getTeleportLocation(livingEntity);
            CagePlayerData cagePlayerData = new CagePlayerData(uuid);

            cagePlayerData.setBlockStateMaps(teleportLocation);
            livingEntity.teleport(teleportLocation);

            ServerUtils.get().runLater(1L, () -> setCageBlocks(cagePlayerData, customSkillElement.getCustom().getCustomCageSkillData(), skill));
            ServerUtils.get().runLater((long) TimeUnit.SECONDS.to(TimeUnit.TICK, getCurrentDuration(customSkillElement)), () -> {
                restoreCageBlocks(cagePlayerData);
                getPlayersInCage().remove(uuid);
            });
        });
    }

    private void restoreCageBlocks(CagePlayerData cagePlayerData) {
        Map<String, Queue<BlockState>> queueMap = cagePlayerData.getMapOfRestoreCages();

        restoreBlocks(queueMap.get("W"));
        restoreBlocks(queueMap.get("F"));
        restoreBlocks(queueMap.get("I"));
    }

    private void restoreBlocks(Queue<BlockState> queue) {
        queue.forEach(blockState -> {
            if (blockState == null) return;

            Location location = blockState.getLocation();
            CageLocationData cageLocationData = getCageLocationDataMap().getOrDefault(location, new CageLocationData(location, 1));
            int amountOfCages = cageLocationData.getAmountOfCages();

            if (amountOfCages == 1) {
                BlockState oldState = cageLocationData.getOldBlockState();

                if (oldState != null) {
                    if (ServerVersion.isServerVersionAtLeast(ServerVersion.V1_13)) {
                        location.getBlock().setBlockData(oldState.getBlockData());
                    } else {
                        if (setBlockDataMethod == null) {
                            try {
                                setBlockDataMethod = Block.class.getMethod("setData", byte.class);
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }

                        location.getBlock().setType(oldState.getType());
                        try {
                            setBlockDataMethod.invoke(location.getBlock(), oldState.getData().getData());
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }

                getCageLocationDataMap().remove(location);
            } else {
                cageLocationData.setAmountOfCages(amountOfCages - 1);
                getCageLocationDataMap().put(location, cageLocationData);
            }
        });
    }

    private void setCageBlocks(CagePlayerData cagePlayerData, CustomCageSkillElement cage, Skill skill) {
        Map<String, Queue<BlockState>> queueMap = cagePlayerData.getMapOfCages();

        setBlocks(queueMap.get("W"), cage.getWallType(), skill);
        setBlocks(queueMap.get("F"), cage.getFlatType(), skill);
        setBlocks(queueMap.get("I"), cage.getInsideType(), skill);
    }

    private void setBlocks(Queue<BlockState> queue, String materialType, Skill skill) {
        Material material = MATERIAL_CONVERTER.from(materialType);

        if (material == null) {
            Debug.SKILL_CAGE_INVALID_MATERIAL.debug(materialType, skill.getDisplayName());
            return;
        }

        queue.forEach(blockState -> {
            if (blockState == null) return;

            Location location = blockState.getLocation();
            CageLocationData cageLocationData = getCageLocationDataMap().getOrDefault(location, new CageLocationData(location, 0));
            int currentAmount = cageLocationData.getAmountOfCages();

            if (currentAmount == 0 || cageLocationData.getOldBlockState() == null)
                cageLocationData.setOldBlockState(blockState);

            blockState.getBlock().setType(material);
            cageLocationData.setAmountOfCages(currentAmount + 1);
            getCageLocationDataMap().put(location, cageLocationData);
        });
    }

    private Location getTeleportLocation(LivingEntity livingEntity) {
        Location currentLocation = livingEntity.getLocation();

        return currentLocation.clone().add(0.5, 0, 0.5);
    }

    private int getCurrentDuration(CustomSkillElement customSkillElement) {
        CustomCageSkillElement customCageSkillElement = customSkillElement.getCustom().getCustomCageSkillData();

        return ObjectUtils.getValue(customCageSkillElement.getDuration(), 5);
    }

    private ClickAction getDurationAction(Skill skill, CustomSkillElement customSkillElement) {
        return event -> {
            ClickType clickType = event.getClick();
            int amountToModifyBy;

            if (clickType.name().contains("RIGHT")) {
                amountToModifyBy = -1;
            } else {
                amountToModifyBy = +1;
            }

            SubCustomSkillElement subCustomSkillElement = customSkillElement.getCustom();
            CustomCageSkillElement customCageSkillElement = subCustomSkillElement.getCustomCageSkillData();
            int currentAmount = ObjectUtils.getValue(customCageSkillElement.getDuration(), 5);
            int newAmount = currentAmount + amountToModifyBy;

            if (newAmount <= 1) newAmount = 1;

            customCageSkillElement.setDuration(newAmount);
            subCustomSkillElement.setOtherSkillData(BossAPI.convertObjectToJsonObject(customCageSkillElement));
            customSkillElement.setCustom(subCustomSkillElement);
            skill.setCustomData(BossAPI.convertObjectToJsonObject(customSkillElement));
            this.plugin.getSkillsFileManager().save();
        };
    }

    private String getFlatTypeCurrent(CustomSkillElement customSkillElement) {
        CustomCageSkillElement customCageSkillElement = customSkillElement.getCustom().getCustomCageSkillData();

        return ObjectUtils.getValue(customCageSkillElement.getFlatType(), "");
    }

    private MaterialTypeEditorPanel getFlatTypeEditor() {
        return new MaterialTypeEditorPanel(this.bossPanelManager, this.bossPanelManager.getListMenu("Skills.Material"), this.plugin) {
            @Override
            public void saveSetting(Skill skill, CustomSkillElement customSkillElement, String newValue) {
                CustomCageSkillElement customCageSkillElement = customSkillElement.getCustom().getCustomCageSkillData();

                customCageSkillElement.setFlatType(newValue);
                customSkillElement.getCustom().setOtherSkillData(BossAPI.convertObjectToJsonObject(customCageSkillElement));
                skill.setCustomData(BossAPI.convertObjectToJsonObject(customSkillElement));
                Cage.this.plugin.getSkillsFileManager().save();
            }

            @Override
            public String getCurrentSetting(CustomSkillElement customSkillElement) {
                return customSkillElement.getCustom().getCustomCageSkillData().getFlatType();
            }

            @Override
            public ISubVariablePanelHandler<Skill, CustomSkillElement> getParentHolder() {
                return this.bossPanelManager.getSpecialSettingsEditorMenu();
            }
        };
    }

    private String getWallTypeCurrent(CustomSkillElement customSkillElement) {
        CustomCageSkillElement customCageSkillElement = customSkillElement.getCustom().getCustomCageSkillData();

        return ObjectUtils.getValue(customCageSkillElement.getWallType(), "");
    }

    private MaterialTypeEditorPanel getWallTypeEditor() {
        return new MaterialTypeEditorPanel(this.bossPanelManager, this.bossPanelManager.getListMenu("Skills.Material"), this.plugin) {
            @Override
            public void saveSetting(Skill skill, CustomSkillElement customSkillElement, String newValue) {
                CustomCageSkillElement customCageSkillElement = customSkillElement.getCustom().getCustomCageSkillData();

                customCageSkillElement.setWallType(newValue);
                customSkillElement.getCustom().setOtherSkillData(BossAPI.convertObjectToJsonObject(customCageSkillElement));
                skill.setCustomData(BossAPI.convertObjectToJsonObject(customSkillElement));
                Cage.this.plugin.getSkillsFileManager().save();
            }

            @Override
            public String getCurrentSetting(CustomSkillElement customSkillElement) {
                return customSkillElement.getCustom().getCustomCageSkillData().getWallType();
            }

            @Override
            public ISubVariablePanelHandler<Skill, CustomSkillElement> getParentHolder() {
                return this.bossPanelManager.getSpecialSettingsEditorMenu();
            }
        };
    }

    private String getInsideTypeCurrent(CustomSkillElement customSkillElement) {
        CustomCageSkillElement customCageSkillElement = customSkillElement.getCustom().getCustomCageSkillData();

        return ObjectUtils.getValue(customCageSkillElement.getInsideType(), "");
    }

    private MaterialTypeEditorPanel getInsideTypeEditor() {
        return new MaterialTypeEditorPanel(this.bossPanelManager, this.bossPanelManager.getListMenu("Skills.Material"), this.plugin) {
            @Override
            public void saveSetting(Skill skill, CustomSkillElement customSkillElement, String newValue) {
                CustomCageSkillElement customCageSkillElement = customSkillElement.getCustom().getCustomCageSkillData();

                customCageSkillElement.setInsideType(newValue);
                customSkillElement.getCustom().setOtherSkillData(BossAPI.convertObjectToJsonObject(customCageSkillElement));
                skill.setCustomData(BossAPI.convertObjectToJsonObject(customSkillElement));
                Cage.this.plugin.getSkillsFileManager().save();
            }

            @Override
            public String getCurrentSetting(CustomSkillElement customSkillElement) {
                return customSkillElement.getCustom().getCustomCageSkillData().getInsideType();
            }

            @Override
            public ISubVariablePanelHandler<Skill, CustomSkillElement> getParentHolder() {
                return this.bossPanelManager.getSpecialSettingsEditorMenu();
            }
        };
    }

}
