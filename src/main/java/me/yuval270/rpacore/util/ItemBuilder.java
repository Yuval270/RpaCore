package me.yuval270.rpacore.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {
    private final ItemStack itemStack;
    private final ItemMeta meta;

    public ItemBuilder(Material material){
        itemStack = new ItemStack(material);
        meta = itemStack.getItemMeta();
    }

    public ItemBuilder setName(String name){
        meta.setDisplayName(Chat.translate(name));
        return this;
    }

    public ItemBuilder setLore(List<String> list){
        meta.setLore(Chat.translateList(list));
        return this;
    }

    public ItemStack build(){
        itemStack.setItemMeta(meta);
        return itemStack.clone();
    }
}
