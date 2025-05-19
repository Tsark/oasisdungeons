package com.hybridiize.oasisDungeons.data;

import java.util.ArrayList;
import java.util.List;

public class DungeonTemplate {
    private String id; // Unique identifier, e.g., "crypt_of_elders"
    private String displayName; // User-friendly name
    private String schematicName; // e.g., "crypt_of_elders.schem" (we'll decide on storage path later)

    private RelativeLocation startLocation; // Relative to schematic origin
    private RelativeRegion endRegion;       // Relative to schematic origin

    private List<RelativeCheckpoint> checkpoints;
    private List<DungeonTrigger> dungeonTriggers; // Replaces simple interactiveRegions

    private long timeLimitSeconds;
    private int minPlayers;
    private int maxPlayers;

    // For admin setup: the absolute min corner of the selection when defining the schematic region
    // This is NOT saved in the final dungeon template file for players, but used during setup.
    // Or, we might not need to store this if schematics are always saved from 0,0,0 relative.
    // For now, let's assume schematics are self-contained.
    // We'll also need a way to store the original WorldEdit selection for saving the schematic.

    // Constructor
    public DungeonTemplate(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
        this.checkpoints = new ArrayList<>();
        this.dungeonTriggers = new ArrayList<>();
        this.minPlayers = 1; // Default min players
        this.maxPlayers = 5; // Default max players
        this.timeLimitSeconds = 1800; // Default 30 minutes
    }

    // No-arg constructor for deserialization
    public DungeonTemplate() {
        this.checkpoints = new ArrayList<>();
        this.dungeonTriggers = new ArrayList<>();
    }

    // --- Getters and Setters for all fields ---
    // (Generate these using your IDE: Alt+Insert -> Getter and Setter)


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSchematicName() {
        return schematicName;
    }

    public void setSchematicName(String schematicName) {
        this.schematicName = schematicName;
    }

    public RelativeLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(RelativeLocation startLocation) {
        this.startLocation = startLocation;
    }

    public RelativeRegion getEndRegion() {
        return endRegion;
    }

    public void setEndRegion(RelativeRegion endRegion) {
        this.endRegion = endRegion;
    }

    public List<RelativeCheckpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<RelativeCheckpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public List<DungeonTrigger> getDungeonTriggers() {
        return dungeonTriggers;
    }

    public void setDungeonTriggers(List<DungeonTrigger> dungeonTriggers) {
        this.dungeonTriggers = dungeonTriggers;
    }

    public long getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    public void setTimeLimitSeconds(long timeLimitSeconds) {
        this.timeLimitSeconds = timeLimitSeconds;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @Override
    public String toString() {
        return "DungeonTemplate{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}