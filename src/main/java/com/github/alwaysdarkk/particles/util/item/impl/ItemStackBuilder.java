package com.github.alwaysdarkk.particles.util.item.impl;

import com.github.alwaysdarkk.particles.util.ColorUtil;
import com.github.alwaysdarkk.particles.util.item.ItemStackBase;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ItemStackBuilder implements ItemStackBase<ItemStackBuilder> {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemStackBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public static ItemStackBuilder newBuilder(Material material) {
        return newBuilder(new ItemStack(material));
    }

    public static ItemStackBuilder newBuilder(ItemStack itemStack) {
        return new ItemStackBuilder(itemStack);
    }

    @Override
    public ItemStackBuilder material(Material material) {
        return changeItemStack(itemStack -> itemStack.setType(material));
    }

    @Override
    public ItemStackBuilder amount(int amount) {
        return changeItemStack(itemStack -> itemStack.setAmount(amount));
    }

    @Override
    public ItemStackBuilder displayName(String displayName) {
        return changeItemMeta(itemMeta ->
                itemMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', displayName))));
    }

    @Override
    public ItemStackBuilder lore(List<String> lore) {
        List<Component> componentLore =
                lore.stream().map(ColorUtil::colored).map(Component::text).collect(Collectors.toList());

        return changeItemMeta(itemMeta -> itemMeta.lore(componentLore));
    }

    @Override
    public ItemStackBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    @Override
    public ItemStackBuilder addLore(String... lore) {
        return changeItemMeta(itemMeta -> {
            List<Component> componentLore = itemMeta.hasLore() ? itemMeta.lore() : new ArrayList<>();
            componentLore.addAll(Arrays.stream(lore)
                    .map(ColorUtil::colored)
                    .map(Component::text)
                    .toList());
            itemMeta.lore(componentLore);
        });
    }

    @Override
    public ItemStackBuilder loreAf(String... lore) {
        return changeItemMeta(itemMeta -> {
            List<Component> componentLore = itemMeta.hasLore() ? itemMeta.lore() : new ArrayList<>();
            assert componentLore != null;

            componentLore.addAll(Arrays.stream(lore)
                    .map(ColorUtil::colored)
                    .map(Component::text)
                    .toList());

            itemMeta.lore(componentLore);
        });
    }

    @Override
    public ItemStackBuilder loreBf(String... lore) {
        return changeItemMeta(itemMeta -> {
            List<Component> componentLore = Arrays.stream(lore)
                    .map(ColorUtil::colored)
                    .map(Component::text)
                    .collect(Collectors.toList());

            List<Component> metaComponentLore = itemMeta.lore();
            if (metaComponentLore != null) {
                componentLore.addAll(metaComponentLore);
            }

            itemMeta.lore(componentLore);
        });
    }

    @Override
    public ItemStackBuilder data(int data) {
        return changeItemMeta(itemMeta -> itemMeta.setCustomModelData(data));
    }

    @Override
    public ItemStackBuilder allFlags() {
        return changeItemMeta(itemMeta -> itemMeta.addItemFlags(ItemFlag.values()));
    }

    @Override
    public ItemStackBuilder addItemFlags(ItemFlag... itemFlags) {
        return changeItemMeta(itemMeta -> itemMeta.addItemFlags(itemFlags));
    }

    @Override
    public ItemStackBuilder removeItemFlags(ItemFlag... itemFlags) {
        return changeItemMeta(itemMeta -> itemMeta.removeItemFlags(itemFlags));
    }

    @Override
    public ItemStackBuilder enchant(Enchantment enchantment, int level) {
        return changeItemStack(itemStack -> itemStack.addUnsafeEnchantment(enchantment, level));
    }

    @Override
    public ItemStackBuilder enchant(Map<Enchantment, Integer> enchants) {
        return changeItemStack(itemStack -> itemStack.addUnsafeEnchantments(enchants));
    }

    @Override
    public ItemStackBuilder removeEnchant(Enchantment enchantment) {
        return changeItemStack(itemStack -> itemStack.removeEnchantment(enchantment));
    }

    @Override
    public ItemStackBuilder unbreakable(boolean unbreakable) {
        return changeItemMeta(itemMeta -> itemMeta.setUnbreakable(unbreakable));
    }

    @Override
    public ItemStackBuilder addAttribute(Attribute attribute, AttributeModifier modifier) {
        return changeItemMeta(itemMeta -> itemMeta.addAttributeModifier(attribute, modifier));
    }

    @Override
    public ItemStackBuilder removeAttribute(Attribute attribute) {
        return changeItemMeta(itemMeta -> itemMeta.removeAttributeModifier(attribute));
    }

    @Override
    public ItemStackBuilder changeItemStack(Consumer<ItemStack> consumer) {
        consumer.accept(itemStack);
        return this;
    }

    @Override
    public ItemStackBuilder changeItemMeta(Consumer<ItemMeta> consumer) {
        consumer.accept(itemMeta);
        return this;
    }

    @Override
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}