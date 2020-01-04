package com.lucky.commands;

import com.lucky.events.EventPlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.lucky.managers.MoneyManager.*;
import static com.lucky.utils.MessageUtils.prefix;
import static com.lucky.utils.MoneyFormatter.format;

public class CommandDinheiro implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if (args.length == 0) {
            p.sendMessage("");
            p.sendMessage("§2» §a/dinheiro ver|/dinheiro ver [player] §a- §2(Vê o seu dinheiro)");
            p.sendMessage("§2» §a/dinheiro pagar [player] [quantidade] §a- §2(Pagar um player)");
            p.sendMessage("");
            if (p.hasPermission("LuckyEconomy.Staff")) {
                p.sendMessage("§4» §c/dinheiro setar [player] [quantia] §4- §c(Setar dinheiro para um player) ");
                p.sendMessage("§4» §c/dinheiro remover [player] [quantia] §4- §c(Remover dinheiro de um player) ");
                p.sendMessage("§4» §c/dinheiro adicionar [player] [quantia] §4- §c(Adicionar dinheiro para um player) ");
                p.sendMessage("§4» §c/dinheiro deletar [player] §4- §c(Remover um player do Banco de Dados) ");
                p.sendMessage("");
            }
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("ver")) {
                p.sendMessage(prefix + "§fVocê tem §aR$" + format(getMoney(p.getName())));
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("ver")) {
                Player target = Bukkit.getPlayer(args[1]);
                p.sendMessage(prefix + target.getName() + " §ftem §aR$" + format(getMoney(target.getName())));
            }
            if (args[0].equalsIgnoreCase("deletar") && p.hasPermission("LuckyEconomy.Staff")) {
                if(!(p.hasPermission("LuckyEconomy.Staff"))) { p.sendMessage("Você não tem perm"); return false; }
                    Player target = Bukkit.getPlayer(args[1]);
                    if (EventPlayerJoin.contains(target.getName())) {
                        deletePlayer(target.getName());
                        p.sendMessage(prefix + "§fO Jogador §a " + target.getName() + " §ffoi removido do banco de dados.");
                        target.kickPlayer(prefix + "§fVocê foi removido do banco de dados por: §a" + p.getName());
                    } else {
                        p.sendMessage(prefix + "§fO Jogador §a" + target.getName() + "§fnão existe no banco de dados.");
                        return false;
                    }
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("pagar")) {
                Player target = Bukkit.getPlayer(args[1]);
                double amount = Double.parseDouble(args[2]);

                if (getMoney(p.getName()) < amount) {
                    p.sendMessage(prefix + "§fVocê precisa de mais §aR$" + (format(amount - getMoney(p.getName())) + " §fpara poder pagar o player §a" + target.getName()));
                    return false;
                }

                if (target == null) {
                    p.sendMessage("§cEste usuário está offline");
                    return false;
                }

                addMoney(target.getName(), amount);
                removeMoney(p.getName(), amount);

                p.sendMessage(prefix + "§fVocê enviou §aR$" + format(amount) + " §fpara §a" + target.getName());
                target.sendMessage(prefix + "§fVocê recebeu §aR$" + format(amount) + " §fde §a" + p.getName());
            }
        }
        if (!(p.hasPermission("LuckyEconomy.Staff"))) {
            p.sendMessage("§cVocê não tem permissão.");
            return false;
        }

        if (args[0].equalsIgnoreCase("setar")) {
            Player target = Bukkit.getPlayer(args[1]);
            double amount = Double.parseDouble(args[2]);

            setMoney(target.getName(), amount);

            p.sendMessage(prefix + "§fVocê setou o dinheiro de §a" + target.getName() + " §fpara §aR$" + format(amount));
        }

        if (args[0].equalsIgnoreCase("remover")) {
            Player target = Bukkit.getPlayer(args[1]);
            double amount = Double.parseDouble(args[2]);

            removeMoney(target.getName(), amount);

            p.sendMessage(prefix + "§fVocê removeu §aR$ " + format(amount) + " §fde §a" + target.getName());
        }
        if(args[0].equalsIgnoreCase("adicionar")) {
            Player target = Bukkit.getPlayer(args[1]);
            double amount = Double.parseDouble(args[2]);

             addMoney(target.getName(), amount);

            p.sendMessage(prefix + "§fVocê adicionou §aR$ " + format(amount) + " §fpara §a" + target.getName());
        }
        return false;
    }
}