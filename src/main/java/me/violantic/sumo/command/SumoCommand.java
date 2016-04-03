package me.violantic.sumo.command;

import me.violantic.sumo.Sumo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Ethan on 4/1/2016.
 */
public class SumoCommand implements CommandExecutor {


    /**
     * Main command of the mini-mini game.
     * @param commandSender
     * @param command
     * @param label
     * @param args
     * @return
     */
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("sumo")) {
            if (commandSender instanceof Player) {

                Player player = (Player) commandSender;

                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("challenge")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null && target != player) {
                            if(Sumo.getInstance().getChallenges().challengerMap.containsKey(player)) {
                                player.sendMessage(Sumo.getInstance().getPrefix() + "You are already in a match.");
                                return false;
                            }
                            Sumo.getInstance().getChallenges().request.put(player, target);
                            player.sendMessage(Sumo.getInstance().getPrefix() + "You have challenged: " + target.getName());
                            target.sendMessage(Sumo.getInstance().getPrefix() + "You have been challenged by: " + player.getName());
                            target.sendMessage(ChatColor.GRAY + "/Sumo accept, /Sumo deny");
                        } else {
                            player.sendMessage(Sumo.getInstance().getPrefix() + "That player is offline.");
                        }
                    }
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("accept")) {
                        if (!Sumo.getInstance().getChallenges().request.containsValue(player)) {
                            player.sendMessage(Sumo.getInstance().getPrefix() + "You have not been challenged!");
                        }

                        // Get player that requested the sender. //
                        for (Player potentialRequester : Sumo.getInstance().getChallenges().request.keySet()) {
                            if (Sumo.getInstance().getChallenges().request.get(potentialRequester) == player) {
                                // The potential requester was selected. //
                                // Submit Challenge. //
                                player.sendMessage(Sumo.getInstance().getPrefix() + "You have accepted, finding an open mat.");
                                potentialRequester.sendMessage(Sumo.getInstance().getPrefix() + "You have accepted, finding an open mat.");
                                Sumo.getInstance().getChallenges().challenge(potentialRequester, player);
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("deny")) {
                        if (!Sumo.getInstance().getChallenges().request.containsKey(player)) {
                            player.sendMessage(Sumo.getInstance().getPrefix() + "You have not been challenged!");
                        }

                        player.sendMessage(Sumo.getInstance().getPrefix() + "You have denied the request.");
                    } else if (args[0].equalsIgnoreCase("regen")) {

                    } else if (args[0].equalsIgnoreCase("destroy")) {

                    }
                } else {
                    commandSender.sendMessage(Sumo.getInstance().getPrefix() + "Invalid arguments: /Sumo challenge, /Sumo accept, /Sumo deny");
                }
            }
            return true;
        }
        return false;
    }
}
