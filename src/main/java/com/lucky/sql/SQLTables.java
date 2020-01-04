package com.lucky.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.lucky.utils.MessageUtils.prefix;
import static com.lucky.utils.MessageUtils.sc;

public class SQLTables {

    protected static void createTable(){
        Connection con = SQLConnection.getConnection();
        if (con != null) {
            PreparedStatement stm;
            try {
                stm = con.prepareStatement("CREATE TABLE IF NOT EXISTS `dinheiro` (`id` INTEGER PRIMARY KEY AUTOINCREMENT,`player` TEXT NULL,`quantia` DOUBLE NULL);");
                stm.executeUpdate();
                sc.sendMessage(prefix + "§aTabela carregada");
            } catch (SQLException e) {
                e.printStackTrace();
                sc.sendMessage(prefix + "§cNão foi possivel carregar a tabela");
            }
        }
    }

    public static void createTableMoney() { createTable(); }
}
