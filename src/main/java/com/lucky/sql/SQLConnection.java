package com.lucky.sql;

import com.lucky.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    private static Connection con;
    public static String prefix = MessageUtils.prefix;

    private static void connectionStart(Plugin plugin) {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
        File sql = new File(plugin.getDataFolder(), "dinheiro.db");

        try {

            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + sql);
            Bukkit.getConsoleSender().sendMessage(prefix + "§fConexão com SQLite aberta com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (getConnection() != null) {
                con.close();
                Bukkit.getConsoleSender().sendMessage(prefix + "§cConexão com SQLite fechada com sucesso.");
            } else {
                Bukkit.getConsoleSender().sendMessage(MessageUtils.prefix + "§fA Conexão já está fechada!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void startConnection(Plugin plugin) {
        connectionStart(plugin);
    }


    public static Connection getConnection() {
        return con;
    }
}
