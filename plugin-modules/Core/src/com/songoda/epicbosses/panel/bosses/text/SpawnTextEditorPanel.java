package com.songoda.epicbosses.panel.bosses.text;

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
 * @since 29-Nov-18
 */
public class SpawnTextEditorPanel extends VariablePanelHandler<BossEntity> {

    private BossesFileManager bossesFileManager;

    public SpawnTextEditorPanel(BossPanelManager bossPanelManager, PanelBuilder panelBuilder, EpicBosses plugin) {
        super(bossPanelManager, panelBuilder);

        this.bossesFileManager = plugin.getBossesFileManager();
    }

    @Override
    public void fillPanel(Panel panel, BossEntity bossEntity) {

    }

    @Override
    public void openFor(Player player, BossEntity bossEntity) {
        Map<String, String> replaceMap = new HashMap<>();
        Integer radius = bossEntity.getMessages().getOnSpawn().getRadius();
        String message = bossEntity.getMessages().getOnSpawn().getMessage();
        PanelBuilder panelBuilder = getPanelBuilder().cloneBuilder();

        if (radius == null) radius = 0;
        if (message == null) message = "N/A";

        replaceMap.put("{name}", BossAPI.getBossEntityName(bossEntity));
        replaceMap.put("{radius}", NumberUtils.get().formatDouble(radius));
        replaceMap.put("{selected}", message);
        panelBuilder.addReplaceData(replaceMap);

        Panel panel = panelBuilder.getPanel()
                .setDestroyWhenDone(true)
                .setCancelClick(true)
                .setCancelLowerClick(true)
                .setParentPanelHandler(this.bossPanelManager.getMainTextEditMenu(), bossEntity);
        PanelBuilderCounter counter = panel.getPanelBuilderCounter();

        ServerUtils.get().runTaskAsync(() -> {
            counter.getSlotsWith("Select").forEach(slot -> panel.setOnClick(slot, event -> this.bossPanelManager.getOnSpawnTextEditMenu().openFor((Player) event.getWhoClicked(), bossEntity)));
            counter.getSlotsWith("Radius").forEach(slot -> panel.setOnClick(slot, getRadiusAction(bossEntity)));

        });

        panel.openFor(player);
    }

    @Override
    public void initializePanel(PanelBuilder panelBuilder) {

    }

    private ClickAction getRadiusAction(BossEntity bossEntity) {
        return event -> {
            if (!bossEntity.isEditing()) {
                Message.Boss_Edit_CannotBeModified.msg(event.getWhoClicked());
                return;
            }

            ClickType clickType = event.getClick();
            int radiusToModifyBy = 0;

            if (clickType == ClickType.LEFT) {
                radiusToModifyBy = 1;
            } else if (clickType == ClickType.SHIFT_LEFT) {
                radiusToModifyBy = 10;
            } else if (clickType == ClickType.RIGHT) {
                radiusToModifyBy = -1;
            } else if (clickType == ClickType.SHIFT_RIGHT) {
                radiusToModifyBy = -10;
            }

            String modifyValue = radiusToModifyBy > 0 ? "increased" : "decreased";
            Integer currentRadius = bossEntity.getMessages().getOnSpawn().getRadius();

            if (currentRadius == null) currentRadius = 0;

            int newRadius = currentRadius + radiusToModifyBy;

            if (newRadius < -1) {
                newRadius = -1;
            }

            bossEntity.getMessages().getOnSpawn().setRadius(newRadius);
            this.bossesFileManager.save();
            Message.Boss_Messages_SetRadiusOnSpawn.msg(event.getWhoClicked(), modifyValue, NumberUtils.get().formatDouble(newRadius));
            openFor((Player) event.getWhoClicked(), bossEntity);
        };
    }
}
