package com.songoda.epicbosses.utils.itemstack;

import com.songoda.core.compatibility.CompatibleMaterial;
import com.songoda.core.compatibility.ServerVersion;
import com.songoda.epicbosses.utils.NumberUtils;
import com.songoda.epicbosses.utils.ServerUtils;
import com.songoda.epicbosses.utils.StringUtils;
import com.songoda.epicbosses.utils.itemstack.enchants.GlowEnchant;
import com.songoda.epicbosses.utils.itemstack.holder.ItemStackHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

/**
 * Created by charl on 28-Apr-17.
 */
public class ItemStackUtils {

    private static final Map<EntityType, Material> spawnableEntityMaterials;
    private static final Map<EntityType, Short> spawnableEntityIds;

    static {
        spawnableEntityMaterials = new HashMap<>();
        spawnableEntityIds = new HashMap<>();

        boolean isLegacy = ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_12);

        Arrays.stream(EntityType.values()).filter(EntityType::isSpawnable).forEach(entityType -> {
            if (isLegacy) {
                spawnableEntityIds.put(entityType, entityType.getTypeId());
            } else {
                String materialName = entityType.name() + "_SPAWN_EGG";
                Material material = Material.matchMaterial(materialName);
                if (material != null)
                    spawnableEntityMaterials.put(entityType, material);
            }
        });
    }

    public static List<EntityType> getSpawnableEntityTypes() {
        if (ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_12)) {
            return new ArrayList<>(spawnableEntityIds.keySet());
        } else {
            return new ArrayList<>(spawnableEntityMaterials.keySet());
        }
    }

    public static ItemStack getSpawnEggForEntity(EntityType entityType) {
        if (!entityType.isSpawnable())
            return new ItemStack(Material.AIR);

        if (ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_12)) {
            return new ItemStack(Material.valueOf("MONSTER_EGG"), 1, spawnableEntityIds.get(entityType));
        } else {
            return new ItemStack(spawnableEntityMaterials.get(entityType));
        }
    }

    public static ItemStack createItemStack(ItemStack itemStack, Map<String, String> replaceMap) {
        return createItemStack(itemStack, replaceMap, null);
    }

    public static ItemStack createItemStack(ItemStack itemStack, Map<String, String> replaceMap, Map<String, Object> compoundData) {
        ItemStack cloneStack = itemStack.clone();
        ItemMeta itemMeta = cloneStack.getItemMeta();
        boolean hasName = cloneStack.getItemMeta().hasDisplayName();
        boolean hasLore = cloneStack.getItemMeta().hasLore();
        String name = "";
        List<String> newLore = new ArrayList<>();

        if (hasName) name = cloneStack.getItemMeta().getDisplayName();

        if (replaceMap != null && !replaceMap.isEmpty()) {
            if (hasName) {
                for (String s : replaceMap.keySet()) {
                    name = name.replace(s, replaceMap.get(s));
                }

                itemMeta.setDisplayName(name);
            }

            if (hasLore) {
                for (String s : itemMeta.getLore()) {
                    for (String z : replaceMap.keySet()) {
                        if (s.contains(z)) s = s.replace(z, replaceMap.get(z));
                    }

                    newLore.add(s);
                }

                itemMeta.setLore(newLore);
            }
        }

        cloneStack.setItemMeta(itemMeta);

        if (compoundData == null || compoundData.isEmpty()) return cloneStack;

        return cloneStack;
    }

    public static ItemStack createItemStack(ConfigurationSection configurationSection) {
        return createItemStack(configurationSection, 1, null);
    }

    public static ItemStack createItemStack(ConfigurationSection configurationSection, int amount, Map<String, String> replacedMap) {

        CompatibleMaterial material = CompatibleMaterial.getMaterial(configurationSection.getString("type"));

        String type = material == null ? configurationSection.getString("type") : material.getMaterial().name();
        String name = configurationSection.getString("name");
        List<String> lore = configurationSection.getStringList("lore");
        List<String> enchants = configurationSection.getStringList("enchants");
        Short durability = (Short) configurationSection.get("durability");
        if (material != null && material.getData() != -1) durability = (short) material.getData();

        String owner = configurationSection.getString("owner");
        Map<Enchantment, Integer> map = new HashMap<>();
        List<String> newLore = new ArrayList<>();
        boolean addGlow = false;
        short meta = 0;
        Material mat;

        if (NumberUtils.get().isInt(type)) {
            mat = MaterialUtils.fromId(NumberUtils.get().getInteger(type));
        } else {
            if (type.contains(":")) {
                String[] split = type.split(":");
                String typeSplit = split[0];

                if (NumberUtils.get().isInt(typeSplit)) {
                    mat = MaterialUtils.fromId(NumberUtils.get().getInteger(typeSplit));
                } else {
                    mat = Material.getMaterial(typeSplit);
                }

                meta = Short.valueOf(split[1]);
            } else {
                mat = Material.getMaterial(type);
            }
        }

        if ((replacedMap != null) && (name != null)) {
            for (String z : replacedMap.keySet()) {
                if (!name.contains(z)) continue;

                name = name.replace(z, replacedMap.get(z));
            }
        }

        if (lore != null && !lore.isEmpty()) {
            for (String z : lore) {
                String y = z;

                if (replacedMap != null) {
                    for (String x : replacedMap.keySet()) {
                        if (x == null || !y.contains(x)) continue;

                        if (replacedMap.get(x) == null) {
                            ServerUtils.get().logDebug("Failed to apply replaced lore: [y=" + y + "x=" + x + "]");
                            continue;
                        }

                        y = y.replace(x, replacedMap.get(x));
                    }
                }

                if (y.contains("\n")) {
                    String[] split = y.split("\n");

                    for (String s2 : split) {
                        newLore.add(ChatColor.translateAlternateColorCodes('&', s2));
                    }
                } else {
                    newLore.add(ChatColor.translateAlternateColorCodes('&', y));
                }
            }
        }

        if (enchants != null) {
            for (String s : enchants) {
                String[] spl = s.split(":");
                String ench = spl[0];

                if (ench.equalsIgnoreCase("GLOW")) {
                    addGlow = true;
                } else {
                    int level = Integer.parseInt(spl[1]);

                    map.put(Enchantment.getByName(ench), level);
                }
            }
        }

        if (mat == null) return null;

        ItemStack itemStack = new ItemStack(mat, amount, meta);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (!newLore.isEmpty()) {
            itemMeta.setLore(newLore);
        }
        if (name != null) {
            if (!name.equals("")) {
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            }
        }

        itemStack.setItemMeta(itemMeta);

        if (!map.isEmpty()) {
            itemStack.addUnsafeEnchantments(map);
        }
        if (durability != null)
            itemStack.setDurability(durability);

        if (configurationSection.contains("owner") && itemStack.getType() == CompatibleMaterial.PLAYER_HEAD.getMaterial()) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

            skullMeta.setOwner(owner);

            itemStack.setItemMeta(skullMeta);
        }

        return addGlow ? addGlow(itemStack) : itemStack;
    }

    public static void applyDisplayName(ItemStack itemStack, String name) {
        applyDisplayName(itemStack, name, null);
    }

    public static void applyDisplayName(ItemStack itemStack, String name, Map<String, String> replaceMap) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (replaceMap != null) {
            for (String s : replaceMap.keySet()) {
                if (name.contains(s)) name = name.replace(s, replaceMap.get(s));
            }
        }

        if (itemMeta == null) return;

        itemMeta.setDisplayName(StringUtils.get().translateColor(name));
        itemStack.setItemMeta(itemMeta);
    }

    public static void applyDisplayLore(ItemStack itemStack, List<String> lore) {
        applyDisplayLore(itemStack, lore, null);
    }

    public static void applyDisplayLore(ItemStack itemStack, List<String> lore, Map<String, String> replaceMap) {
        ItemMeta itemMeta;
        if (itemStack == null || (itemMeta = itemStack.getItemMeta()) == null)
            return;
        if (lore == null || lore.isEmpty()) {
            itemMeta.setLore(Collections.EMPTY_LIST);
            itemStack.setItemMeta(itemMeta);
            return;
        }

        if (replaceMap != null) {
            for (String s : replaceMap.keySet()) {
                lore.replaceAll(loreLine -> loreLine
                        .replace(s, replaceMap.get(s))
                        .replace('&', '§')
                );
            }
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    public static String getName(ItemStack itemStack) {
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName()) {
            return StringUtils.get().formatString(itemStack.getType().name());
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        return itemMeta.getDisplayName();
    }

    public static Material getType(String string) {
        Material material = Material.getMaterial(string);

        if (material == null) {
            if (NumberUtils.get().isInt(string)) {
                return null;
            } else {
                String[] split = string.split(":");

                material = Material.getMaterial(split[0]);

                if (material != null) return material;

                return null;
            }
        }

        return material;
    }

    public static ItemStack addGlow(ItemStack itemStack) {
        return GlowEnchant.addGlow(itemStack);
    }

    public static void giveItems(Player player, ItemStack... items) {
        giveItems(player, Arrays.asList(items));
    }

    public static void giveItems(Player player, List<ItemStack> items) {
        PlayerInventory inventory = player.getInventory();

        for (ItemStack itemStack : items) {
            int amount = itemStack.getAmount();

            while (amount > 0) {
                int toGive = amount > 64 ? 64 : amount;

                ItemStack stack = itemStack.clone();
                stack.setAmount(toGive);

                if (inventory.firstEmpty() != -1) {
                    inventory.addItem(stack);
                } else {
                    player.getWorld().dropItemNaturally(player.getLocation(), stack);
                }

                amount -= toGive;
            }
        }
    }

    public static void takeItems(Player player, Map<ItemStack, Integer> items) {
        PlayerInventory inventory = player.getInventory();

        for (ItemStack itemStack : items.keySet()) {
            int toTake = items.get(itemStack);
            int i = 0;

            while (toTake > 0 && i < inventory.getSize()) {
                if (inventory.getItem(i) != null && inventory.getItem(i).getType() == itemStack.getType() && (inventory.getItem(i).getData().getData() == itemStack.getData().getData() || itemStack.getData().getData() == -1)) {
                    ItemStack target = inventory.getItem(i);
                    if (target.getAmount() > toTake) {
                        target.setAmount(target.getAmount() - toTake);
                        inventory.setItem(i, target);
                        break;
                    } else if (target.getAmount() == toTake) {
                        inventory.setItem(i, new ItemStack(Material.AIR));
                        break;
                    } else {
                        toTake -= target.getAmount();
                        inventory.setItem(i, new ItemStack(Material.AIR));
                    }
                }
                i++;
            }
        }
    }

    public static int getAmount(Player player, ItemStack itemStack) {
        PlayerInventory playerInventory = player.getInventory();
        int amountInInventory = 0;

        for (int i = 0; i < playerInventory.getSize(); i++) {
            if (playerInventory.getItem(i) != null && playerInventory.getItem(i).getType() == itemStack.getType() && (playerInventory.getItem(i).getData().getData() == itemStack.getData().getData() || itemStack.getData().getData() == -1)) {
                amountInInventory += playerInventory.getItem(i).getAmount();
            }
        }

        return amountInInventory;
    }

    @SuppressWarnings("unchecked")
    public static ItemStackHolder getItemStackHolder(ConfigurationSection configurationSection) {
        Integer amount = (Integer) configurationSection.get("amount", null);

        CompatibleMaterial material = CompatibleMaterial.getMaterial(configurationSection.getString("type", null));

        String type = material.getMaterial().name();
        Short durability = (Short) configurationSection.get("durability");
        if (material.getData() != -1) durability = (short) material.getData();
        String name = configurationSection.getString("name", null);
        List<String> lore = (List<String>) configurationSection.getList("lore", null);
        List<String> enchants = (List<String>) configurationSection.getList("enchants", null);
        String skullOwner = configurationSection.getString("skullOwner", null);
        Short spawnerId = (Short) configurationSection.get("spawnerId", null);
        //Boolean isGlowing = (Boolean) configurationSection.get("isGlowing", null);

        return new ItemStackHolder(amount, type, durability, name, lore, enchants, skullOwner, spawnerId);
    }

    public static boolean isItemStackSame(ItemStack itemStack1, ItemStack itemStack2) {
        if (itemStack1 == null || itemStack2 == null) return false;
        if (itemStack1.getType() != itemStack2.getType()) return false;
        // Durability checks are too tempermental to be reliable for all versions
        //if(itemStack1.getDurability() != itemStack2.getDurability()) return false;

        ItemMeta itemMeta1 = itemStack1.getItemMeta();
        ItemMeta itemMeta2 = itemStack2.getItemMeta();

        if (itemMeta1 == null || itemMeta2 == null) return false;

        if (itemMeta1.hasDisplayName() == itemMeta2.hasDisplayName()) {
            if (itemMeta1.hasDisplayName() && !itemMeta1.getDisplayName().equals(itemMeta2.getDisplayName()))
                return false;
        } else {
            return false;
        }

        if (itemMeta1.hasLore() == itemMeta2.hasLore()) {
            if (itemMeta1.hasLore() && !itemMeta1.getLore().equals(itemMeta2.getLore()))
                return false;
        } else {
            return false;
        }

        return true;
    }
}