package com.hybridiize.oasisDungeons.data;

import java.util.ArrayList;
import java.util.List;

public class DungeonTrigger {
    private String id; // For admin reference and internal tracking
    private EventType type;
    private List<String> commandsToExecute;
    private boolean executeAsConsole;
    private boolean singleExecutionPerInstance; // Trigger only once per dungeon run
    private boolean singleExecutionPerPlayerPerInstance; // Or once per player per run
    private int executionDelayTicks; // Delay in server ticks before commands run

    // --- Type-specific conditions ---

    // For REGION_ENTER, REGION_EXIT, BLOCK_PLACED_IN_REGION, ITEM_PLACED_IN_CHEST_IN_REGION
    private RelativeRegion region;

    // For ..._MOBS_KILLED_IN_REGION (mobRegion can be the same as 'region' or different)
    private RelativeRegion mobRegion;
    private List<String> entityTypes; // List of EntityType names (e.g., "ZOMBIE", "SKELETON")
    private int requiredKillCount;

    // For PLAYER_DEATHS_IN_INSTANCE_THRESHOLD
    private int deathThreshold;

    // For ITEM_PLACED_IN_CHEST_IN_REGION (materialTypes is better than single materialType)
    private List<String> itemMaterialTypes; // List of Material names for items
    private int requiredItemCount; // How many of *any* of these items
    // boolean exactMatch; // If true, only these items, no others in the slot/chest (more complex, omit for now for simplicity)

    // For CHAT_MESSAGE_SENT_MATCHES_PATTERN
    private String messagePattern; // Regex pattern
    private boolean instanceChatOnly; // Only trigger if message sent by player within this instance

    // Constructor (basic) - more specific setters will be used
    public DungeonTrigger(String id, EventType type) {
        this.id = id;
        this.type = type;
        this.commandsToExecute = new ArrayList<>();
        this.entityTypes = new ArrayList<>();
        this.itemMaterialTypes = new ArrayList<>();
        this.executeAsConsole = true; // Default to console
        this.singleExecutionPerInstance = false;
        this.singleExecutionPerPlayerPerInstance = false;
        this.executionDelayTicks = 0;
        this.requiredKillCount = 1;
        this.deathThreshold = 1;
        this.requiredItemCount = 1;
        this.instanceChatOnly = true;
    }

    // No-arg constructor for deserialization
    public DungeonTrigger() {
        this.commandsToExecute = new ArrayList<>();
        this.entityTypes = new ArrayList<>();
        this.itemMaterialTypes = new ArrayList<>();
    }

    // --- Getters and Setters for all fields ---
    // (It's good practice to generate these using your IDE for conciseness)
    // IntelliJ: Alt+Insert -> Getter and Setter -> Select all fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public List<String> getCommandsToExecute() {
        return commandsToExecute;
    }

    public void setCommandsToExecute(List<String> commandsToExecute) {
        this.commandsToExecute = commandsToExecute;
    }

    public boolean isExecuteAsConsole() {
        return executeAsConsole;
    }

    public void setExecuteAsConsole(boolean executeAsConsole) {
        this.executeAsConsole = executeAsConsole;
    }

    public boolean isSingleExecutionPerInstance() {
        return singleExecutionPerInstance;
    }

    public void setSingleExecutionPerInstance(boolean singleExecutionPerInstance) {
        this.singleExecutionPerInstance = singleExecutionPerInstance;
    }

    public boolean isSingleExecutionPerPlayerPerInstance() {
        return singleExecutionPerPlayerPerInstance;
    }

    public void setSingleExecutionPerPlayerPerInstance(boolean singleExecutionPerPlayerPerInstance) {
        this.singleExecutionPerPlayerPerInstance = singleExecutionPerPlayerPerInstance;
    }

    public int getExecutionDelayTicks() {
        return executionDelayTicks;
    }

    public void setExecutionDelayTicks(int executionDelayTicks) {
        this.executionDelayTicks = executionDelayTicks;
    }

    public RelativeRegion getRegion() {
        return region;
    }

    public void setRegion(RelativeRegion region) {
        this.region = region;
    }

    public RelativeRegion getMobRegion() {
        return mobRegion;
    }

    public void setMobRegion(RelativeRegion mobRegion) {
        this.mobRegion = mobRegion;
    }

    public List<String> getEntityTypes() {
        return entityTypes;
    }

    public void setEntityTypes(List<String> entityTypes) {
        this.entityTypes = entityTypes;
    }

    public int getRequiredKillCount() {
        return requiredKillCount;
    }

    public void setRequiredKillCount(int requiredKillCount) {
        this.requiredKillCount = requiredKillCount;
    }

    public int getDeathThreshold() {
        return deathThreshold;
    }

    public void setDeathThreshold(int deathThreshold) {
        this.deathThreshold = deathThreshold;
    }

    public List<String> getItemMaterialTypes() {
        return itemMaterialTypes;
    }

    public void setItemMaterialTypes(List<String> itemMaterialTypes) {
        this.itemMaterialTypes = itemMaterialTypes;
    }

    public int getRequiredItemCount() {
        return requiredItemCount;
    }

    public void setRequiredItemCount(int requiredItemCount) {
        this.requiredItemCount = requiredItemCount;
    }

    public String getMessagePattern() {
        return messagePattern;
    }

    public void setMessagePattern(String messagePattern) {
        this.messagePattern = messagePattern;
    }

    public boolean isInstanceChatOnly() {
        return instanceChatOnly;
    }

    public void setInstanceChatOnly(boolean instanceChatOnly) {
        this.instanceChatOnly = instanceChatOnly;
    }

    @Override
    public String toString() {
        return "DungeonTrigger{" +
                "id='" + id + '\'' +
                ", type=" + type +
                // Add other important fields for quick identification if needed
                '}';
    }
}