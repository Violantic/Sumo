package me.violantic.sumo.map;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Created by Ethan on 3/21/2016.
 */
public class SumoMat implements Mat {

    /**
     * Location that is the center of the map.
     */
    public Location location;

    /**
     * Location that is the first player's spawn.
     */
    public Location one;

    /**
     * Location that is the second player's spawn.
     */
    public Location two;

    /**
     * Constructor
     * @param location
     * @param one
     * @param two
     */
    public SumoMat(Location location, Location one, Location two) {
        this.location = location;
        this.one = one;
        this.two = two;
    }

    /**
     * Get the center of the mat.
     * @return
     */
    public Location getCenter() {
        return this.location;
    }

    /**
     * Get the first player's spawn location.
     * @return
     */
    public Location getOne() { return this.one; }

    /**
     * Get the second player's spawn location.
     * @return
     */
    public Location getTwo() { return this.two; }

    /**
     * Get the material for the outer ring of the mat.
     * @return
     */
    public Material getOutside() {
        return Material.COBBLESTONE;
    }

    /**
     * Get the material for the inside of the mat.
     * @return
     */
    public Material getInside() {
        return Material.WOOL;
    }

    /**
     * Get the size of the mat (Radius).
     * @return
     */
    public int getSize() {
        return 6;
    }

    /**
     * The SumoMat is not circular.
     * @return
     */
    public boolean circular() {
        return false;
    }
}
