package me.violantic.sumo.handler;

import me.violantic.sumo.Sumo;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 4/1/2016.
 */
public class GameHandler {

    /**
     * List that keeps track of who is currently in a match.
     */
    public List<Player> pvp = new ArrayList<Player>();

    /**
     * Instance of plugin's main class.
     */
    public Sumo instance;

    /**
     * Constructor.
     * @param instance
     */
    public GameHandler(Sumo instance) {
        this.instance = instance;
    }

    /**
     * Can a specific player pvp?
     * @param player
     * @return
     */
    public boolean canPvP(Player player) {
        return (pvp.contains(player));
    }

    /**
     * Is a specific player playing?
     * @param player
     * @return
     */
    public boolean isPlaying(Player player) {
        return (pvp.contains(player));
    }

    /**
     * Find the opponent of a player.
     * @param player
     * @return
     */
    public Player getOpponent(Player player) {
        // Find opposite player. //
        if(Sumo.getInstance().getChallenges().challengerMap.containsKey(player)) {
            // Player initially challenged someone else. //
            return Sumo.getInstance().getChallenges().challengerMap.get(player);
        } else if (Sumo.getInstance().getChallenges().challengedMap.containsKey(player)) {
            // Player was challenged by someone else. //
            return Sumo.getInstance().getChallenges().challengedMap.get(player);
        }
        return null;
    }



}
