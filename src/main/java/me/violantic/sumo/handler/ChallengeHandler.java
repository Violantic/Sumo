package me.violantic.sumo.handler;

import me.violantic.sumo.Sumo;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ethan on 4/1/2016.
 */
public class ChallengeHandler {

    /**
     * Variables.
     */
    public Map<Player, Player> request = new HashMap<Player, Player>();
    public Map<Player, Player> challengerMap = new HashMap<Player, Player>();
    public Map<Player, Player> challengedMap = new HashMap<Player, Player>();
    public Sumo instance;

    /**
     * Constructor
     * @param instance
     */
    public ChallengeHandler(Sumo instance) {
        this.instance = instance;
    }

    /**
     * Finalize a challenge between two players.
     * @param challenger
     * @param target
     */
    public void challenge(Player challenger, Player target) {
        challengerMap.put(challenger, target);
        challengedMap.put(target, challenger);

        // Find open mat. //
        Sumo.getInstance().getMats().find(challenger, target);
    }

    /**
     * Find the targeted player for a player.
     * @param challenger
     * @return
     */
    public Player getTarget(Player challenger) {
        if(!challengerMap.containsKey(challenger)) return null;

        return challengerMap.get(challenger);
    }

    /**
     * Get a player's challenger.
     * @param target
     * @return
     */
    public Player getChalleneger(Player target) {
        if(!challengedMap.containsKey(target)) return null;

        return challengedMap.get(target);
    }

}
