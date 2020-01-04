package com.lucky.events;

import com.lucky.managers.MoneyManager;
import com.lucky.sql.SQLConnection;
import com.lucky.sql.SQLTables;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class EventPlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(!(contains(e.getPlayer().getName()))) {
            MoneyManager.newAccount(e.getPlayer().getName());
        }
    }

        public static boolean contains(String player){
            PreparedStatement stm;
            try {
                stm = SQLConnection.getConnection().prepareStatement("SELECT * FROM `dinheiro` WHERE `player` = ?");
                stm.setString(1, player.toLowerCase());
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    return true;
                }
                return false;
            } catch (SQLException e) {
                return false;
            }
        }
}
