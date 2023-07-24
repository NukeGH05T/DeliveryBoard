package me.nukeghost;

import me.nukeghost.commands.AutoTabCompleter;
import me.nukeghost.commands.CommandManager;
import me.nukeghost.external.ItemPlugin;
import me.nukeghost.handlers.GenerationHandler;
import me.nukeghost.language.LanguageConfig;
import me.nukeghost.language.Message;
import me.nukeghost.listeners.*;
import me.nukeghost.menusystem.PaginatedMenu;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.tasks.HourlyDeliveryUpdateTask;
import me.nukeghost.tasks.SixHourlyDeliveryUpdateTask;
import me.nukeghost.tasks.ThreeHourlyDeliveryUpdateTask;
import me.nukeghost.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class DeliveryBoard extends JavaPlugin {

    public static final HashMap<String, Long> cooldown = new HashMap<>();//
    private static final HashMap<Player, PlayerMenuUtility> PLAYER_MENU_UTILITY_MAP = new HashMap<>();

    public static List<Player> hourlyCompletedPlayerList = new ArrayList<>();//
    public static List<Player> threeHourlyCompletedPlayerList = new ArrayList<>();
    public static List<Player> sixHourlyCompletedPlayerList = new ArrayList<>();


    public static DeliveryBoard plugin;

    private static ItemStack hourlyItem;//
    private static ItemStack threeHourlyItem;
    private static ItemStack sixHourlyItem;
    public static HashMap<String, ItemPlugin> usedItemPluginsHashMap = new HashMap<>();
    public static HashMap<Player, HashMap<PaginatedMenu, PlayerMenuUtility>> adminAddCommandRewardHashMap = new HashMap<>();
    public static List<String> enabledItemPlugins = new ArrayList<>();


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
            initializeDefaultDeliveries();
        }
        startTasks();

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
    //ItemPlugins present

    private void initializeConfig() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    private void initializeLang() {
        LanguageConfig.setupLang();

        //LanguageConfig.getLangConfig().options().copyDefaults(true);
        //LanguageConfig.saveLangConfig();

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

    void startTasks() {
        BukkitTask hourlyTask = new HourlyDeliveryUpdateTask(this).runTaskTimer(this,0L, 20L * TimeUtils.convertToSeconds("1m"));
        cooldown.put("hourly", System.currentTimeMillis() + 3600000);

        BukkitTask threeHourlyTask = new ThreeHourlyDeliveryUpdateTask(this).runTaskTimer(this,0L, 20L * TimeUtils.convertToSeconds("1m"));
        cooldown.put("three-hourly", System.currentTimeMillis() + (3600000 * 3));

        BukkitTask sixHourlyTask = new SixHourlyDeliveryUpdateTask(this).runTaskTimer(this,0L, 20L * TimeUtils.convertToSeconds("1m"));
        cooldown.put("six-hourly", System.currentTimeMillis() + (3600000 * 6));
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

    private void initializeDefaultDeliveries() {
        setHourlyItem(GenerationHandler.generateDeliveryItem("hourly"));
        setThreeHourlyItem(GenerationHandler.generateDeliveryItem("three-hourly"));
        setSixHourlyItem(GenerationHandler.generateDeliveryItem("six-hourly"));
    }

    public static ItemStack getHourlyItem() {
        return hourlyItem;
    }

    public static void setHourlyItem(ItemStack hourlyItem) {
        DeliveryBoard.hourlyItem = hourlyItem;
    }

    public static ItemStack getThreeHourlyItem() {
        return threeHourlyItem;
    }

    public static void setThreeHourlyItem(ItemStack threeHourlyItem) {
        DeliveryBoard.threeHourlyItem = threeHourlyItem;
    }

    public static ItemStack getSixHourlyItem() {
        return sixHourlyItem;
    }

    public static void setSixHourlyItem(ItemStack sixHourlyItem) {
        DeliveryBoard.sixHourlyItem = sixHourlyItem;
    }
}
