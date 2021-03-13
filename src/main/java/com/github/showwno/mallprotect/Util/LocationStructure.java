package com.github.showwno.mallprotect.Util;


import org.bukkit.Location;

public class LocationStructure {
    private final int lowX;
    private final int highX;
    private final int lowY;
    private final int highY;
    private final int lowZ;
    private final int highZ;

    public LocationStructure(Location loc1, Location loc2){
        lowX = Math.min(loc1.getBlockX(),loc2.getBlockX());
        highX = Math.max(loc1.getBlockX(),loc2.getBlockX());
        lowY = Math.min(loc1.getBlockY(),loc2.getBlockY());
        highY = Math.max(loc1.getBlockY(),loc2.getBlockY());
        lowZ = Math.min(loc1.getBlockZ(),loc2.getBlockZ());
        highZ = Math.max(loc1.getBlockZ(),loc2.getBlockZ());
    }

    public boolean checkLocation(Location target){
        if(lowX <= target.getBlockX() && target.getBlockX() <= highX ){
            if(lowZ <= target.getBlockZ() && target.getBlockZ() <= highZ ){
                if(lowY <= target.getBlockY() && target.getBlockY() <= highY ){
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
