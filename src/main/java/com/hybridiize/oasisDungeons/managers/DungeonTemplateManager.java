package com.hybridiize.oasisDungeons.managers;

import com.hybridiize.oasisDungeons.OasisDungeons;
import com.hybridiize.oasisDungeons.data.DungeonTemplate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class DungeonTemplateManager {

    private final OasisDungeons plugin;
    private final Map<String, DungeonTemplate> dungeonTemplates;
    private final File templatesFolder;

    public DungeonTemplateManager(OasisDungeons plugin) {
        this.plugin = plugin;
        this.dungeonTemplates = new HashMap<>();
        this.templatesFolder = new File(plugin.getDataFolder(), "dungeon_templates");

        if (!templatesFolder.exists()) {
            if (templatesFolder.mkdirs()) {
                plugin.getLogger().info("Created dungeon_templates folder.");
            } else {
                plugin.getLogger().severe("Could not create dungeon_templates folder!");
            }
        }
    }

    public void loadTemplates() {
        dungeonTemplates.clear(); // Clear existing templates before loading
        if (!templatesFolder.exists() || !templatesFolder.isDirectory()) {
            plugin.getLogger().warning("Dungeon templates folder not found or is not a directory. No templates loaded.");
            return;
        }

        File[] templateFiles = templatesFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".yml"));

        if (templateFiles == null || templateFiles.length == 0) {
            plugin.getLogger().info("No dungeon template files found in dungeon_templates folder.");
            return;
        }

        for (File templateFile : templateFiles) {
            String templateId = templateFile.getName().substring(0, templateFile.getName().length() - 4); // Remove .yml
            try {
                FileConfiguration config = YamlConfiguration.loadConfiguration(templateFile);
                // We use a custom deserialization method here because SnakeYAML (used by Bukkit)
                // can directly map to POJOs if they are structured correctly in the YAML.
                // Bukkit's get("path", DungeonTemplate.class) doesn't work for complex nested objects easily.
                // So, we'll store the whole DungeonTemplate object directly.
                DungeonTemplate template = (DungeonTemplate) config.get("template");

                if (template != null) {
                    // Ensure transient fields like in RelativeRegion are recalculated if needed after deserialization
                    // For RelativeRegion, calculateBounds() should be called.
                    // This might require making DungeonTemplate aware or having a postLoad() method.
                    // For now, let's assume the getters/setters handle it or it's done upon access.
                    // A more robust way is to add a method to DungeonTemplate: template.postLoadProcessing();
                    // which would iterate through its regions and checkpoints to call calculateBounds().
                    // Let's add this concept.

                    if (template.getEndRegion() != null && template.getEndRegion().getPos1() != null && template.getEndRegion().getPos2() != null) {
                        template.getEndRegion().calculateBounds();
                    }
                    template.getCheckpoints().forEach(cp -> {
                        if (cp.getRegion() != null && cp.getRegion().getPos1() != null && cp.getRegion().getPos2() != null) {
                            cp.getRegion().calculateBounds();
                        }
                    });
                    template.getDungeonTriggers().forEach(dt -> {
                        if (dt.getRegion() != null && dt.getRegion().getPos1() != null && dt.getRegion().getPos2() != null) {
                            dt.getRegion().calculateBounds();
                        }
                        if (dt.getMobRegion() != null && dt.getMobRegion().getPos1() != null && dt.getMobRegion().getPos2() != null) {
                            dt.getMobRegion().calculateBounds();
                        }
                    });


                    dungeonTemplates.put(template.getId().toLowerCase(), template); // Store with lowercase ID for consistency
                    plugin.getLogger().info("Loaded dungeon template: " + template.getDisplayName() + " (ID: " + template.getId() + ")");
                } else {
                    plugin.getLogger().warning("Failed to load dungeon template from " + templateFile.getName() + ". 'template' section not found or invalid.");
                }

            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Error loading dungeon template: " + templateFile.getName(), e);
            }
        }
        plugin.getLogger().info("Loaded " + dungeonTemplates.size() + " dungeon templates.");
    }

    public void saveTemplate(DungeonTemplate template) {
        if (template == null || template.getId() == null || template.getId().isEmpty()) {
            plugin.getLogger().warning("Attempted to save an invalid or null template.");
            return;
        }

        File templateFile = new File(templatesFolder, template.getId().toLowerCase() + ".yml");
        FileConfiguration config = new YamlConfiguration();

        // Store the entire DungeonTemplate object under the key "template"
        // This relies on Bukkit's YamlConfiguration being able to serialize your POJOs.
        // Your POJOs (DungeonTemplate, RelativeLocation, etc.) must have:
        // 1. A no-arg constructor.
        // 2. Public getters for all fields you want to save.
        // 3. Public setters for all fields you want to load (or make fields public, less ideal).
        // For complex objects (like lists of custom objects), ensure they are also serializable.
        config.set("template", template);

        try {
            config.save(templateFile);
            plugin.getLogger().info("Saved dungeon template: " + template.getDisplayName() + " (ID: " + template.getId() + ")");
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save dungeon template: " + template.getId(), e);
        }
    }

    public DungeonTemplate getTemplate(String id) {
        if (id == null) return null;
        return dungeonTemplates.get(id.toLowerCase());
    }

    public Set<String> getTemplateIds() {
        return dungeonTemplates.keySet();
    }

    public Map<String, DungeonTemplate> getAllTemplates() {
        return new HashMap<>(dungeonTemplates); // Return a copy to prevent external modification
    }

    public void addTemplate(DungeonTemplate template) {
        if (template != null && template.getId() != null) {
            dungeonTemplates.put(template.getId().toLowerCase(), template);
            saveTemplate(template); // Save it immediately when added programmatically
        }
    }

    public void removeTemplate(String id) {
        if (id == null) return;
        DungeonTemplate removedTemplate = dungeonTemplates.remove(id.toLowerCase());
        if (removedTemplate != null) {
            File templateFile = new File(templatesFolder, id.toLowerCase() + ".yml");
            if (templateFile.exists()) {
                if (templateFile.delete()) {
                    plugin.getLogger().info("Deleted dungeon template file: " + templateFile.getName());
                } else {
                    plugin.getLogger().warning("Could not delete dungeon template file: " + templateFile.getName());
                }
            }
            plugin.getLogger().info("Removed dungeon template from memory: " + id);
        }
    }

    // We will add methods related to admin commands here later
    // e.g., createNewTemplate(String id, String displayName), setDungeonStart(String id, Location loc), etc.
}