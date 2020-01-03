package com.lucky;


import com.lucky.commands.CommandDinheiro;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class LuckyEconomy extends JavaPlugin {

    private static File fileOfMoney = new File("plugins/LuckyEconomy/money.yml");
    public static YamlConfiguration money = YamlConfiguration.loadConfiguration(fileOfMoney);

    @Override
    public void onEnable() {
        criarConfig();
        getCommand("dinheiro").setExecutor(new CommandDinheiro());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LuckyEconomy getPlugin() {
        return getPlugin(LuckyEconomy.class);
    }

    public void criarConfig() {
        if (!fileOfMoney.exists()) saveMoneyFile();
    }

        public static void saveMoneyFile() {
            try {
                money.save(fileOfMoney);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
