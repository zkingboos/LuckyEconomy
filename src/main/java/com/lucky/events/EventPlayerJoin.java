package com.lucky.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.lucky.managers.MoneyManager.checkPlayer;
import static com.lucky.managers.MoneyManager.newAccount;

public class EventPlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        checkPlayer(e.getPlayer());
    }

}
