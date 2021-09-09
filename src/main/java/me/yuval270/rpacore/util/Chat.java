package me.yuval270.rpacore.util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class Chat {
    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> translateList(List<String> list) {
        return list.stream().map(s -> translate(s)).collect(Collectors.toList());
    }
}
