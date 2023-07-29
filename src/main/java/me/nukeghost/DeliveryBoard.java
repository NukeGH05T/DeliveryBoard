package me.nukeghost;

import me.nukeghost.commands.AutoTabCompleter;
import me.nukeghost.commands.CommandManager;
import me.nukeghost.external.ItemPlugin;
import me.nukeghost.language.LanguageConfig;
import me.nukeghost.language.Message;
import me.nukeghost.listeners.*;
import me.nukeghost.menusystem.PaginatedMenu;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.tasks.DeliveryUpdateTask;
import me.nukeghost.template.Delivery;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class DeliveryBoard extends JavaPlugin {

    public static final HashMap<String, Long> cooldown = new HashMap<>();//
    private static final HashMap<Player, PlayerMenuUtility> PLAYER_MENU_UTILITY_MAP = new HashMap<>();

    public static DeliveryBoard plugin;

    public static HashMap<String, ItemPlugin> usedItemPluginsHashMap = new HashMap<>();
    public static HashMap<Player, HashMap<PaginatedMenu, PlayerMenuUtility>> adminAddCommandRewardHashMap = new HashMap<>();
    public static List<String> enabledItemPlugins = new ArrayList<>();

    public static List<Delivery> deliveries = new ArrayList<>();
    public static List<List<Player>> deliveryCompletedPlayerList = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin = this;

        initializeConfig();
        initializeLang();
        registerCommands();
        initializeItemPlugins();
        registerListeners(); // Let IA be added as an ItemPlugin before doing this
        //Not initing if ItemsAdder is present. ItemsAdder takes time to load item data. Moved to FirstItemsAdderLoadListener
        if (!enabledItemPlugins.contains("ItemsAdder")) {
            startTasks();
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "This plugin is licensed to " + ChatColor.GREEN + "%%__USERNAME__%%");
    }

    //Checking which ItemPlugins are present
    private void initializeItemPlugins() {
        String[] itemPluginStrings = {"MMOItems", "ItemsAdder", "EcoItems", "ExecutableItems"};

        for (String itemPluginName : itemPluginStrings) {
            Plugin iPlugin = Bukkit.getPluginManager().getPlugin(itemPluginName);
            if (iPlugin != null && iPlugin.isEnabled()) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[DB] " + itemPluginName + " hooked !");
                switch (itemPluginName) {
                    case "MMOItems":
                        enabledItemPlugins.add("MMOItems");
                        break;
                    case "ItemsAdder":
                        enabledItemPlugins.add("ItemsAdder");
                        break;
                    case "EcoItems":
                        enabledItemPlugins.add("EcoItems");
                        break;
                    case "ExecutableItems":
                        enabledItemPlugins.add("ExecutableItems");
                        break;
                }
            }
        }
    }

    private void initializeConfig() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    private void initializeLang() {
        LanguageConfig.setupLang();
        Message.loadMessages();
    }

    void registerCommands() {
        getCommand("deliveryboard").setExecutor(new CommandManager());
        getCommand("deliveryboard").setTabCompleter(new AutoTabCompleter());
    }

    void registerListeners() {
        getServer().getPluginManager().registerEvents(new MenuListener(), plugin);
        getServer().getPluginManager().registerEvents(new BoardInteractionListener(plugin), plugin);
        getServer().getPluginManager().registerEvents(new AdminCommandAddListener(), plugin);
        getServer().getPluginManager().registerEvents(new AdminMaxRandomRewardListener(), plugin);

        if (enabledItemPlugins.contains("ItemsAdder")) {
            getServer().getPluginManager().registerEvents(new FirstItemsAdderLoadListener(), plugin);
        }
    }

    public void startTasks() {
        for (String deliveryID : plugin.getConfig().getStringList("active-deliveries")) {
            Delivery delivery = new Delivery(deliveryID, plugin.getConfig().getString("delivery." + deliveryID + ".title"),
                    plugin.getConfig().getLong("delivery." + deliveryID + ".cooldown"));

            DeliveryBoard.deliveryCompletedPlayerList.add(new ArrayList<>());
            deliveries.add(delivery);
        }

        BukkitTask deliveryUpdateTask = new DeliveryUpdateTask(plugin).runTaskTimer(this,0L, 20L * 60);
    }

    public PlayerMenuUtility getPlayerMenuUtility(Player p) {
        if (PLAYER_MENU_UTILITY_MAP.containsKey(p)) {
            return PLAYER_MENU_UTILITY_MAP.get(p);
        } else {
            PlayerMenuUtility playerMenuUtility = new PlayerMenuUtility(p);
            PLAYER_MENU_UTILITY_MAP.put(p, playerMenuUtility);
            return playerMenuUtility;
        }
    }
}
