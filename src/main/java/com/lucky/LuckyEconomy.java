package com.lucky;


import com.lucky.commands.CommandDinheiro;
import com.lucky.events.EventPlayerJoin;
import com.lucky.sql.SQLConnection;
import com.lucky.sql.SQLTables;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class LuckyEconomy extends JavaPlugin {


    @Override
    public void onEnable() {
        getCommand("dinheiro").setExecutor(new CommandDinheiro());
        Bukkit.getPluginManager().registerEvents(new EventPlayerJoin(), this);
        SQLConnection.startConnection(this);
        SQLTables.createTableMoney();

    }

    @Override
    public void onDisable() {
        SQLConnection.closeConnection();
    }

    public static LuckyEconomy getPlugin() {
        return getPlugin(LuckyEconomy.class);
    }

}
