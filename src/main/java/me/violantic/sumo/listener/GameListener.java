package me.violantic.sumo.listener;

import me.violantic.sumo.Sumo;
import me.violantic.sumo.handler.GameHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;


/**
 * Created by Ethan on 4/1/2016.
 */
public class GameListener implements Listener {

    /**
     * Listens for PvP'ing
     * @param event
     */
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();

            GameHandler gameManager = Sumo.getInstance().getGame();

            if (!gameManager.canPvP(damager)) {
                damager.sendMessage(Sumo.getInstance().getPrefix() + "You cannot attack that target.");
                event.setCancelled(true);
            } else if (gameManager.canPvP(damager) && !gameManager.canPvP(damaged)) {
                damager.sendMessage(Sumo.getInstance().getPrefix() + "You cannot attack another player while they are in a match!");
                event.setCancelled(true);
            } else if (gameManager.canPvP(damager) && gameManager.canPvP(damaged)) {
                damaged.setHealth(damaged.getMaxHealth());
                // ideally conditional. //
                if(damager.isSneaking() && !damaged.isSneaking()) {
                    // Critical knockback. //
                    damaged.setVelocity(damager.getLocation().getDirection().multiply(2D));
                }

                if(damager.isSneaking() && damaged.isSneaking()) {
                    // Well played. //
                    damaged.setVelocity(damager.getLocation().getDirection().multiply(2D));
                    damager.setVelocity(damaged.getLocation().getDirection().multiply(2D));
                }
            }
        }
    }

    /**
     * Listens for when a player wins/loses
     * @param event
     */
    @EventHandler
    public void onMove(PlayerDeathEvent event) {
        if(Sumo.getInstance().getGame().isPlaying(event.getEntity())) {
            Player loser = event.getEntity();
            Player winner = Sumo.getInstance().getGame().getOpponent(loser);

            loser.teleport(Sumo.getInstance().getSpawn());
            winner.teleport(Sumo.getInstance().getSpawn());

            Bukkit.broadcastMessage(Sumo.getInstance().getPrefix() + winner.getName() + "has won against " + loser.getName());

            Sumo.getInstance().getGame().pvp.remove(loser);
            Sumo.getInstance().getGame().pvp.remove(winner);

            if(Sumo.getInstance().getChallenges().challengerMap.containsKey(event.getEntity())) {
                // Player initially challenged someone else. //
                Sumo.getInstance().getMats().deactivate(Sumo.getInstance().getMats().reserved.get(event.getEntity()));
                Sumo.getInstance().getChallenges().challengerMap.remove(event.getEntity());
                Sumo.getInstance().getChallenges().challengedMap.remove(Sumo.getInstance().getGame().getOpponent(event.getEntity()));
            } else if (Sumo.getInstance().getChallenges().challengedMap.containsKey(event.getEntity())) {
                Sumo.getInstance().getMats().deactivate(Sumo.getInstance().getMats().reserved.get(Sumo.getInstance().getGame().getOpponent(event.getEntity())));
                Sumo.getInstance().getChallenges().challengedMap.remove(Sumo.getInstance().getGame().getOpponent(event.getEntity()));
                Sumo.getInstance().getChallenges().challengerMap.remove(Sumo.getInstance().getGame().getOpponent(event.getEntity()));
            }
        }
    }

}
