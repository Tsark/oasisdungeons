package com.hybridiize.oasisDungeons.data;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector; // For block vectors and pasteOrigin

public class RelativeRegion {
    private RelativeLocation pos1; // One corner of the cuboid
    private RelativeLocation pos2; // The opposite corner

    // Min and Max relative coordinates, calculated for efficient checks
    private transient double minX, minY, minZ;
    private transient double maxX, maxY, maxZ;


    // Constructors
    public RelativeRegion(RelativeLocation pos1, RelativeLocation pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        calculateBounds();
    }

    // No-arg constructor for deserialization
    public RelativeRegion() {
    }

    // Getters
    public RelativeLocation getPos1() {
        return pos1;
    }

    public RelativeLocation getPos2() {
        return pos2;
    }

    // Setters (ensure bounds are recalculated if positions change)
    public void setPos1(RelativeLocation pos1) {
        this.pos1 = pos1;
        if (this.pos2 != null) calculateBounds();
    }

    public void setPos2(RelativeLocation pos2) {
        this.pos2 = pos2;
        if (this.pos1 != null) calculateBounds();
    }

    /**
     * Calculates the minimum and maximum X, Y, Z coordinates for the region.
     * This should be called after pos1 or pos2 are set or changed.
     */
    public void calculateBounds() {
        if (pos1 == null || pos2 == null) return; // Not enough data yet

        this.minX = Math.min(pos1.getX(), pos2.getX());
        this.minY = Math.min(pos1.getY(), pos2.getY());
        this.minZ = Math.min(pos1.getZ(), pos2.getZ());
        this.maxX = Math.max(pos1.getX(), pos2.getX());
        this.maxY = Math.max(pos1.getY(), pos2.getY());
        this.maxZ = Math.max(pos1.getZ(), pos2.getZ());
    }

    /**
     * Checks if an absolute location is within this relative region,
     * given the paste origin of the dungeon instance.
     *
     * @param absoluteLocation The location to check.
     * @param pasteOrigin The origin point (minimum corner) where the schematic was pasted.
     * @return True if the location is within the region, false otherwise.
     */
    public boolean contains(Location absoluteLocation, Vector pasteOrigin) {
        if (pos1 == null || pos2 == null) return false; // Region not fully defined

        // If bounds haven't been calculated (e.g., after deserialization), calculate them.
        // This is a safety, ideally they are calculated on setPos1/setPos2 or after loading.
        if (minX == 0 && maxX == 0 && minY == 0 && maxY == 0 && minZ == 0 && maxZ == 0 &&
                (pos1.getX() != 0 || pos2.getX() != 0)) { // A crude check to see if bounds might be uninitialized
            calculateBounds();
        }

        double locX = absoluteLocation.getX() - pasteOrigin.getX();
        double locY = absoluteLocation.getY() - pasteOrigin.getY();
        double locZ = absoluteLocation.getZ() - pasteOrigin.getZ();

        return locX >= minX && locX <= maxX &&
                locY >= minY && locY <= maxY &&
                locZ >= minZ && locZ <= maxZ;
    }

    /**
     * Creates a RelativeRegion from two absolute Bukkit Locations and a paste origin.
     * @param absLoc1 One corner of the absolute region.
     * @param absLoc2 The opposite corner of the absolute region.
     * @param pasteOrigin The origin point (minimum corner) of the schematic's build area.
     * @return A new RelativeRegion.
     */
    public static RelativeRegion fromAbsoluteLocations(Location absLoc1, Location absLoc2, Vector pasteOrigin) {
        RelativeLocation relPos1 = RelativeLocation.fromAbsoluteLocation(absLoc1, pasteOrigin);
        RelativeLocation relPos2 = RelativeLocation.fromAbsoluteLocation(absLoc2, pasteOrigin);
        return new RelativeRegion(relPos1, relPos2);
    }

    @Override
    public String toString() {
        return "RelativeRegion{" +
                "pos1=" + (pos1 != null ? pos1.toString() : "null") +
                ", pos2=" + (pos2 != null ? pos2.toString() : "null") +
                '}';
    }
}