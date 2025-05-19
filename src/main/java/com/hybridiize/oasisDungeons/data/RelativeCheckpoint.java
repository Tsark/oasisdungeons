package com.hybridiize.oasisDungeons.data;

public class RelativeCheckpoint {
    private String id; // Unique identifier for this checkpoint within the dungeon
    private RelativeRegion region; // The area that activates this checkpoint
    private RelativeLocation spawnPoint; // Where players respawn if this is their last checkpoint

    // Constructors
    public RelativeCheckpoint(String id, RelativeRegion region, RelativeLocation spawnPoint) {
        this.id = id;
        this.region = region;
        this.spawnPoint = spawnPoint;
    }

    // No-arg constructor for deserialization
    public RelativeCheckpoint() {
    }

    // Getters
    public String getId() {
        return id;
    }

    public RelativeRegion getRegion() {
        return region;
    }

    public RelativeLocation getSpawnPoint() {
        return spawnPoint;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setRegion(RelativeRegion region) {
        this.region = region;
    }

    public void setSpawnPoint(RelativeLocation spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    @Override
    public String toString() {
        return "RelativeCheckpoint{" +
                "id='" + id + '\'' +
                ", region=" + region +
                ", spawnPoint=" + spawnPoint +
                '}';
    }
}