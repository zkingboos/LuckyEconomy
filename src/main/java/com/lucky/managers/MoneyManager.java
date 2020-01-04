package com.lucky.managers;

import com.lucky.sql.SQLConnection;
import com.lucky.utils.MessageUtils;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MoneyManager {

    private static Connection con = SQLConnection.getConnection();
    private static ConsoleCommandSender sc = MessageUtils.sc;
    private static String prefix = MessageUtils.prefix;
    private static PreparedStatement stm;


    public static void newAccount(String player) {

        try {
            stm = con.prepareStatement("INSERT INTO `dinheiro`(`player`, `quantia`) VALUES (?,?)");
            stm.setString(1, player.toLowerCase());
            stm.setDouble(2, 0);
            stm.executeUpdate();
            sc.sendMessage(prefix + " §fO player §a" + player + " §f foi criado com sucesso.");
        } catch (SQLException e) {
            sc.sendMessage(prefix + "§cNão foi possivel inserir o player: §f" + player + "§a no banco de dados!");
        }
    }

    public static void setMoney(String player, Double quantia) {
            try {
                stm = con.prepareStatement("UPDATE `dinheiro` SET `quantia` = ? WHERE `player` = ?");
                stm.setDouble(1, quantia);
                stm.setString(2, player.toLowerCase());
                stm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                sc.sendMessage(prefix + "§cNão foi possivel setar o dinheiro do jogador");
            }
    }

    public static Double getMoney(String player){
            try {
                stm = con.prepareStatement("SELECT * FROM `dinheiro` WHERE `player` = ?");
                stm.setString(1, player.toLowerCase());
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    return rs.getDouble("quantia");
                }
                return 0.0;
            } catch (SQLException e) {
                e.printStackTrace();
                return 0.0;
            }
    }

    public static void addMoney(String player, Double quantia){
            setMoney(player, getMoney(player) + quantia);
    }

    public static void removeMoney(String player, Double quantia){
            setMoney(player, getMoney(player) - quantia);
    }

    public static void deletePlayer(String player){

            try {
                stm = con.prepareStatement("DELETE FROM `dinheiro` WHERE `player` = ?");
                stm.setString(1, player.toLowerCase());
                stm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                sc.sendMessage(prefix + "§cNão foi possivel remover o jogador §f" + player + "§c do banco de dados!");
            }
    }

    public static List<String> getTops(){

        List<String> tops = new ArrayList<>();
        try {
            stm = con.prepareStatement("SELECT * FROM `dinheiro` ORDER BY `quantia` DESC");
            ResultSet rs = stm.executeQuery();
            int i = 0;
            while (rs.next()) {
                if (i <= 10){
                    i++;
                    tops.add("§f" + i + "º §3" + rs.getString("player") + ":§b " + rs.getDouble("quantia"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sc.sendMessage(prefix + "§cNão foi possivel carregar o top dinheiro");
        }
        return tops;
    }
}
