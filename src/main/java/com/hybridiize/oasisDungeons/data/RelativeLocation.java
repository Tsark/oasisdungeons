package com.hybridiize.oasisDungeons.data;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector; // For the pasteOrigin

public class RelativeLocation {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    // Constructors
    public RelativeLocation(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public RelativeLocation(double x, double y, double z) {
        this(x, y, z, 0f, 0f); // Default yaw and pitch
    }

    // No-arg constructor for deserialization (e.g., by SnakeYAML)
    public RelativeLocation() {
    }

    // Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    // Setters (useful for configuration/deserialization)
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    /**
     * Converts this relative location to an absolute Bukkit Location.
     * @param world The world the absolute location will be in.
     * @param pasteOrigin The origin point (minimum corner) where the schematic was pasted.
     * @return The absolute Bukkit Location.
     */
    public Location toAbsoluteLocation(World world, Vector pasteOrigin) {
        return new Location(world, pasteOrigin.getX() + this.x, pasteOrigin.getY() + this.y, pasteOrigin.getZ() + this.z, this.yaw, this.pitch);
    }

    /**
     * Creates a RelativeLocation from an absolute Bukkit Location and a paste origin.
     * This is useful when admins are defining points within a built structure.
     * @param absoluteLocation The absolute location (e.g., where the admin is standing).
     * @param pasteOrigin The origin point (minimum corner) of the schematic's build area.
     * @return A new RelativeLocation.
     */
    public static RelativeLocation fromAbsoluteLocation(Location absoluteLocation, Vector pasteOrigin) {
        double relX = absoluteLocation.getX() - pasteOrigin.getX();
        double relY = absoluteLocation.getY() - pasteOrigin.getY();
        double relZ = absoluteLocation.getZ() - pasteOrigin.getZ();
        return new RelativeLocation(relX, relY, relZ, absoluteLocation.getYaw(), absoluteLocation.getPitch());
    }

    @Override
    public String toString() {
        return "RelativeLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                '}';
    }
}