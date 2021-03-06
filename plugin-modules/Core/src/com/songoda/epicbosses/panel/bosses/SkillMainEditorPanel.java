package com.songoda.epicbosses.panel.bosses;

import com.songoda.epicbosses.EpicBosses;
import com.songoda.epicbosses.api.BossAPI;
import com.songoda.epicbosses.entity.BossEntity;
import com.songoda.epicbosses.managers.BossPanelManager;
import com.songoda.epicbosses.managers.files.BossesFileManager;
import com.songoda.epicbosses.utils.Message;
import com.songoda.epicbosses.utils.NumberUtils;
import com.songoda.epicbosses.utils.ServerUtils;
import com.songoda.epicbosses.utils.panel.Panel;
import com.songoda.epicbosses.utils.panel.base.ClickAction;
import com.songoda.epicbosses.utils.panel.base.handlers.VariablePanelHandler;
import com.songoda.epicbosses.utils.panel.builder.PanelBuilder;
import com.songoda.epicbosses.utils.panel.builder.PanelBuilderCounter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 25-Nov-18
 */
public class SkillMainEditorPanel extends VariablePanelHandler<BossEntity> {

    private BossesFileManager bossesFileManager;

    public SkillMainEditorPanel(BossPanelManager bossPanelManager, PanelBuilder panelBuilder, EpicBosses plugin) {
        super(bossPanelManager, panelBuilder);

        this.bossesFileManager = plugin.getBossesFileManager();
    }

    @Override
    public void fillPanel(Panel panel, BossEntity bossEntity) {

    }

    @Override
    public void openFor(Player player, BossEntity bossEntity) {
        Map<String, String> replaceMap = new HashMap<>();
        Double chance = bossEntity.getSkills().getOverallChance();
        PanelBuilder panelBuilder = getPanelBuilder().cloneBuilder();

        if (chance == null) chance = 0.0;

        replaceMap.put("{name}", BossAPI.getBossEntityName(bossEntity));
        replaceMap.put("{chance}", NumberUtils.get().formatDouble(chance));
        panelBuilder.addReplaceData(replaceMap);

        Panel panel = panelBuilder.getPanel()
                .setDestroyWhenDone(true)
                .setCancelClick(true)
                .setCancelLowerClick(true)
                .setParentPanelHandler(this.bossPanelManager.getMainBossEditMenu(), bossEntity);
        PanelBuilderCounter counter = panel.getPanelBuilderCounter();

        ServerUtils.get().runTaskAsync(() -> {
            counter.getSlotsWith("OverallChance").forEach(slot -> panel.setOnClick(slot, getOverallChanceAction(bossEntity)));
            counter.getSlotsWith("SkillList").forEach(slot -> panel.setOnClick(slot, event -> this.bossPanelManager.getSkillListBossEditMenu().openFor((Player) event.getWhoClicked(), bossEntity)));
            counter.getSlotsWith("Message").forEach(slot -> panel.setOnClick(slot, event -> this.bossPanelManager.getBossSkillMasterMessageTextEditMenu().openFor((Player) event.getWhoClicked(), bossEntity)));
        });

        panel.openFor(player);
    }

    @Override
    public void initializePanel(PanelBuilder panelBuilder) {

    }

    private ClickAction getOverallChanceAction(BossEntity bossEntity) {
        return event -> {
            if (!bossEntity.isEditing()) {
                Message.Boss_Edit_CannotBeModified.msg(event.getWhoClicked());
                return;
            }

            ClickType clickType = event.getClick();
            double chanceToModifyBy = 0.0;

            if (clickType == ClickType.LEFT) {
                chanceToModifyBy = 1.0;
            } else if (clickType == ClickType.SHIFT_LEFT) {
                chanceToModifyBy = 0.1;
            } else if (clickType == ClickType.RIGHT) {
                chanceToModifyBy = -1.0;
            } else if (clickType == ClickType.SHIFT_RIGHT) {
                chanceToModifyBy = -0.1;
            }

            String modifyValue = chanceToModifyBy > 0.0 ? "increased" : "decreased";
            Double currentChance = bossEntity.getSkills().getOverallChance();

            if (currentChance == null) currentChance = 0.0;

            double newChance = currentChance + chanceToModifyBy;

            if (newChance < 0.0) {
                newChance = 0.0;
            }

            if (newChance > 100.0) {
                newChance = 100.0;
            }

            bossEntity.getSkills().setOverallChance(newChance);
            this.bossesFileManager.save();
            Message.Boss_Skills_SetChance.msg(event.getWhoClicked(), modifyValue, NumberUtils.get().formatDouble(newChance));
            openFor((Player) event.getWhoClicked(), bossEntity);
        };
    }
}
