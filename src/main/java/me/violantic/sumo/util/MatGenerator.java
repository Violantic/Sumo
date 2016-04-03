package me.violantic.sumo.util;

import me.violantic.sumo.map.Mat;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 3/21/2016.
 */
public class MatGenerator {

    public static List<Block> blockList = new ArrayList<Block>();

    public MatGenerator() {}

    /**
     * Spawn a Mat
     * TODO - Generate a mat if Mat::isCircular() == true
     * @param mat
     */
    public static void spawn(Mat mat) {
        if(!mat.circular()){
            Location quadCenter = mat.getCenter();
            World world = quadCenter.getWorld();
            int size = mat.getSize();

            for(int x = quadCenter.getBlockX() - size; x < quadCenter.getX() + size; x++) {
                for(int y = quadCenter.getBlockY() - size; y < quadCenter.getY() + size; y++) {
                    for(int z = quadCenter.getBlockZ() - size; z < quadCenter.getZ() + size; z++) {
                        world.getBlockAt(x, quadCenter.getBlockY() - 1, z).setType(mat.getInside());
                        blockList.add(world.getBlockAt(x, quadCenter.getBlockY() - 1, z));
                    }
                }
            }

        } else {

        }
    }

}
