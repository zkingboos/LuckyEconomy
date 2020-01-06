package com.lucky.sql;

import com.lucky.utils.Utilities;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SQLTables {

    private final SQLProvider provider;

    public SQLTables createMoneyTable(){
        int result = provider.update("CREATE TABLE IF NOT EXISTS dinheiro ("+
                "player varchar(50) PRIMARY KEY, " +
                "quantia DOUBLE" +
                ")");

        String msg = result == -1 ?
                "§cNão foi possivel carregar a tabela ou ela já existe."
                : "§aTabela carregada";

        Utilities.sc.sendMessage(Utilities.prefix + msg);
        return this;
    }
}
