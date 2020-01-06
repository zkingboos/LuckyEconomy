package com.lucky.managers;

import com.lucky.sql.SQLProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.stream.Stream;


@RequiredArgsConstructor
public class MoneyManager {

    private final SQLProvider provider;

    public int setMoney(UUID id, Double quantia) {
        if(hasAccount(id)) provider.update("update dinheiro set quantia=? where player=?", quantia, id.toString());
        else provider.update("insert into dinheiro(player,quantia) values (?,?)", id.toString(), quantia);
        return 0;
    }

    public boolean hasAccount(UUID id) {
        return provider.query(
                "select player from dinheiro where player=?",
                set -> true,
                id.toString()
        ).orElse(false);
    }

    public Double getMoney(UUID id) {
        return provider.query(
                "select quantia from dinheiro where player=?",
                set -> set.getDouble("quantia"),
                id.toString()
        ).orElse(0.0);
    }

    public int addMoney(UUID id, Double quantia) {
        return setMoney(id, getMoney(id) + quantia);
    }

    public int removeMoney(UUID id, Double quantia) {
        return setMoney(id, getMoney(id) - quantia);
    }

    public int deletePlayer(UUID id) {
        return provider.update("delete from dinheiro where player=?", id.toString());
    }

    public Stream<TemporaryUser> getTops() {
        return provider.map("SELECT * FROM `dinheiro` ORDER BY `quantia` DESC LIMIT 10", it -> {
                String id = it.getString("player");
                Double money = it.getDouble("quantia");
                return new TemporaryUser(id, money);
        }).get();
    }

    @AllArgsConstructor @Getter
    public class TemporaryUser {
        private String id;
        private Double money;
    }
}
