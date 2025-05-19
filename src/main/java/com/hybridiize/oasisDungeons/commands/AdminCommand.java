package com.hybridiize.oasisDungeons.commands;

import com.hybridiize.oasisDungeons.OasisDungeons;
import com.hybridiize.oasisDungeons.data.DungeonTemplate;
import com.hybridiize.oasisDungeons.managers.DungeonTemplateManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdminCommand implements CommandExecutor, TabCompleter {

    private final OasisDungeons plugin;
    private final DungeonTemplateManager templateManager;

    public AdminCommand(OasisDungeons plugin) {
        this.plugin = plugin;
        this.templateManager = plugin.getDungeonTemplateManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            if (!canConsoleUse(args.length > 0 ? args[0] : "")) {
                sender.sendMessage(ChatColor.RED + "This command, or this specific subcommand, can only be run by a player.");
                return true;
            }
            // Allow console for specific subcommands if needed in the future
        }

        if (!sender.hasPermission("oasisdungeons.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "create":
                return handleCreateCommand(sender, args);
            // TODO: Add more cases for other subcommands (setstart, setend, addcheckpoint, etc.)
            // case "setregion":
            // case "setschematic":
            // case "setstart":
            // case "setend":
            // case "addcheckpoint":
            // case "settimelimit":
            // case "save": // Maybe not needed if templates save on modification
            // case "delete":
            // case "list":
            // case "definesigil":
            // case "removesigil":
            // case "addtrigger":
            // etc.
            default:
                sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /odadmin help for a list of commands.");
                return true;
        }
    }

    private boolean canConsoleUse(String subCommand) {
        // List subcommands that console CAN use. For now, most will require a player.
        List<String> consoleAllowed = Arrays.asList("list", "reload"); // Example
        return consoleAllowed.contains(subCommand.toLowerCase());
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "--- OasisDungeons Admin Help ---");
        sender.sendMessage(ChatColor.YELLOW + "/odadmin create <id> <displayName> " + ChatColor.GRAY + "- Creates a new dungeon template.");
        // TODO: Add more help messages as commands are implemented
    }

    private boolean handleCreateCommand(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /odadmin create <id> <displayName>");
            return true;
        }

        String id = args[1];
        // Combine remaining args for displayName
        StringBuilder displayNameBuilder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            displayNameBuilder.append(args[i]).append(" ");
        }
        String displayName = displayNameBuilder.toString().trim();

        if (id.isEmpty() || displayName.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "ID and Display Name cannot be empty.");
            return true;
        }

        if (templateManager.getTemplate(id) != null) {
            sender.sendMessage(ChatColor.RED + "A dungeon template with ID '" + id + "' already exists.");
            return true;
        }

        // Basic ID validation (no spaces, simple characters - can be expanded)
        if (!id.matches("^[a-zA-Z0-9_-]+$")) {
            sender.sendMessage(ChatColor.RED + "Invalid ID. Use only alphanumeric characters, underscores, and hyphens.");
            return true;
        }


        DungeonTemplate newTemplate = new DungeonTemplate(id, displayName);
        templateManager.addTemplate(newTemplate); // addTemplate also calls saveTemplate

        sender.sendMessage(ChatColor.GREEN + "Dungeon template '" + displayName + "' (ID: " + id + ") created successfully!");
        sender.sendMessage(ChatColor.YELLOW + "Now you can define its regions, start/end points, checkpoints, etc.");
        return true;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (!sender.hasPermission("oasisdungeons.admin")) {
            return completions;
        }

        if (args.length == 1) {
            List<String> subCommands = Arrays.asList("create", "setregion", "setschematic", "setstart", "setend", "addcheckpoint", "settimelimit", "delete", "list", "definesigil", "addtrigger");
            // Add more as they are implemented
            for (String sub : subCommands) {
                if (sub.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(sub);
                }
            }
        } else if (args.length > 1) {
            String subCommand = args[0].toLowerCase();
            // Add tab completion for specific subcommand arguments
            switch (subCommand) {
                case "create":
                    if (args.length == 2) {
                        completions.add("<template_id>");
                    } else if (args.length == 3) {
                        completions.add("<display_name>");
                    }
                    break;
                // case "delete":
                // case "setstart": // etc., would suggest existing template IDs
                //    if (args.length == 2) {
                //        completions.addAll(templateManager.getTemplateIds().stream()
                //            .filter(id -> id.toLowerCase().startsWith(args[1].toLowerCase()))
                //            .collect(Collectors.toList()));
                //    }
                //    break;
            }
        }
        return completions;
    }
}