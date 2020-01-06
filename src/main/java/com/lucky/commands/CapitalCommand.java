package com.lucky.commands;

import com.lucky.managers.MoneyManager;
import com.lucky.utils.Utilities;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class CapitalCommand implements CommandExecutor {

    private final MoneyManager manager;

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
            return false;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("ver")) {
                p.sendMessage(Utilities.prefix + "§fVocê tem §aR$" +
                        Utilities.format(manager.getMoney(p.getUniqueId())));
            }
            if (args[0].equalsIgnoreCase("ranking")) {
                //p.sendMessage("");
                //p.sendMessage("       §a[LuckyRanking] §7- §fTop 10 Dinheiro \n ");
                manager.getTops().forEach(r -> {
                    p.sendMessage(r.getId());
                    p.sendMessage(r.getMoney() + "");
                });
                //p.sendMessage("");

            }
            return false;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("ver")) {
                Player target = Bukkit.getPlayer(args[1]);
                p.sendMessage(Utilities.prefix + target.getName() + " §ftem §aR$" + Utilities.format(manager.getMoney(target.getUniqueId())));
            }
            if (args[0].equalsIgnoreCase("deletar") && p.hasPermission("LuckyEconomy.Staff")) {
                if (!(p.hasPermission("LuckyEconomy.Staff"))) {
                    p.sendMessage("Você não tem perm");
                    return false;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (manager.hasAccount(target.getUniqueId())) {
                    manager.deletePlayer(target.getUniqueId());
                    p.sendMessage(Utilities.prefix + "§fO Jogador §a " + target.getName() + " §ffoi removido do banco de dados.");
                    target.kickPlayer(Utilities.prefix + "§fVocê foi removido do banco de dados por: §a" + p.getName());
                } else {
                    p.sendMessage(Utilities.prefix + "§fO Jogador §a" + target.getName() + "§fnão existe no banco de dados.");
                }
            }
            return false;
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("pagar")) {
                Player target = Bukkit.getPlayer(args[1]);
                double amount = Double.parseDouble(args[2]);

                if (manager.getMoney(p.getUniqueId()) < amount) {
                    p.sendMessage(Utilities.prefix + "§fVocê precisa de mais §aR$" +
                            (Utilities.format(
                                    amount - manager.getMoney(p.getUniqueId())
                            ) + " §fpara poder pagar o player §a" + target.getName()));
                    return false;
                }

                if (target == null) {
                    p.sendMessage("§cEste usuário está offline");
                    return false;
                }

                manager.addMoney(target.getUniqueId(), amount);
                manager.removeMoney(p.getUniqueId(), amount);

                p.sendMessage(Utilities.prefix + "§fVocê enviou §aR$" + Utilities.format(amount) + " §fpara §a" + target.getName());
                target.sendMessage(Utilities.prefix + "§fVocê recebeu §aR$" + Utilities.format(amount) + " §fde §a" + p.getName());
            }
            if (!(p.hasPermission("LuckyEconomy.Staff"))) {
                p.sendMessage("§cVocê não tem permissão.");
                return false;
            }

            if (args[0].equalsIgnoreCase("setar")) {
                Player target = Bukkit.getPlayer(args[1]);
                double amount = Double.parseDouble(args[2]);

                manager.setMoney(target.getUniqueId(), amount);

                p.sendMessage(Utilities.prefix + "§fVocê setou o dinheiro de §a" + target.getName() + " §fpara §aR$" + Utilities.format(amount));
            }

            if (args[0].equalsIgnoreCase("remover")) {
                Player target = Bukkit.getPlayer(args[1]);
                double amount = Double.parseDouble(args[2]);

                manager.removeMoney(target.getUniqueId(), amount);

                p.sendMessage(Utilities.prefix + "§fVocê removeu §aR$ " + Utilities.format(amount) + " §fde §a" + target.getName());
            }
            if (args[0].equalsIgnoreCase("adicionar")) {
                Player target = Bukkit.getPlayer(args[1]);
                double amount = Double.parseDouble(args[2]);

                manager.addMoney(target.getUniqueId(), amount);

                p.sendMessage(Utilities.prefix + "§fVocê adicionou §aR$ " + Utilities.format(amount) + " §fpara §a" + target.getName());
            }
        }
        return false;
    }
}