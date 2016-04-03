package me.violantic.sumo.map;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Created by Ethan on 3/21/2016.
 */
public interface Mat {

    /**
     * Get the location of the center of the Mat.
     * @return
     */
    Location getCenter();

    /**
     * Get the location of the first player's spawn.
     * @return
     */
    Location getOne();

    /**
     * Get the location of the second player's spawn.
     * @return
     */
    Location getTwo();

    /**
     * Get the material for the outer ring of the mat.
     * TODO
     * @return
     */
    Material getOutside();

    /**
     * Get the material for the inner ring of the mat.
     * @return
     */
    Material getInside();

    /**
     * Get the size for the Mat (Radius).
     * @return
     */
    int getSize();

    /**
     * Is the mat circular?
     * @return
     */
    boolean circular();

}
