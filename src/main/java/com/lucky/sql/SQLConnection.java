package com.lucky.sql;

import com.lucky.utils.Utilities;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.sqlite.JDBC;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

@Getter
public abstract class SQLConnection {

    private Connection con;

    private Plugin plugin;
    private File SQLFile;
    private String fileName;

    public SQLConnection(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.SQLFile = new File(plugin.getDataFolder(), fileName);
    }

    @SneakyThrows
    public boolean openConnection() {
        if (hasConnection()) return true;
        if(!getSQLFile().exists()) plugin.saveResource(fileName, false);

        DriverManager.registerDriver(new JDBC());
        con = DriverManager.getConnection("jdbc:sqlite:" + SQLFile);
        return !con.isClosed();
    }

    @SneakyThrows
    public void closeConnection() {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        if (hasConnection()) {
            con.close();
            sender.sendMessage(Utilities.prefix + "§cConexão com SQLite fechada com sucesso.");
        } else {
            sender.sendMessage(Utilities.prefix + "§fA Conexão já está fechada!");
        }
    }

    @SneakyThrows
    public boolean hasConnection() {
        return getCon() != null && !getCon().isClosed();
    }
}
