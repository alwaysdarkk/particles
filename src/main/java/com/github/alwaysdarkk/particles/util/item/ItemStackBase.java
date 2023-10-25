package com.github.alwaysdarkk.particles.util.item;

import com.github.alwaysdarkk.particles.util.item.impl.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface ItemStackBase<T extends ItemStackBuilder> extends Builder<ItemStack> {
    T material(Material material);

    T amount(int amount);

    T displayName(String displayName);

    T lore(List<String> lore);

    T lore(String... lore);

    T addLore(String... lore);

    T loreAf(String... lore);

    T loreBf(String... lore);

    T data(int data);

    T allFlags();

    T addItemFlags(ItemFlag... itemFlags);

    T removeItemFlags(ItemFlag... itemFlags);

    T enchant(Enchantment enchantment, int level);

    T enchant(Map<Enchantment, Integer> enchants);

    T removeEnchant(Enchantment enchantment);

    T unbreakable(boolean unbreakable);

    T addAttribute(Attribute attribute, AttributeModifier modifier);

    T removeAttribute(Attribute attribute);

    T changeItemStack(Consumer<ItemStack> consumer);

    T changeItemMeta(Consumer<ItemMeta> consumer);
}