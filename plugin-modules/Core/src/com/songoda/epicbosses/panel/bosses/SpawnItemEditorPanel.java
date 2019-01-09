package com.songoda.epicbosses.panel.bosses;

import com.songoda.epicbosses.CustomBosses;
import com.songoda.epicbosses.api.BossAPI;
import com.songoda.epicbosses.entity.BossEntity;
import com.songoda.epicbosses.entity.elements.EntityStatsElement;
import com.songoda.epicbosses.managers.BossPanelManager;
import com.songoda.epicbosses.managers.files.BossesFileManager;
import com.songoda.epicbosses.managers.files.ItemsFileManager;
import com.songoda.epicbosses.utils.ObjectUtils;
import com.songoda.epicbosses.utils.ServerUtils;
import com.songoda.epicbosses.utils.itemstack.ItemStackUtils;
import com.songoda.epicbosses.utils.itemstack.holder.ItemStackHolder;
import com.songoda.epicbosses.utils.panel.Panel;
import com.songoda.epicbosses.utils.panel.base.handlers.VariablePanelHandler;
import com.songoda.epicbosses.utils.panel.builder.PanelBuilder;
import com.songoda.epicbosses.utils.panel.builder.PanelBuilderCounter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 10-Jan-19
 */
public class SpawnItemEditorPanel extends VariablePanelHandler<BossEntity> {

    private BossesFileManager bossesFileManager;
    private ItemsFileManager itemsFileManager;
    private CustomBosses plugin;

    public SpawnItemEditorPanel(BossPanelManager bossPanelManager, PanelBuilder panelBuilder, CustomBosses plugin) {
        super(bossPanelManager, panelBuilder);

        this.bossesFileManager = plugin.getBossesFileManager();
        this.itemsFileManager = plugin.getItemStackManager();
        this.plugin = plugin;
    }

    @Override
    public void fillPanel(Panel panel, BossEntity bossEntity) {
        Map<String, ItemStackHolder> itemStackHolderMap = this.itemsFileManager.getItemStackHolders();
        List<String> entryList = new ArrayList<>(itemStackHolderMap.keySet());
        int maxPage = panel.getMaxPage(entryList);

        panel.setOnPageChange(((player, currentPage, requestedPage) -> {
            if(requestedPage < 0 || requestedPage > maxPage) return false;

            loadPage(panel, requestedPage, itemStackHolderMap, entryList, bossEntity);
            return true;
        }));

        loadPage(panel, 0, itemStackHolderMap, entryList, bossEntity);
    }

    @Override
    public void openFor(Player player, BossEntity bossEntity) {
        Map<String, String> replaceMap = new HashMap<>();
        PanelBuilder panelBuilder = getPanelBuilder().cloneBuilder();

        replaceMap.put("{name}", BossAPI.getBossEntityName(bossEntity));
        panelBuilder.addReplaceData(replaceMap);

        PanelBuilderCounter panelBuilderCounter = panelBuilder.getPanelBuilderCounter();
        Panel panel = panelBuilder.getPanel()
                .setParentPanelHandler(this.bossPanelManager.getMainBossEditMenu(), bossEntity);

        ServerUtils.get().runTaskAsync(() -> {
            panelBuilderCounter.getSlotsWith("AddNew").forEach(slot -> panel.setOnClick(slot, event -> this.bossPanelManager.getAddItemsMenu().openFor(player)));
            panelBuilderCounter.getSlotsWith("Remove").forEach(slot -> panel.setOnClick(slot, event -> {
                bossEntity.setSpawnItem("");
                this.bossesFileManager.save();

                openFor((Player) event.getWhoClicked(), bossEntity);
            }));

            fillPanel(panel, bossEntity);
        });

        panel.openFor(player);
    }

    @Override
    public void initializePanel(PanelBuilder panelBuilder) {

    }

    private void loadPage(Panel panel, int requestedPage, Map<String, ItemStackHolder> filteredMap, List<String> entryList, BossEntity bossEntity) {
        String current = ObjectUtils.getValue(bossEntity.getSpawnItem(), "");

        panel.loadPage(requestedPage, (slot, realisticSlot) -> {
            if(slot >= filteredMap.size()) {
                panel.setItem(realisticSlot, new ItemStack(Material.AIR), e -> {});
            } else {
                String name = entryList.get(slot);
                ItemStackHolder itemStackHolder = filteredMap.get(name);
                ItemStack itemStack = this.itemsFileManager.getItemStackConverter().from(itemStackHolder);

                if(itemStack == null) {
                    itemStack = new ItemStack(Material.BARRIER);
                }

                if(name.equalsIgnoreCase(current)) {
                    Map<String, String> replaceMap = new HashMap<>();

                    replaceMap.put("{name}", ItemStackUtils.getName(itemStack));

                    ItemStackUtils.applyDisplayName(itemStack, this.plugin.getConfig().getString("Display.Boss.Equipment.name"), replaceMap);
                }

                panel.setItem(realisticSlot, itemStack, e -> {
                    bossEntity.setSpawnItem(name);
                    this.bossesFileManager.save();

                    loadPage(panel, requestedPage, filteredMap, entryList, bossEntity);
                });
            }
        });
    }
}