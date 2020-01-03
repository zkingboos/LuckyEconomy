package com.lucky.managers;

import com.lucky.LuckyEconomy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
        if(money.getString("Dinheiro." + p.getName()) == null) newAccount(p);
    }

    public static void payMoney(Player sender, double amount, Player recipient) {
        checkPlayer(sender);
        checkPlayer(recipient);

        double moneysender = getMoney(sender);
        double moneyrecipient = getMoney(recipient);

        if(moneysender < amount) sender.sendMessage("§cVocê não tem dinheiro suficiente.");
        String path = "Dinheiro." + sender.getName();

        money.set(path, moneysender - amount);
        money.set(path, moneyrecipient + amount);
        sender.sendMessage("§eVocê enviou §6R$" + format(amount) + " §ereais para §6" + recipient.getName());
        recipient.sendMessage("§eVocê recebeu §6R$" + format(amount) + " §ereais de§6" + sender.getName());
        saveMoneyFile();

    }

    public static void setMoney(Player p, double amount) {
        String path = "Dinheiro." + p.getName();
        money.set(path, amount);
        saveMoneyFile();
    }

}
