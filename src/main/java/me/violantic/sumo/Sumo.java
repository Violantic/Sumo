package me.violantic.sumo;

import me.violantic.sumo.command.SumoCommand;
import me.violantic.sumo.handler.ChallengeHandler;
import me.violantic.sumo.handler.GameHandler;
import me.violantic.sumo.handler.MatHandler;
import me.violantic.sumo.listener.GameListener;
import me.violantic.sumo.map.Mat;
import me.violantic.sumo.map.SumoMat;
import me.violantic.sumo.util.MatGenerator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Ethan on 3/21/2016.
 */
public class Sumo extends JavaPlugin implements Listener {

    /**
     * Instance of the plugin.
     */
    public static Sumo instance;

    /**
     * Handles everything challenge/request related.
     */
    public ChallengeHandler challenge;

    /**
     * Handles everything mat related.
     */
    public MatHandler mat;

    /**
     * Handles everything game related.
     */
    public GameHandler game;

    /**
     * When the server starts.
     */
    @Override
    public void onEnable() {
        instance = this;

        challenge = new ChallengeHandler(this);
        mat = new MatHandler(this);
        game = new GameHandler(this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        getServer().getPluginCommand("sumo").setExecutor(new SumoCommand());

        initMat();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new GameListener(), this);
    }

    /**
     * When the server shuts down.
     */
    @Override
    public void onDisable() {
        removeMat();
    }

    /**
     * Instance of the plugin.
     */
    public static Sumo getInstance() {
        return instance;
    }

    /**
     * Handles all player challenges/requests.
     * @return
     */
    public ChallengeHandler getChallenges() {
        return this.challenge;
    }

    /**
     * Handles all mats.
     * @return
     */
    public MatHandler getMats() {
        return this.mat;
    }

    /**
     * Handles everything game related.
     * @return
     */
    public GameHandler getGame() {
        return this.game;
    }

    /**
     * Get the "lobby" of the mini-mini game.
     * @return
     */
    public Location getSpawn() {
        ConfigurationSection section = getConfig().getConfigurationSection("spawn");
        return new Location(Bukkit.getWorld("world"), section.getInt("x"), section.getInt("y"), section.getInt("z"));
    }

    /**
     * The prefix that the plugin uses for messaging.
     * @return
     */
    public String getPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Sumo" + ChatColor.GRAY + "]: " + ChatColor.RESET;
    }

    /**
     * Initialize and build the Mats.
     */
    public void initMat() {
        for(String key : getConfig().getConfigurationSection("mats").getKeys(false)) {
            ConfigurationSection section = getConfig().getConfigurationSection("mats." + key);
            int x, y, z;
            x = section.getInt("x");
            y = section.getInt("y");
            z = section.getInt("z");

            int x1, y1, z1;
            x1 = section.getInt("one-x");
            y1 = section.getInt("one-y");
            z1 = section.getInt("one-z");
            Location one = Bukkit.getWorld("world").getBlockAt(x1, y1, z1).getLocation();

            int x2, y2, z2;
            x2 = section.getInt("two-x");
            y2 = section.getInt("two-y");
            z2 = section.getInt("two-z");
            Location two = Bukkit.getWorld("world").getBlockAt(x2, y2, z2).getLocation();

            Mat mat = new SumoMat(Bukkit.getWorld("world").getBlockAt(x, y, z).getLocation(), one, two);
            getMats().reservations.put(mat, false);
            MatGenerator.spawn(mat);
        }
    }

    /**
     * Remove all of the mats.
     */
    public void removeMat() {
        for(Block b : MatGenerator.blockList) {
            b.setType(Material.AIR);
        }
    }

}
