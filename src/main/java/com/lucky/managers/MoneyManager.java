package com.lucky.managers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.List;

import static com.lucky.LuckyEconomy.*;
import static com.lucky.utils.MoneyFormatter.format;


public class MoneyManager {

    public static void newAccount(Player p) {
        try {
            String path = "Dinheiro." + p.getName();
            money.set(path, 0f);
            saveMoneyFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getMoney(Player p) {
        checkPlayer(p);
        return (money.getDouble("Dinheiro." + p.getName()));
    }

    public static void checkPlayer(Player p) {
        if (money.getString("Dinheiro." + p.getName()) == null) newAccount(p);
    }

    public static void payMoney(Player sender, double amount, Player recipient) {

        checkPlayer(sender);
        checkPlayer(recipient);

        double moneysender = getMoney(sender);
        double moneyrecipient = getMoney(recipient);

        double moneyformatado1 = (moneysender - amount);
        double moneyformatado2 = (moneyrecipient + amount);

        if (moneysender < amount) {
            sender.sendMessage("§cVocê não tem dinheiro suficiente.");
            return;
        }

        String path = "Dinheiro." + sender.getName();
        String path2 = "Dinheiro." + recipient.getName();

        money.set(path, moneysender - amount);
        money.set(path2, moneyrecipient + amount);

        saveMoneyFile();

    }

    public static void setMoney(Player p, double amount) {
        String path = "Dinheiro." + p.getName();
        money.set(path, amount);
        saveMoneyFile();
    }

    public static void removeMoney(Player p, double amount) {
        String path = "Dinheiro." + p.getName();
        money.set(path, getMoney(p) - amount);
    }
}
