package me.violantic.sumo.handler;

import me.violantic.sumo.Sumo;
import me.violantic.sumo.map.Mat;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ethan on 4/1/2016.
 */
public class MatHandler {

    /**
     * Variables.
     */
    public Map<Mat, Boolean> reservations = new HashMap<Mat, Boolean>();
    public Map<Player, Mat> reserved = new HashMap<Player, Mat>();
    public Sumo instance;

    /**
     * Constructor
     * @param instance
     */
    public MatHandler(Sumo instance) {
        this.instance = instance;
    }

    /**
     * Find an open mat for the players.
     * @param challenger
     * @param challenged
     */
    public void find(final Player challenger, final Player challenged) {
        for(Mat mat : reservations.keySet()) {
            if(reservations.get(mat) == false) {
                challenger.teleport(mat.getOne());
                challenged.teleport(mat.getTwo());

                challenger.sendMessage(Sumo.getInstance().getPrefix() + "Found a mat, sending you.");
                challenged.sendMessage(Sumo.getInstance().getPrefix() + "Found a mat, sending you.");
                challenger.sendMessage(Sumo.getInstance().getPrefix() + "PvP enables in 5 seconds.");
                challenged.sendMessage(Sumo.getInstance().getPrefix() + "PvP enables in 5 seconds.");
                challenger.teleport(mat.getOne());
                challenged.teleport(mat.getTwo());

                reservations.put(mat, true);
                reserved.put(challenger, mat);

                new BukkitRunnable() {
                    public void run() {
                        Sumo.getInstance().getGame().pvp.addAll(new ArrayList<Player>() {
                            {
                                this.add(challenger);
                                this.add(challenged);
                                challenger.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 180, 2));
                                challenged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*180, 2));
                            }
                        });
                    }
                }.runTaskLater(Sumo.getInstance(), 20*5);

                break;
            } else {
                challenger.sendMessage(Sumo.getInstance().getPrefix() + "There were no open mats, the challenge was cancelled.");
                challenged.sendMessage(Sumo.getInstance().getPrefix() + "There were no open mats, the challenge was cancelled.");
            }
        }
    }

    /**
     * Find if a mat is taken.
     * @param mat
     * @return
     */
    public boolean isTaken(Mat mat) {
        return reservations.get(mat);
    }

    /**
     * Un-reserve a mat.
     * @param mat
     */
    public void deactivate(Mat mat) {
        reservations.put(mat, false);
    }



}
