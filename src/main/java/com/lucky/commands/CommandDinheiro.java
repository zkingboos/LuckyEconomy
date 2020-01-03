package com.lucky.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.lucky.managers.MoneyManager.getMoney;
import static com.lucky.managers.MoneyManager.setMoney;
import static com.lucky.utils.MessageUtils.prefix;
import static com.lucky.utils.MoneyFormatter.format;

public class CommandDinheiro implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if (args.length == 0) {
            p.sendMessage("");
            p.sendMessage("§2» §a/dinheiro ver|/dinheiro ver [player] §a- §2(Vê o seu dinheiro)");
            p.sendMessage("§2» §a/dinheiro pagar [quantidade] §a- §2(Pagar um player)");
            p.sendMessage("");
            if (p.hasPermission("LuckyEconomy.Staff")) {
                p.sendMessage("§4» §c/dinheiro setar [player] §4- §c(Setar dinheiro para um player) ");
                p.sendMessage("§4» §c/dinheiro remover [player] §4- §c(Remover dinheiro de um player) ");
                p.sendMessage("§4» §c/dinheiro adicionar [player] §4- §c(Adicionar dinheiro para um player) ");
                p.sendMessage("");
            }
            return false;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("ver")) {
                p.sendMessage(prefix + "§fVocê tem §aR$" + getMoney(p) + " §freais.");
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("ver")) {
                String targetName = args[1];
                Player target = Bukkit.getPlayer(targetName);
                if (target != null) {
                    p.sendMessage(prefix + "§a" + target.getName() + " §ftem §aR$" + format(getMoney(target)) + " §freais.");
                } else {
                    p.sendMessage("§cEste usuário está offline");
                    return false;
                }
                return false;
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("setar")) {
                if (p.hasPermission("LuckyEconomy.Staff")) {
                    String targetName = args[1];
                    double amount = Double.parseDouble(args[2]);
                    Player target = Bukkit.getPlayer(targetName);
                    if (target != null) {
                        setMoney(target, amount);
                        p.sendMessage(prefix + "§fVocê setou o dinheiro de §a" + target.getName() + " §fpara §aR$" + format(amount));
                    } else {
                        p.sendMessage("§cEste usuário está offline.");
                        return false;
                    }
                }
            }
            if (args[0].equalsIgnoreCase("adicionar")) {
                if (p.hasPermission("LuckyEconomy.Staff")) {
                    String targetName = args[1];
                    double amount = Double.parseDouble(args[2]);
                    Player target = Bukkit.getPlayer(targetName);
                    if (target != null) {
                        setMoney(target, (int) (getMoney(target) + amount));
                        p.sendMessage(prefix + "§fVocê adicionou §aR$" + format(amount) + " §fna conta de §a" + target.getName());
                    } else {
                        p.sendMessage("§cEste usuário está offline.");
                        return false;
                    }
                }
            }
        }
        return false;
    }
}