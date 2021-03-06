package com.songoda.epicbosses.managers;

import com.songoda.epicbosses.EpicBosses;
import com.songoda.epicbosses.api.BossAPI;
import com.songoda.epicbosses.autospawns.AutoSpawn;
import com.songoda.epicbosses.droptable.DropTable;
import com.songoda.epicbosses.droptable.elements.DropTableElement;
import com.songoda.epicbosses.droptable.elements.GiveTableElement;
import com.songoda.epicbosses.droptable.elements.SprayTableElement;
import com.songoda.epicbosses.entity.BossEntity;
import com.songoda.epicbosses.entity.elements.EntityStatsElement;
import com.songoda.epicbosses.entity.elements.EquipmentElement;
import com.songoda.epicbosses.entity.elements.HandsElement;
import com.songoda.epicbosses.panel.*;
import com.songoda.epicbosses.panel.additems.CustomItemsAddItemsParentPanelHandler;
import com.songoda.epicbosses.panel.autospawns.*;
import com.songoda.epicbosses.panel.bosses.*;
import com.songoda.epicbosses.panel.bosses.commands.OnDeathCommandEditor;
import com.songoda.epicbosses.panel.bosses.commands.OnSpawnCommandEditor;
import com.songoda.epicbosses.panel.bosses.equipment.BootsEditorPanel;
import com.songoda.epicbosses.panel.bosses.equipment.ChestplateEditorPanel;
import com.songoda.epicbosses.panel.bosses.equipment.HelmetEditorPanel;
import com.songoda.epicbosses.panel.bosses.equipment.LeggingsEditorPanel;
import com.songoda.epicbosses.panel.bosses.list.BossListEquipmentEditorPanel;
import com.songoda.epicbosses.panel.bosses.list.BossListStatisticEditorPanel;
import com.songoda.epicbosses.panel.bosses.list.BossListWeaponEditorPanel;
import com.songoda.epicbosses.panel.bosses.text.DeathTextEditorPanel;
import com.songoda.epicbosses.panel.bosses.text.SkillMasterMessageTextEditorPanel;
import com.songoda.epicbosses.panel.bosses.text.SpawnTextEditorPanel;
import com.songoda.epicbosses.panel.bosses.text.TauntTextEditorPanel;
import com.songoda.epicbosses.panel.bosses.weapons.MainHandEditorPanel;
import com.songoda.epicbosses.panel.bosses.weapons.OffHandEditorPanel;
import com.songoda.epicbosses.panel.droptables.DropTableTypeEditorPanel;
import com.songoda.epicbosses.panel.droptables.MainDropTableEditorPanel;
import com.songoda.epicbosses.panel.droptables.rewards.DropTableNewRewardEditorPanel;
import com.songoda.epicbosses.panel.droptables.rewards.DropTableRewardMainEditorPanel;
import com.songoda.epicbosses.panel.droptables.rewards.DropTableRewardsListEditorPanel;
import com.songoda.epicbosses.panel.droptables.types.drop.DropDropNewRewardPanel;
import com.songoda.epicbosses.panel.droptables.types.drop.DropDropRewardListPanel;
import com.songoda.epicbosses.panel.droptables.types.drop.DropDropRewardMainEditPanel;
import com.songoda.epicbosses.panel.droptables.types.drop.DropDropTableMainEditorPanel;
import com.songoda.epicbosses.panel.droptables.types.give.GiveRewardMainEditPanel;
import com.songoda.epicbosses.panel.droptables.types.give.GiveRewardPositionListPanel;
import com.songoda.epicbosses.panel.droptables.types.give.GiveRewardRewardsListPanel;
import com.songoda.epicbosses.panel.droptables.types.give.commands.GiveCommandNewRewardPanel;
import com.songoda.epicbosses.panel.droptables.types.give.commands.GiveCommandRewardListPanel;
import com.songoda.epicbosses.panel.droptables.types.give.commands.GiveCommandRewardMainEditPanel;
import com.songoda.epicbosses.panel.droptables.types.give.drops.GiveDropNewRewardPanel;
import com.songoda.epicbosses.panel.droptables.types.give.drops.GiveDropRewardListPanel;
import com.songoda.epicbosses.panel.droptables.types.give.drops.GiveDropRewardMainEditPanel;
import com.songoda.epicbosses.panel.droptables.types.give.handlers.GiveRewardEditHandler;
import com.songoda.epicbosses.panel.droptables.types.spray.SprayDropNewRewardPanel;
import com.songoda.epicbosses.panel.droptables.types.spray.SprayDropRewardListPanel;
import com.songoda.epicbosses.panel.droptables.types.spray.SprayDropRewardMainEditPanel;
import com.songoda.epicbosses.panel.droptables.types.spray.SprayDropTableMainEditorPanel;
import com.songoda.epicbosses.panel.handlers.ListMessageListEditor;
import com.songoda.epicbosses.panel.handlers.SingleMessageListEditor;
import com.songoda.epicbosses.panel.skills.MainSkillEditorPanel;
import com.songoda.epicbosses.panel.skills.SkillTypeEditorPanel;
import com.songoda.epicbosses.panel.skills.custom.CommandSkillEditorPanel;
import com.songoda.epicbosses.panel.skills.custom.CustomSkillEditorPanel;
import com.songoda.epicbosses.panel.skills.custom.GroupSkillEditorPanel;
import com.songoda.epicbosses.panel.skills.custom.PotionSkillEditorPanel;
import com.songoda.epicbosses.panel.skills.custom.commands.CommandListSkillEditorPanel;
import com.songoda.epicbosses.panel.skills.custom.commands.ModifyCommandEditorPanel;
import com.songoda.epicbosses.panel.skills.custom.custom.CustomSkillTypeEditorPanel;
import com.songoda.epicbosses.panel.skills.custom.custom.MinionSelectEditorPanel;
import com.songoda.epicbosses.panel.skills.custom.custom.SpecialSettingsEditorPanel;
import com.songoda.epicbosses.panel.skills.custom.potions.CreatePotionEffectEditorPanel;
import com.songoda.epicbosses.panel.skills.custom.potions.PotionEffectTypeEditorPanel;
import com.songoda.epicbosses.skills.Skill;
import com.songoda.epicbosses.skills.elements.SubCommandSkillElement;
import com.songoda.epicbosses.skills.types.CustomSkillElement;
import com.songoda.epicbosses.utils.ILoadable;
import com.songoda.epicbosses.utils.StringUtils;
import com.songoda.epicbosses.utils.panel.base.IPanelHandler;
import com.songoda.epicbosses.utils.panel.base.ISubSubVariablePanelHandler;
import com.songoda.epicbosses.utils.panel.base.ISubVariablePanelHandler;
import com.songoda.epicbosses.utils.panel.base.IVariablePanelHandler;
import com.songoda.epicbosses.utils.panel.builder.PanelBuilder;
import com.songoda.epicbosses.utils.potion.holder.PotionEffectHolder;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 10-Oct-18
 */
public class BossPanelManager implements ILoadable {

    private static final String HELMET_EDITOR_PATH = "HelmetEditorPanel", CHESTPLATE_EDITOR_PATH = "ChestplateEditorPanel", LEGGINGS_EDITOR_PATH = "LeggingsEditorPanel",
            BOOTS_EDITOR_PATH = "BootsEditorPanel", MAIN_HAND_EDITOR_PATH = "MainHandEditorPanel", OFF_HAND_EDITOR_PATH = "OffHandEditorPanel";
    private final EpicBosses epicBosses;
    private IPanelHandler mainMenu, customItems, bosses, autoSpawns, dropTables, customSkills, shopPanel;
    private IPanelHandler customItemAddItemsMenu;
    private ISubVariablePanelHandler<BossEntity, EntityStatsElement> equipmentEditMenu, helmetEditorMenu, chestplateEditorMenu, leggingsEditorMenu, bootsEditorMenu;
    private ISubVariablePanelHandler<BossEntity, EntityStatsElement> weaponEditMenu, offHandEditorMenu, mainHandEditorMenu;
    private ISubVariablePanelHandler<BossEntity, EntityStatsElement> statisticMainEditMenu, entityTypeEditMenu;
    private IVariablePanelHandler<BossEntity> mainBossEditMenu, dropsEditMenu, targetingEditMenu, skillsBossEditMenu, skillListBossEditMenu, commandsMainEditMenu, onSpawnCommandEditMenu,
            onDeathCommandEditMenu, mainDropsEditMenu, mainTextEditMenu, mainTauntEditMenu, onSpawnTextEditMenu, onSpawnSubTextEditMenu, onDeathTextEditMenu, onDeathSubTextEditMenu, onDeathPositionTextEditMenu,
            onTauntTextEditMenu, spawnItemEditMenu, bossShopEditMenu, bossSkillMasterMessageTextEditMenu;
    private BossListEditorPanel equipmentListEditMenu, weaponListEditMenu, statisticListEditMenu;
    private IVariablePanelHandler<Skill> mainSkillEditMenu, customMessageEditMenu, skillTypeEditMenu, potionSkillEditorPanel, commandSkillEditorPanel, groupSkillEditorPanel, customSkillEditorPanel;
    private ISubVariablePanelHandler<Skill, PotionEffectHolder> createPotionEffectMenu, potionEffectTypeEditMenu;
    private ISubVariablePanelHandler<Skill, SubCommandSkillElement> modifyCommandEditMenu, commandListSkillEditMenu;
    private ISubVariablePanelHandler<Skill, CustomSkillElement> customSkillTypeEditorMenu, specialSettingsEditorMenu, minionSelectEditorMenu;
    private IVariablePanelHandler<DropTable> mainDropTableEditMenu, dropTableTypeEditMenu;
    private ISubVariablePanelHandler<DropTable, SprayTableElement> sprayDropTableMainEditMenu;
    private DropTableRewardMainEditorPanel<SprayTableElement> sprayDropRewardMainEditPanel;
    private DropTableNewRewardEditorPanel<SprayTableElement> sprayDropNewRewardEditPanel;
    private DropTableRewardsListEditorPanel<SprayTableElement> sprayDropRewardListPanel;
    private ISubVariablePanelHandler<DropTable, GiveRewardEditHandler> giveRewardMainEditMenu, giveCommandRewardListPanel, giveCommandNewRewardPanel;
    private ISubSubVariablePanelHandler<DropTable, GiveRewardEditHandler, String> giveCommandRewardMainEditMenu;
    private ISubSubVariablePanelHandler<DropTable, GiveTableElement, String> giveRewardRewardsListMenu;
    private ISubVariablePanelHandler<DropTable, GiveTableElement> giveRewardPositionListMenu;
    private DropTableRewardMainEditorPanel<GiveRewardEditHandler> giveDropRewardMainEditPanel;
    private DropTableNewRewardEditorPanel<GiveRewardEditHandler> giveDropNewRewardEditPanel;
    private DropTableRewardsListEditorPanel<GiveRewardEditHandler> giveDropRewardListPanel;
    private ISubVariablePanelHandler<DropTable, DropTableElement> dropDropTableMainEditMenu;
    private DropTableRewardMainEditorPanel<DropTableElement> dropDropRewardMainEditPanel;
    private DropTableNewRewardEditorPanel<DropTableElement> dropDropNewRewardEditPanel;
    private DropTableRewardsListEditorPanel<DropTableElement> dropDropRewardListPanel;
    private IVariablePanelHandler<AutoSpawn> mainAutoSpawnEditPanel, autoSpawnEntitiesEditPanel, autoSpawnSpecialSettingsEditorPanel, autoSpawnTypeEditorPanel, autoSpawnCustomSettingsEditorPanel,
            autoSpawnMessageEditorPanel;
    private PanelBuilder addItemsBuilder;

    public BossPanelManager(EpicBosses epicBosses) {
        this.epicBosses = epicBosses;
    }

    @Override
    public void load() {
        loadMainMenu();
        loadShopMenu();

        loadAutoSpawnsMenu();
        loadCustomBossesMenu();
        loadCustomItemsMenu();
        loadCustomSkillsMenu();
        loadDropTableMenu();

        loadAddItemsMenu();
        loadMainEditMenu();
        loadDropsEditMenu();
        loadEditorListMenus();
        loadTargetingEditMenu();
        loadSkillsEditMenu();
        loadStatEditMenu();
        loadCommandEditMenus();
        loadTextEditMenus();
        loadEquipmentEditMenu();
        loadWeaponEditMenu();
        loadEquipmentEditMenus();

        loadSkillEditMenus();
        loadDropTableEditMenus();
        loadAutoSpawnEditMenus();
    }

    public void reload() {
        reloadMainMenu();
        reloadShopMenu();

        reloadAutoSpawnsMenu();
        reloadCustomBosses();
        reloadCustomItems();
        reloadCustomSkills();
        reloadDropTable();

        reloadAddItemsMenu();
        reloadMainEditMenu();
        reloadDropsEditMenu();
        reloadEditorListMenus();
        reloadTargetingEditMenu();
        reloadSkillsEditMenu();
        reloadStatEditMenu();
        reloadCommandEditMenus();
        reloadTextEditMenus();
        reloadEquipmentEditMenu();
        reloadWeaponEditMenu();
        reloadEquipmentEditMenus();

        reloadSkillEditMenus();
        reloadDropTableEditMenus();
        reloadAutoSpawnEditMenus();
    }

    public int isItemStackUsed(String name) {
        Collection<BossEntity> values = this.epicBosses.getBossEntityContainer().getData().values();
        int timesUsed = 0;

        for (BossEntity bossEntity : values) {
            if (bossEntity != null) {
                if (bossEntity.getSpawnItem() != null) {
                    if (bossEntity.getSpawnItem().equalsIgnoreCase(name)) timesUsed += 1;
                }

                if (bossEntity.getEntityStats() != null) {
                    List<EntityStatsElement> entityStatsElements = bossEntity.getEntityStats();

                    for (EntityStatsElement entityStatsElement : entityStatsElements) {
                        EquipmentElement equipmentElement = entityStatsElement.getEquipment();
                        HandsElement handsElement = entityStatsElement.getHands();

                        if (handsElement.getMainHand().equalsIgnoreCase(name)) timesUsed += 1;
                        if (handsElement.getOffHand().equalsIgnoreCase(name)) timesUsed += 1;
                        if (equipmentElement.getHelmet().equalsIgnoreCase(name)) timesUsed += 1;
                        if (equipmentElement.getChestplate().equalsIgnoreCase(name)) timesUsed += 1;
                        if (equipmentElement.getLeggings().equalsIgnoreCase(name)) timesUsed += 1;
                        if (equipmentElement.getBoots().equalsIgnoreCase(name)) timesUsed += 1;
                    }
                }
            }
        }

        return timesUsed;
    }

    //---------------------------------------------
    //
    //  G E N E R A L   L I S T   P A N E L
    //
    //---------------------------------------------

    public PanelBuilder getListMenu(String path) {
        Map<String, String> replaceMap = new HashMap<>();
        String finalPath = getPath(path);
        String value = this.epicBosses.getDisplay().getString(finalPath);

        replaceMap.put("{panelName}", StringUtils.get().translateColor(value));

        return new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("ListPanel"), replaceMap);
    }

    //---------------------------------------------
    //
    //  A U T O   S P A W N   E D I T   P A N E L S
    //
    //---------------------------------------------

    private void loadAutoSpawnEditMenus() {
        FileConfiguration editor = this.epicBosses.getEditor();
        PanelBuilder panelBuilder = new PanelBuilder(editor.getConfigurationSection("MainAutoSpawnEditMenu"));
        PanelBuilder panelBuilder1 = new PanelBuilder(editor.getConfigurationSection("AutoSpawnEntitiesEditMenu"));
        PanelBuilder panelBuilder2 = new PanelBuilder(editor.getConfigurationSection("AutoSpawnCustomSettingsEditMenu"));
        PanelBuilder panelBuilder3 = new PanelBuilder(editor.getConfigurationSection("AutoSpawnSpecialSettingsEditMenu"));
        PanelBuilder panelBuilder4 = new PanelBuilder(editor.getConfigurationSection("AutoSpawnTypeEditMenu"));

        this.mainAutoSpawnEditPanel = new MainAutoSpawnEditorPanel(this, panelBuilder, this.epicBosses);
        this.autoSpawnEntitiesEditPanel = new AutoSpawnEntitiesEditorPanel(this, panelBuilder1, this.epicBosses);
        this.autoSpawnCustomSettingsEditorPanel = new AutoSpawnCustomSettingsEditorPanel(this, panelBuilder2, this.epicBosses);
        this.autoSpawnSpecialSettingsEditorPanel = new AutoSpawnSpecialSettingsEditorPanel(this, panelBuilder3, this.epicBosses);
        this.autoSpawnMessageEditorPanel = new AutoSpawnSpawnMessageEditorPanel(this, getListMenu("AutoSpawns.SpawnMessage"), this.epicBosses);
        this.autoSpawnTypeEditorPanel = new AutoSpawnTypeEditorPanel(this, panelBuilder4, this.epicBosses);
    }

    private void reloadAutoSpawnEditMenus() {
        FileConfiguration editor = this.epicBosses.getEditor();
        PanelBuilder panelBuilder = new PanelBuilder(editor.getConfigurationSection("MainAutoSpawnEditMenu"));
        PanelBuilder panelBuilder1 = new PanelBuilder(editor.getConfigurationSection("AutoSpawnEntitiesEditMenu"));
        PanelBuilder panelBuilder2 = new PanelBuilder(editor.getConfigurationSection("AutoSpawnCustomSettingsEditMenu"));
        PanelBuilder panelBuilder3 = new PanelBuilder(editor.getConfigurationSection("AutoSpawnSpecialSettingsEditMenu"));
        PanelBuilder panelBuilder4 = new PanelBuilder(editor.getConfigurationSection("AutoSpawnTypeEditMenu"));

        this.mainAutoSpawnEditPanel.initializePanel(panelBuilder);
        this.autoSpawnEntitiesEditPanel.initializePanel(panelBuilder1);
        this.autoSpawnCustomSettingsEditorPanel.initializePanel(panelBuilder2);
        this.autoSpawnSpecialSettingsEditorPanel.initializePanel(panelBuilder3);
        this.autoSpawnMessageEditorPanel.initializePanel(getListMenu("AutoSpawns.SpawnMessage"));
        this.autoSpawnTypeEditorPanel.initializePanel(panelBuilder4);
    }

    //---------------------------------------------
    //
    //  D R O P   T A B L E   E D I T   P A N E L S
    //
    //---------------------------------------------

    private void loadDropTableEditMenus() {
        FileConfiguration editor = this.epicBosses.getEditor();
        PanelBuilder panelBuilder = new PanelBuilder(editor.getConfigurationSection("DropTableMainEditorPanel"));
        PanelBuilder panelBuilder1 = new PanelBuilder(editor.getConfigurationSection("DropTableTypeEditorPanel"));
        PanelBuilder panelBuilder2 = new PanelBuilder(editor.getConfigurationSection("SprayDropTableMainEditMenu"));

        PanelBuilder panelBuilder3 = new PanelBuilder(editor.getConfigurationSection("DropTableRewardsListEditMenu"));
        PanelBuilder panelBuilder4 = new PanelBuilder(editor.getConfigurationSection("DropTableNewRewardEditMenu"));
        PanelBuilder panelBuilder5 = new PanelBuilder(editor.getConfigurationSection("DropTableRewardMainEditMenu"));

        PanelBuilder panelBuilder6 = new PanelBuilder(editor.getConfigurationSection("DropDropTableMainEditMenu"));

        PanelBuilder panelBuilder10 = new PanelBuilder(editor.getConfigurationSection("GiveRewardPositionListMenu"));
        PanelBuilder panelBuilder11 = new PanelBuilder(editor.getConfigurationSection("GiveRewardRewardsListMenu"));
        PanelBuilder panelBuilder12 = new PanelBuilder(editor.getConfigurationSection("GiveRewardMainEditMenu"));

        this.mainDropTableEditMenu = new MainDropTableEditorPanel(this, panelBuilder);
        this.dropTableTypeEditMenu = new DropTableTypeEditorPanel(this, panelBuilder1, this.epicBosses);

        this.sprayDropTableMainEditMenu = new SprayDropTableMainEditorPanel(this, panelBuilder2, this.epicBosses);
        this.sprayDropNewRewardEditPanel = new SprayDropNewRewardPanel(this, panelBuilder4, this.epicBosses);
        this.sprayDropRewardListPanel = new SprayDropRewardListPanel(this, panelBuilder3, this.epicBosses);
        this.sprayDropRewardMainEditPanel = new SprayDropRewardMainEditPanel(this, panelBuilder5, this.epicBosses);

        this.dropDropTableMainEditMenu = new DropDropTableMainEditorPanel(this, panelBuilder6, this.epicBosses);
        this.dropDropNewRewardEditPanel = new DropDropNewRewardPanel(this, panelBuilder4, this.epicBosses);
        this.dropDropRewardListPanel = new DropDropRewardListPanel(this, panelBuilder3, this.epicBosses);
        this.dropDropRewardMainEditPanel = new DropDropRewardMainEditPanel(this, panelBuilder5, this.epicBosses);

        this.giveRewardPositionListMenu = new GiveRewardPositionListPanel(this, panelBuilder10, this.epicBosses);
        this.giveRewardRewardsListMenu = new GiveRewardRewardsListPanel(this, panelBuilder11, this.epicBosses);
        this.giveRewardMainEditMenu = new GiveRewardMainEditPanel(this, panelBuilder12, this.epicBosses);

        this.giveDropNewRewardEditPanel = new GiveDropNewRewardPanel(this, panelBuilder4, this.epicBosses);
        this.giveDropRewardListPanel = new GiveDropRewardListPanel(this, panelBuilder3, this.epicBosses);
        this.giveDropRewardMainEditPanel = new GiveDropRewardMainEditPanel(this, panelBuilder5, this.epicBosses);
        this.giveCommandNewRewardPanel = new GiveCommandNewRewardPanel(this, panelBuilder4, this.epicBosses);
        this.giveCommandRewardListPanel = new GiveCommandRewardListPanel(this, panelBuilder3, this.epicBosses);
        this.giveCommandRewardMainEditMenu = new GiveCommandRewardMainEditPanel(this, panelBuilder5, this.epicBosses);
    }

    private void reloadDropTableEditMenus() {
        FileConfiguration editor = this.epicBosses.getEditor();
        PanelBuilder panelBuilder = new PanelBuilder(editor.getConfigurationSection("DropTableMainEditorPanel"));
        PanelBuilder panelBuilder1 = new PanelBuilder(editor.getConfigurationSection("DropTableTypeEditorPanel"));
        PanelBuilder panelBuilder2 = new PanelBuilder(editor.getConfigurationSection("SprayDropTableMainEditMenu"));

        PanelBuilder panelBuilder3 = new PanelBuilder(editor.getConfigurationSection("DropTableRewardsListEditMenu"));
        PanelBuilder panelBuilder4 = new PanelBuilder(editor.getConfigurationSection("DropTableNewRewardEditMenu"));
        PanelBuilder panelBuilder5 = new PanelBuilder(editor.getConfigurationSection("DropTableRewardMainEditMenu"));

        PanelBuilder panelBuilder6 = new PanelBuilder(editor.getConfigurationSection("DropDropTableMainEditMenu"));

        PanelBuilder panelBuilder10 = new PanelBuilder(editor.getConfigurationSection("GiveRewardPositionListMenu"));
        PanelBuilder panelBuilder11 = new PanelBuilder(editor.getConfigurationSection("GiveRewardRewardsListMenu"));
        PanelBuilder panelBuilder12 = new PanelBuilder(editor.getConfigurationSection("GiveRewardMainEditMenu"));

        this.mainDropTableEditMenu.initializePanel(panelBuilder);
        this.dropTableTypeEditMenu.initializePanel(panelBuilder1);

        this.sprayDropTableMainEditMenu.initializePanel(panelBuilder2);
        this.sprayDropNewRewardEditPanel.initializePanel(panelBuilder4);
        this.sprayDropRewardListPanel.initializePanel(panelBuilder5);
        this.sprayDropRewardMainEditPanel.initializePanel(panelBuilder3);

        this.dropDropTableMainEditMenu.initializePanel(panelBuilder6);
        this.dropDropNewRewardEditPanel.initializePanel(panelBuilder4);
        this.dropDropRewardListPanel.initializePanel(panelBuilder5);
        this.dropDropRewardMainEditPanel.initializePanel(panelBuilder3);

        this.giveRewardPositionListMenu.initializePanel(panelBuilder10);
        this.giveRewardPositionListMenu.initializePanel(panelBuilder11);
        this.giveRewardMainEditMenu.initializePanel(panelBuilder12);
        this.giveDropNewRewardEditPanel.initializePanel(panelBuilder4);
        this.giveDropRewardListPanel.initializePanel(panelBuilder5);
        this.giveDropRewardMainEditPanel.initializePanel(panelBuilder3);
        this.giveCommandNewRewardPanel.initializePanel(panelBuilder4);
        this.giveCommandRewardListPanel.initializePanel(panelBuilder5);
        this.giveCommandRewardMainEditMenu.initializePanel(panelBuilder3);
    }

    //---------------------------------------------
    //
    //  S K I L L   E D I T   P A N E L S
    //
    //---------------------------------------------

    private void loadSkillEditMenus() {
        FileConfiguration editor = this.epicBosses.getEditor();
        PanelBuilder panelBuilder = new PanelBuilder(editor.getConfigurationSection("SkillEditorPanel"));
        PanelBuilder panelBuilder1 = new PanelBuilder(editor.getConfigurationSection("SkillTypeEditorPanel"));
        PanelBuilder panelBuilder2 = new PanelBuilder(editor.getConfigurationSection("PotionSkillEditorPanel"));
        PanelBuilder panelBuilder3 = new PanelBuilder(editor.getConfigurationSection("CreatePotionEffectEditorPanel"));
        PanelBuilder panelBuilder4 = new PanelBuilder(editor.getConfigurationSection("CommandSkillEditorPanel"));
        PanelBuilder panelBuilder5 = new PanelBuilder(editor.getConfigurationSection("ModifyCommandEditorPanel"));
        PanelBuilder panelBuilder6 = new PanelBuilder(editor.getConfigurationSection("CustomSkillEditorPanel"));
        PanelBuilder panelBuilder7 = new PanelBuilder(editor.getConfigurationSection("CustomSkillTypeEditorPanel"));
        PanelBuilder panelBuilder8 = new PanelBuilder(editor.getConfigurationSection("SpecialSettingsEditorPanel"));

        this.mainSkillEditMenu = new MainSkillEditorPanel(this, panelBuilder, this.epicBosses);
        this.customMessageEditMenu = new SingleMessageListEditor<Skill>(this, getListMenu("Skills.MainEdit"), this.epicBosses) {

            @Override
            public String getCurrent(Skill object) {
                return object.getCustomMessage();
            }

            @Override
            public void updateMessage(Skill object, String newPath) {
                object.setCustomMessage(newPath);
                BossPanelManager.this.epicBosses.getSkillsFileManager().save();
            }

            @Override
            public IVariablePanelHandler<Skill> getParentHolder() {
                return getMainSkillEditMenu();
            }

            @Override
            public String getName(Skill object) {
                return BossAPI.getSkillName(object);
            }
        };
        this.skillTypeEditMenu = new SkillTypeEditorPanel(this, panelBuilder1, this.epicBosses);
        this.potionSkillEditorPanel = new PotionSkillEditorPanel(this, panelBuilder2, this.epicBosses);
        this.createPotionEffectMenu = new CreatePotionEffectEditorPanel(this, panelBuilder3, this.epicBosses);
        this.potionEffectTypeEditMenu = new PotionEffectTypeEditorPanel(this, getListMenu("Skills.CreatePotion"), this.epicBosses);
        this.commandSkillEditorPanel = new CommandSkillEditorPanel(this, panelBuilder4, this.epicBosses);
        this.modifyCommandEditMenu = new ModifyCommandEditorPanel(this, panelBuilder5, this.epicBosses);
        this.commandListSkillEditMenu = new CommandListSkillEditorPanel(this, getListMenu("Skills.CommandList"), this.epicBosses);
        this.groupSkillEditorPanel = new GroupSkillEditorPanel(this, getListMenu("Skills.Group"), this.epicBosses);
        this.customSkillEditorPanel = new CustomSkillEditorPanel(this, panelBuilder6, this.epicBosses);
        this.customSkillTypeEditorMenu = new CustomSkillTypeEditorPanel(this, panelBuilder7, this.epicBosses);
        this.specialSettingsEditorMenu = new SpecialSettingsEditorPanel(this, panelBuilder8, this.epicBosses);
        this.minionSelectEditorMenu = new MinionSelectEditorPanel(this, getListMenu("Skills.MinionList"), this.epicBosses);
    }

    private void reloadSkillEditMenus() {
        FileConfiguration editor = this.epicBosses.getEditor();
        PanelBuilder panelBuilder = new PanelBuilder(editor.getConfigurationSection("SkillEditorPanel"));
        PanelBuilder panelBuilder1 = new PanelBuilder(editor.getConfigurationSection("SkillTypeEditorPanel"));
        PanelBuilder panelBuilder2 = new PanelBuilder(editor.getConfigurationSection("PotionSkillEditorPanel"));
        PanelBuilder panelBuilder3 = new PanelBuilder(editor.getConfigurationSection("CreatePotionEffectEditorPanel"));
        PanelBuilder panelBuilder4 = new PanelBuilder(editor.getConfigurationSection("CommandSkillEditorPanel"));
        PanelBuilder panelBuilder5 = new PanelBuilder(editor.getConfigurationSection("ModifyCommandEditorPanel"));
        PanelBuilder panelBuilder6 = new PanelBuilder(editor.getConfigurationSection("CustomSkillEditorPanel"));
        PanelBuilder panelBuilder7 = new PanelBuilder(editor.getConfigurationSection("CustomSkillTypeEditorPanel"));
        PanelBuilder panelBuilder8 = new PanelBuilder(editor.getConfigurationSection("SpecialSettingsEditorPanel"));

        this.mainSkillEditMenu.initializePanel(panelBuilder);
        this.customMessageEditMenu.initializePanel(getListMenu("Skills.MainEdit"));
        this.skillTypeEditMenu.initializePanel(panelBuilder1);
        this.potionSkillEditorPanel.initializePanel(panelBuilder2);
        this.createPotionEffectMenu.initializePanel(panelBuilder3);
        this.potionEffectTypeEditMenu.initializePanel(getListMenu("Skills.CreatePotion"));
        this.commandSkillEditorPanel.initializePanel(panelBuilder4);
        this.modifyCommandEditMenu.initializePanel(panelBuilder5);
        this.commandListSkillEditMenu.initializePanel(getListMenu("Skills.CommandList"));
        this.groupSkillEditorPanel.initializePanel(getListMenu("Skills.Group"));
        this.customSkillEditorPanel.initializePanel(panelBuilder6);
        this.customSkillTypeEditorMenu.initializePanel(panelBuilder7);
        this.specialSettingsEditorMenu.initializePanel(panelBuilder8);
        this.minionSelectEditorMenu.initializePanel(getListMenu("Skills.MinionList"));
    }

    //---------------------------------------------
    //
    //  T E X T   E D I T   P A N E L S
    //
    //---------------------------------------------

    private void loadTextEditMenus() {
        FileConfiguration editor = this.epicBosses.getEditor();
        PanelBuilder panelBuilder = new PanelBuilder(editor.getConfigurationSection("TextEditorMainPanel"));
        PanelBuilder panelBuilder1 = new PanelBuilder(editor.getConfigurationSection("SpawnTextEditorPanel"));
        PanelBuilder panelBuilder2 = new PanelBuilder(editor.getConfigurationSection("DeathTextEditorPanel"));
        PanelBuilder panelBuilder3 = new PanelBuilder(editor.getConfigurationSection("TauntEditorPanel"));

        this.mainTextEditMenu = new TextMainEditorPanel(this, panelBuilder);
        this.bossSkillMasterMessageTextEditMenu = new SkillMasterMessageTextEditorPanel(this, getListMenu("Boss.Text"), this.epicBosses);
        this.onSpawnSubTextEditMenu = new SpawnTextEditorPanel(this, panelBuilder1, this.epicBosses);
        this.onDeathSubTextEditMenu = new DeathTextEditorPanel(this, panelBuilder2, this.epicBosses);
        this.mainTauntEditMenu = new TauntTextEditorPanel(this, panelBuilder3, this.epicBosses);
        this.onSpawnTextEditMenu = new SingleMessageListEditor<BossEntity>(this, getListMenu("Boss.Text"), this.epicBosses) {
            @Override
            public String getCurrent(BossEntity bossEntity) {
                return bossEntity.getMessages().getOnSpawn().getMessage();
            }

            @Override
            public void updateMessage(BossEntity bossEntity, String newPath) {
                bossEntity.getMessages().getOnSpawn().setMessage(newPath);
            }

            @Override
            public IVariablePanelHandler<BossEntity> getParentHolder() {
                return getOnSpawnSubTextEditMenu();
            }

            @Override
            public String getName(BossEntity object) {
                return BossAPI.getBossEntityName(object);
            }
        };
        this.onDeathTextEditMenu = new SingleMessageListEditor<BossEntity>(this, getListMenu("Boss.Text"), this.epicBosses) {
            @Override
            public String getCurrent(BossEntity bossEntity) {
                return bossEntity.getMessages().getOnDeath().getMessage();
            }

            @Override
            public void updateMessage(BossEntity bossEntity, String newPath) {
                bossEntity.getMessages().getOnDeath().setMessage(newPath);
            }

            @Override
            public IVariablePanelHandler<BossEntity> getParentHolder() {
                return getOnDeathSubTextEditMenu();
            }

            @Override
            public String getName(BossEntity object) {
                return BossAPI.getBossEntityName(object);
            }
        };
        this.onDeathPositionTextEditMenu = new SingleMessageListEditor<BossEntity>(this, getListMenu("Boss.Text"), this.epicBosses) {
            @Override
            public String getCurrent(BossEntity bossEntity) {
                return bossEntity.getMessages().getOnDeath().getPositionMessage();
            }

            @Override
            public void updateMessage(BossEntity bossEntity, String newPath) {
                bossEntity.getMessages().getOnDeath().setPositionMessage(newPath);
            }

            @Override
            public IVariablePanelHandler<BossEntity> getParentHolder() {
                return getOnDeathSubTextEditMenu();
            }

            @Override
            public String getName(BossEntity object) {
                return BossAPI.getBossEntityName(object);
            }
        };
        this.onTauntTextEditMenu = new ListMessageListEditor<BossEntity>(this, getListMenu("Boss.Text"), this.epicBosses) {
            @Override
            public List<String> getCurrent(BossEntity bossEntity) {
                return bossEntity.getMessages().getTaunts().getTaunts();
            }

            @Override
            public void updateMessage(BossEntity bossEntity, String modifiedValue) {
                List<String> current = getCurrent(bossEntity);

                if (current.contains(modifiedValue)) {
                    current.remove(modifiedValue);
                } else {
                    current.add(modifiedValue);
                }

                bossEntity.getMessages().getTaunts().setTaunts(current);
            }

            @Override
            public IVariablePanelHandler<BossEntity> getParentHolder() {
                return getMainTauntEditMenu();
            }

            @Override
            public String getName(BossEntity object) {
                return BossAPI.getBossEntityName(object);
            }
        };
    }

    private void reloadTextEditMenus() {
        FileConfiguration editor = this.epicBosses.getEditor();
        PanelBuilder panelBuilder = new PanelBuilder(editor.getConfigurationSection("TextEditorMainPanel"));
        PanelBuilder panelBuilder1 = new PanelBuilder(editor.getConfigurationSection("SpawnTextEditorPanel"));
        PanelBuilder panelBuilder2 = new PanelBuilder(editor.getConfigurationSection("DeathTextEditorPanel"));
        PanelBuilder panelBuilder3 = new PanelBuilder(editor.getConfigurationSection("TauntEditorPanel"));

        this.mainTextEditMenu.initializePanel(panelBuilder);
        this.bossSkillMasterMessageTextEditMenu.initializePanel(getListMenu("Boss.Text"));
        this.onSpawnSubTextEditMenu.initializePanel(panelBuilder1);
        this.onDeathSubTextEditMenu.initializePanel(panelBuilder2);
        this.mainTauntEditMenu.initializePanel(panelBuilder3);
        this.onSpawnTextEditMenu.initializePanel(getListMenu("Boss.Text"));
        this.onDeathTextEditMenu.initializePanel(getListMenu("Boss.Text"));
        this.onDeathPositionTextEditMenu.initializePanel(getListMenu("Boss.Text"));
        this.onTauntTextEditMenu.initializePanel(getListMenu("Boss.Text"));
    }

    //---------------------------------------------
    //
    //  C O M M A N D S   E D I T   P A N E L S
    //
    //---------------------------------------------

    private void loadCommandEditMenus() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("CommandsEditorPanel"));

        this.commandsMainEditMenu = new CommandsMainEditorPanel(this, panelBuilder);
        this.onSpawnCommandEditMenu = new OnSpawnCommandEditor(this, getListMenu("Boss.Commands"), this.epicBosses);
        this.onDeathCommandEditMenu = new OnDeathCommandEditor(this, getListMenu("Boss.Commands"), this.epicBosses);
    }

    private void reloadCommandEditMenus() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("CommandsEditorPanel"));

        this.commandsMainEditMenu.initializePanel(panelBuilder);
        this.onSpawnCommandEditMenu.initializePanel(getListMenu("Boss.Commands"));
        this.onDeathCommandEditMenu.initializePanel(getListMenu("Boss.Commands"));
    }

    //---------------------------------------------
    //
    //  E Q U I P M E N T   E D I T   P A N E L S
    //
    //---------------------------------------------

    private void loadEquipmentEditMenus() {
        FileConfiguration editor = this.epicBosses.getEditor();

        this.spawnItemEditMenu = new SpawnItemEditorPanel(this, new PanelBuilder(editor.getConfigurationSection("SpawnItemEditorPanel")), this.epicBosses);

        this.helmetEditorMenu = new HelmetEditorPanel(this, editor.getConfigurationSection(HELMET_EDITOR_PATH), this.epicBosses);
        this.chestplateEditorMenu = new ChestplateEditorPanel(this, editor.getConfigurationSection(CHESTPLATE_EDITOR_PATH), this.epicBosses);
        this.leggingsEditorMenu = new LeggingsEditorPanel(this, editor.getConfigurationSection(LEGGINGS_EDITOR_PATH), this.epicBosses);
        this.bootsEditorMenu = new BootsEditorPanel(this, editor.getConfigurationSection(BOOTS_EDITOR_PATH), this.epicBosses);

        this.mainHandEditorMenu = new MainHandEditorPanel(this, editor.getConfigurationSection(MAIN_HAND_EDITOR_PATH), this.epicBosses);
        this.offHandEditorMenu = new OffHandEditorPanel(this, editor.getConfigurationSection(OFF_HAND_EDITOR_PATH), this.epicBosses);
    }

    private void reloadEquipmentEditMenus() {
        FileConfiguration editor = this.epicBosses.getEditor();

        this.spawnItemEditMenu.initializePanel(new PanelBuilder(editor.getConfigurationSection("SpawnItemEditorPanel")));

        this.helmetEditorMenu.initializePanel(new PanelBuilder(editor.getConfigurationSection(HELMET_EDITOR_PATH)));
        this.chestplateEditorMenu.initializePanel(new PanelBuilder(editor.getConfigurationSection(CHESTPLATE_EDITOR_PATH)));
        this.leggingsEditorMenu.initializePanel(new PanelBuilder(editor.getConfigurationSection(LEGGINGS_EDITOR_PATH)));
        this.bootsEditorMenu.initializePanel(new PanelBuilder(editor.getConfigurationSection(BOOTS_EDITOR_PATH)));

        this.mainHandEditorMenu.initializePanel(new PanelBuilder(editor.getConfigurationSection(MAIN_HAND_EDITOR_PATH)));
        this.offHandEditorMenu.initializePanel(new PanelBuilder(editor.getConfigurationSection(OFF_HAND_EDITOR_PATH)));
    }

    //---------------------------------------------
    //
    //  L I S T   E D I T   P A N E LS
    //
    //---------------------------------------------

    private void loadEditorListMenus() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("BossListEditorPanel"));

        this.equipmentListEditMenu = new BossListEquipmentEditorPanel(this, panelBuilder.cloneBuilder(), this.epicBosses);
        this.weaponListEditMenu = new BossListWeaponEditorPanel(this, panelBuilder.cloneBuilder(), this.epicBosses);
        this.statisticListEditMenu = new BossListStatisticEditorPanel(this, panelBuilder.cloneBuilder(), this.epicBosses);
    }

    private void reloadEditorListMenus() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("BossListEditorPanel"));

        this.equipmentListEditMenu.initializePanel(panelBuilder.cloneBuilder());
        this.weaponListEditMenu.initializePanel(panelBuilder.cloneBuilder());
        this.statisticListEditMenu.initializePanel(panelBuilder.cloneBuilder());
    }

    //---------------------------------------------
    //
    //  S K I L L S   E D I T   P A N E L
    //
    //---------------------------------------------

    private void loadStatEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("StatisticsMainEditorPanel"));

        this.statisticMainEditMenu = new StatisticMainEditorPanel(this, panelBuilder, this.epicBosses);
        this.entityTypeEditMenu = new EntityTypeEditorPanel(this, getListMenu("Boss.EntityType"), this.epicBosses);
    }

    private void reloadStatEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("StatisticsMainEditorPanel"));

        this.statisticMainEditMenu.initializePanel(panelBuilder);
        this.entityTypeEditMenu.initializePanel(getListMenu("Boss.EntityType"));
    }

    //---------------------------------------------
    //
    //  S K I L L S   E D I T   P A N E L
    //
    //---------------------------------------------

    private void loadSkillsEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("SkillMainEditorPanel"));

        this.skillsBossEditMenu = new SkillMainEditorPanel(this, panelBuilder, this.epicBosses);
        this.skillListBossEditMenu = new SkillListEditorPanel(this, getListMenu("Boss.Skills"), this.epicBosses);
    }

    private void reloadSkillsEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("SkillMainEditorPanel"));

        this.skillsBossEditMenu.initializePanel(panelBuilder);
        this.skillListBossEditMenu.initializePanel(getListMenu("Boss.Skills"));
    }

    //---------------------------------------------
    //
    //  E Q U I P M E N T   E D I T   P A N E L
    //
    //---------------------------------------------

    private void loadWeaponEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("WeaponEditorPanel"));

        this.weaponEditMenu = new WeaponsEditorPanel(this, panelBuilder);
    }

    private void reloadWeaponEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("WeaponEditorPanel"));

        this.weaponEditMenu.initializePanel(panelBuilder);
    }

    //---------------------------------------------
    //
    //  E Q U I P M E N T   E D I T   P A N E L
    //
    //---------------------------------------------

    private void loadEquipmentEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("EquipmentEditorPanel"));

        this.equipmentEditMenu = new EquipmentEditorPanel(this, panelBuilder);
    }

    private void reloadEquipmentEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("EquipmentEditorPanel"));

        this.equipmentEditMenu.initializePanel(panelBuilder);
    }

    //---------------------------------------------
    //
    //  D R O P S   E D I T   P A N E L
    //
    //---------------------------------------------

    private void loadDropsEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("DropsEditorPanel"));
        PanelBuilder panelBuilder1 = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("DropsMainEditorPanel"));

        this.mainDropsEditMenu = new DropsMainEditorPanel(this, panelBuilder1, this.epicBosses);
        this.dropsEditMenu = new DropsEditorPanel(this, panelBuilder, this.epicBosses);
    }

    private void reloadDropsEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("DropsEditorPanel"));
        PanelBuilder panelBuilder1 = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("DropsMainEditorPanel"));

        this.mainDropsEditMenu.initializePanel(panelBuilder1);
        this.dropsEditMenu.initializePanel(panelBuilder);
    }

    //---------------------------------------------
    //
    //  T A R G E T I N G   E D I T   P A N E L
    //
    //---------------------------------------------

    private void loadTargetingEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("TargetingPanel"));

        this.targetingEditMenu = new TargetingEditorPanel(this, panelBuilder, this.epicBosses);
    }

    private void reloadTargetingEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("TargetingPanel"));

        this.targetingEditMenu.initializePanel(panelBuilder);
    }

    //---------------------------------------------
    //
    //  M A I N   E D I T   P A N E L
    //
    //---------------------------------------------

    private void loadMainEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("MainEditorPanel"));

        this.mainBossEditMenu = new MainBossEditPanel(this, panelBuilder, this.epicBosses);
    }

    private void reloadMainEditMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("MainEditorPanel"));

        this.mainBossEditMenu.initializePanel(panelBuilder);
    }

    //---------------------------------------------
    //
    //  A D D   I T E M S   P A N E L
    //
    //---------------------------------------------

    private void loadAddItemsMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("AddItemsMenu"));

        this.addItemsBuilder = panelBuilder.cloneBuilder();
        this.customItemAddItemsMenu = new AddItemsPanel(this, panelBuilder.cloneBuilder(), this.epicBosses, new CustomItemsAddItemsParentPanelHandler(this));
    }

    private void reloadAddItemsMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("AddItemsMenu"));

        this.addItemsBuilder = panelBuilder.cloneBuilder();
        this.customItemAddItemsMenu.initializePanel(panelBuilder.cloneBuilder());
    }

    //---------------------------------------------
    //
    //  S H O P   P A N E L
    //
    //---------------------------------------------

    private void loadShopMenu() {
        this.shopPanel = new ShopPanel(this, new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("ShopListPanel")), this.epicBosses);
        this.bossShopEditMenu = new BossShopEditorPanel(this, new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("BossShopEditorPanel")), this.epicBosses);
    }

    private void reloadShopMenu() {
        this.shopPanel.initializePanel(new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("ShopListPanel")));
        this.bossShopEditMenu.initializePanel(new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("BossShopEditorPanel")));
    }

    //---------------------------------------------
    //
    //  M A I N   M E N U   P A N E L
    //
    //---------------------------------------------

    private void loadMainMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("MainMenu"));

        this.mainMenu = new MainMenuPanel(this, panelBuilder);
    }

    private void reloadMainMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("MainMenu"));

        this.mainMenu.initializePanel(panelBuilder);
    }

    //---------------------------------------------
    //
    //  A U T O   S P A W N S   P A N E L
    //
    //---------------------------------------------

    private void loadAutoSpawnsMenu() {
        this.autoSpawns = new AutoSpawnsPanel(this, getListMenu("AutoSpawns.Main"), this.epicBosses);
    }

    private void reloadAutoSpawnsMenu() {
        this.autoSpawns.initializePanel(getListMenu("AutoSpawns.Main"));
    }

    //---------------------------------------------
    //
    //  C U S T O M   B O S S E S   P A N E L
    //
    //---------------------------------------------

    private void loadCustomBossesMenu() {
        this.bosses = new CustomBossesPanel(this, getListMenu("Bosses"), this.epicBosses);
    }

    private void reloadCustomBosses() {
        this.bosses.initializePanel(getListMenu("Bosses"));
    }

    //---------------------------------------------
    //
    //  C U S T O M   S K I L L S   P A N E L
    //
    //---------------------------------------------

    private void loadCustomSkillsMenu() {
        this.customSkills = new CustomSkillsPanel(this, getListMenu("Skills.Main"), this.epicBosses);
    }

    private void reloadCustomSkills() {
        this.customSkills.initializePanel(getListMenu("Skills.Main"));
    }

    //---------------------------------------------
    //
    //  D R O P   T A B L E   P A N E L
    //
    //---------------------------------------------

    private void loadDropTableMenu() {
        this.dropTables = new DropTablePanel(this, getListMenu("DropTable.Main"), this.epicBosses);
    }

    private void reloadDropTable() {
        this.dropTables.initializePanel(getListMenu("DropTable.Main"));
    }

    //---------------------------------------------
    //
    //  C U S T O M   I T E M S   P A N E L
    //
    //---------------------------------------------

    private void loadCustomItemsMenu() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("CustomItemsMenu"));

        this.customItems = new CustomItemsPanel(this, panelBuilder, this.epicBosses);
    }

    private void reloadCustomItems() {
        PanelBuilder panelBuilder = new PanelBuilder(this.epicBosses.getEditor().getConfigurationSection("CustomItemsMenu"));

        this.customItems.initializePanel(panelBuilder);
    }

    private String getPath(String key) {
        return "Display." + key + ".menuName";
    }

    public IPanelHandler getMainMenu() {
        return this.mainMenu;
    }

    public IPanelHandler getCustomItems() {
        return this.customItems;
    }

    public IPanelHandler getBosses() {
        return this.bosses;
    }

    public IPanelHandler getAutoSpawns() {
        return this.autoSpawns;
    }

    public IPanelHandler getDropTables() {
        return this.dropTables;
    }

    public IPanelHandler getCustomSkills() {
        return this.customSkills;
    }

    public IPanelHandler getShopPanel() {
        return this.shopPanel;
    }

    public IPanelHandler getCustomItemAddItemsMenu() {
        return this.customItemAddItemsMenu;
    }

    public ISubVariablePanelHandler<BossEntity, EntityStatsElement> getEquipmentEditMenu() {
        return this.equipmentEditMenu;
    }

    public ISubVariablePanelHandler<BossEntity, EntityStatsElement> getHelmetEditorMenu() {
        return this.helmetEditorMenu;
    }

    public ISubVariablePanelHandler<BossEntity, EntityStatsElement> getChestplateEditorMenu() {
        return this.chestplateEditorMenu;
    }

    public ISubVariablePanelHandler<BossEntity, EntityStatsElement> getLeggingsEditorMenu() {
        return this.leggingsEditorMenu;
    }

    public ISubVariablePanelHandler<BossEntity, EntityStatsElement> getBootsEditorMenu() {
        return this.bootsEditorMenu;
    }

    public ISubVariablePanelHandler<BossEntity, EntityStatsElement> getWeaponEditMenu() {
        return this.weaponEditMenu;
    }

    public ISubVariablePanelHandler<BossEntity, EntityStatsElement> getOffHandEditorMenu() {
        return this.offHandEditorMenu;
    }

    public ISubVariablePanelHandler<BossEntity, EntityStatsElement> getMainHandEditorMenu() {
        return this.mainHandEditorMenu;
    }

    public ISubVariablePanelHandler<BossEntity, EntityStatsElement> getStatisticMainEditMenu() {
        return this.statisticMainEditMenu;
    }

    public ISubVariablePanelHandler<BossEntity, EntityStatsElement> getEntityTypeEditMenu() {
        return this.entityTypeEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getMainBossEditMenu() {
        return this.mainBossEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getDropsEditMenu() {
        return this.dropsEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getTargetingEditMenu() {
        return this.targetingEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getSkillsBossEditMenu() {
        return this.skillsBossEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getSkillListBossEditMenu() {
        return this.skillListBossEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getCommandsMainEditMenu() {
        return this.commandsMainEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getOnSpawnCommandEditMenu() {
        return this.onSpawnCommandEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getOnDeathCommandEditMenu() {
        return this.onDeathCommandEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getMainDropsEditMenu() {
        return this.mainDropsEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getMainTextEditMenu() {
        return this.mainTextEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getMainTauntEditMenu() {
        return this.mainTauntEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getOnSpawnTextEditMenu() {
        return this.onSpawnTextEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getOnSpawnSubTextEditMenu() {
        return this.onSpawnSubTextEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getOnDeathTextEditMenu() {
        return this.onDeathTextEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getOnDeathSubTextEditMenu() {
        return this.onDeathSubTextEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getOnDeathPositionTextEditMenu() {
        return this.onDeathPositionTextEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getOnTauntTextEditMenu() {
        return this.onTauntTextEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getSpawnItemEditMenu() {
        return this.spawnItemEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getBossShopEditMenu() {
        return this.bossShopEditMenu;
    }

    public IVariablePanelHandler<BossEntity> getBossSkillMasterMessageTextEditMenu() {
        return this.bossSkillMasterMessageTextEditMenu;
    }

    public BossListEditorPanel getEquipmentListEditMenu() {
        return this.equipmentListEditMenu;
    }

    public BossListEditorPanel getWeaponListEditMenu() {
        return this.weaponListEditMenu;
    }

    public BossListEditorPanel getStatisticListEditMenu() {
        return this.statisticListEditMenu;
    }

    public IVariablePanelHandler<Skill> getMainSkillEditMenu() {
        return this.mainSkillEditMenu;
    }

    public IVariablePanelHandler<Skill> getCustomMessageEditMenu() {
        return this.customMessageEditMenu;
    }

    public IVariablePanelHandler<Skill> getSkillTypeEditMenu() {
        return this.skillTypeEditMenu;
    }

    public IVariablePanelHandler<Skill> getPotionSkillEditorPanel() {
        return this.potionSkillEditorPanel;
    }

    public IVariablePanelHandler<Skill> getCommandSkillEditorPanel() {
        return this.commandSkillEditorPanel;
    }

    public IVariablePanelHandler<Skill> getGroupSkillEditorPanel() {
        return this.groupSkillEditorPanel;
    }

    public IVariablePanelHandler<Skill> getCustomSkillEditorPanel() {
        return this.customSkillEditorPanel;
    }

    public ISubVariablePanelHandler<Skill, PotionEffectHolder> getCreatePotionEffectMenu() {
        return this.createPotionEffectMenu;
    }

    public ISubVariablePanelHandler<Skill, PotionEffectHolder> getPotionEffectTypeEditMenu() {
        return this.potionEffectTypeEditMenu;
    }

    public ISubVariablePanelHandler<Skill, SubCommandSkillElement> getModifyCommandEditMenu() {
        return this.modifyCommandEditMenu;
    }

    public ISubVariablePanelHandler<Skill, SubCommandSkillElement> getCommandListSkillEditMenu() {
        return this.commandListSkillEditMenu;
    }

    public ISubVariablePanelHandler<Skill, CustomSkillElement> getCustomSkillTypeEditorMenu() {
        return this.customSkillTypeEditorMenu;
    }

    public ISubVariablePanelHandler<Skill, CustomSkillElement> getSpecialSettingsEditorMenu() {
        return this.specialSettingsEditorMenu;
    }

    public ISubVariablePanelHandler<Skill, CustomSkillElement> getMinionSelectEditorMenu() {
        return this.minionSelectEditorMenu;
    }

    public IVariablePanelHandler<DropTable> getMainDropTableEditMenu() {
        return this.mainDropTableEditMenu;
    }

    public IVariablePanelHandler<DropTable> getDropTableTypeEditMenu() {
        return this.dropTableTypeEditMenu;
    }

    public ISubVariablePanelHandler<DropTable, SprayTableElement> getSprayDropTableMainEditMenu() {
        return this.sprayDropTableMainEditMenu;
    }

    public DropTableRewardMainEditorPanel<SprayTableElement> getSprayDropRewardMainEditPanel() {
        return this.sprayDropRewardMainEditPanel;
    }

    public DropTableNewRewardEditorPanel<SprayTableElement> getSprayDropNewRewardEditPanel() {
        return this.sprayDropNewRewardEditPanel;
    }

    public DropTableRewardsListEditorPanel<SprayTableElement> getSprayDropRewardListPanel() {
        return this.sprayDropRewardListPanel;
    }

    public ISubVariablePanelHandler<DropTable, GiveRewardEditHandler> getGiveRewardMainEditMenu() {
        return this.giveRewardMainEditMenu;
    }

    public ISubVariablePanelHandler<DropTable, GiveRewardEditHandler> getGiveCommandRewardListPanel() {
        return this.giveCommandRewardListPanel;
    }

    public ISubVariablePanelHandler<DropTable, GiveRewardEditHandler> getGiveCommandNewRewardPanel() {
        return this.giveCommandNewRewardPanel;
    }

    public ISubSubVariablePanelHandler<DropTable, GiveRewardEditHandler, String> getGiveCommandRewardMainEditMenu() {
        return this.giveCommandRewardMainEditMenu;
    }

    public ISubSubVariablePanelHandler<DropTable, GiveTableElement, String> getGiveRewardRewardsListMenu() {
        return this.giveRewardRewardsListMenu;
    }

    public ISubVariablePanelHandler<DropTable, GiveTableElement> getGiveRewardPositionListMenu() {
        return this.giveRewardPositionListMenu;
    }

    public DropTableRewardMainEditorPanel<GiveRewardEditHandler> getGiveDropRewardMainEditPanel() {
        return this.giveDropRewardMainEditPanel;
    }

    public DropTableNewRewardEditorPanel<GiveRewardEditHandler> getGiveDropNewRewardEditPanel() {
        return this.giveDropNewRewardEditPanel;
    }

    public DropTableRewardsListEditorPanel<GiveRewardEditHandler> getGiveDropRewardListPanel() {
        return this.giveDropRewardListPanel;
    }

    public ISubVariablePanelHandler<DropTable, DropTableElement> getDropDropTableMainEditMenu() {
        return this.dropDropTableMainEditMenu;
    }

    public DropTableRewardMainEditorPanel<DropTableElement> getDropDropRewardMainEditPanel() {
        return this.dropDropRewardMainEditPanel;
    }

    public DropTableNewRewardEditorPanel<DropTableElement> getDropDropNewRewardEditPanel() {
        return this.dropDropNewRewardEditPanel;
    }

    public DropTableRewardsListEditorPanel<DropTableElement> getDropDropRewardListPanel() {
        return this.dropDropRewardListPanel;
    }

    public IVariablePanelHandler<AutoSpawn> getMainAutoSpawnEditPanel() {
        return this.mainAutoSpawnEditPanel;
    }

    public IVariablePanelHandler<AutoSpawn> getAutoSpawnEntitiesEditPanel() {
        return this.autoSpawnEntitiesEditPanel;
    }

    public IVariablePanelHandler<AutoSpawn> getAutoSpawnSpecialSettingsEditorPanel() {
        return this.autoSpawnSpecialSettingsEditorPanel;
    }

    public IVariablePanelHandler<AutoSpawn> getAutoSpawnTypeEditorPanel() {
        return this.autoSpawnTypeEditorPanel;
    }

    public IVariablePanelHandler<AutoSpawn> getAutoSpawnCustomSettingsEditorPanel() {
        return this.autoSpawnCustomSettingsEditorPanel;
    }

    public IVariablePanelHandler<AutoSpawn> getAutoSpawnMessageEditorPanel() {
        return this.autoSpawnMessageEditorPanel;
    }

    public PanelBuilder getAddItemsBuilder() {
        return this.addItemsBuilder;
    }
}
