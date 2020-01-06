package com.lucky;

import com.lucky.commands.CapitalCommand;
import com.lucky.managers.MoneyManager;
import com.lucky.sql.SQLProvider;
import com.lucky.sql.SQLTables;
import com.lucky.utils.Utilities;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class LuckyEconomy extends JavaPlugin {

    public LuckyEconomy plugin;
    private SQLProvider sql;
    private SQLTables tables;
    private MoneyManager manager;

    @Override
    public void onEnable() {
        plugin = this;
        sql = new SQLProvider(this, "dinheiro.db");
        manager = new MoneyManager(sql);

        ConsoleCommandSender console = Bukkit.getConsoleSender();

        if(sql.openConnection()) {
            console.sendMessage(Utilities.prefix + "§fConexão com SQLite foi aberta com sucesso.");
            tables = new SQLTables(sql).createMoneyTable();
        }

        getCommand("dinheiro").setExecutor(new CapitalCommand(manager));
    }

    @Override
    public void onDisable() {
        sql.closeConnection();
    }
}
