package com.lucky.utils;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Utilities {
    public static String prefix = "§a[LuckyEconomy] ";
    public static ConsoleCommandSender sc = Bukkit.getConsoleSender();

    private static String[] suffix = {"", "k", "m", "b", "t", "q", "qq", "s", "ss", "o", "n", "d"};

    public static String format(Number value) {
        DecimalFormat format = new DecimalFormat("#,##0.00");
        String[] parts = format.format(value).split("\\.");
        return parts[0] + suffix[parts.length - 1];
    }
}
