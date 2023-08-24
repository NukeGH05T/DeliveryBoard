package me.nukeghost;

import me.nukeghost.commands.AutoTabCompleter;
import me.nukeghost.commands.CommandManager;
import me.nukeghost.database.Database;
import me.nukeghost.external.ItemPlugin;
import me.nukeghost.language.LanguageConfig;
import me.nukeghost.language.Message;
import me.nukeghost.listeners.*;
import me.nukeghost.menusystem.PaginatedMenu;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.tasks.DeliveryUpdateTask;
import me.nukeghost.template.Delivery;
import me.nukeghost.utils.Metrics;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class DeliveryBoard extends JavaPlugin {
    private static final HashMap<Player, PlayerMenuUtility> PLAYER_MENU_UTILITY_MAP = new HashMap<>();

    public static DeliveryBoard plugin;

    public static HashMap<String, ItemPlugin> usedItemPluginsHashMap = new HashMap<>();
    public static HashMap<Player, HashMap<PaginatedMenu, PlayerMenuUtility>> adminAddCommandRewardHashMap = new HashMap<>();
    public static List<String> enabledItemPlugins = new ArrayList<>();

    public static List<Delivery> deliveries = new ArrayList<>();
    public static List<List<Player>> deliveryCompletedPlayerList = new ArrayList<>();

    public static String connectionURL;
    public static int defaultTokenAmount;


    private static Economy econ = null;

    @Override
    public void onEnable() {
        plugin = this;
        defaultTokenAmount = getConfig().getInt("delivery-tokens.default");

        //bStats
        int pluginId = 19318;
        Metrics metrics = new Metrics(this, pluginId);

        initializeConfig();
        initializeLang();
        registerCommands();
        initializeItemPlugins();
        registerListeners(); // Let IA be added as an ItemPlugin before doing this
        //Not initing if ItemsAdder is present. ItemsAdder takes time to load item data. Moved to FirstItemsAdderLoadListener
        if (!enabledItemPlugins.contains("ItemsAdder")) {
            startTasks();
        }

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - No Vault dependency found!", getDescription().getName()));
        }

        setupDatabase();

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
        getServer().getPluginManager().registerEvents(new PlayerCurrencyInitializeOnJoinListener(), plugin);

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

        //BukkitTask deliveryUpdateTask = new DeliveryUpdateTask(plugin).runTaskTimer(this,0L, 20L * 60);
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

    private void setupDatabase() {
        if (plugin.getConfig().getString("database").equalsIgnoreCase("mysql")) {
            //MySQL String Setup
            String host = plugin.getConfig().getString("mysql-settings.host");
            String dbName = plugin.getConfig().getString("mysql-settings.db-name");
            String port = plugin.getConfig().getString("mysql-settings.port");
            String username = plugin.getConfig().getString("mysql-settings.username");
            String password = plugin.getConfig().getString("mysql-settings.password");

            connectionURL = "jdbc:mysql://" + username + ":" + password + "@" + host + ":" + port + "/" + dbName;
        } else if (plugin.getConfig().getString("database").equalsIgnoreCase("h2")) {
            //H2 Embedded Setup
            connectionURL = "jdbc:h2:file:" + getDataFolder().getAbsolutePath() + "/currency/currency-data";
        } else {
            Bukkit.getLogger().severe("[DeliveryBoard] Invalid database type! Please choose either \"mysql\" or \"h2\"");
            this.getPluginLoader().disablePlugin(this);
        }

        Database.initializeDatabase();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
