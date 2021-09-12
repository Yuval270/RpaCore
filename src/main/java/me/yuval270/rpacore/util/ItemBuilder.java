package me.yuval270.rpacore.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(Material material){
        itemStack = new ItemStack(material);
    }

    public ItemBuilder setName(String name){
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(Chat.translate(name));
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> list){
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Chat.translateList(list));
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStack build(){
        return itemStack.clone();
    }
}
