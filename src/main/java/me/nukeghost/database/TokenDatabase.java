package me.nukeghost.database;

import me.nukeghost.DeliveryBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;
import java.time.LocalDateTime;

import static me.nukeghost.DeliveryBoard.plugin;

public class TokenDatabase {

    private static Connection connection;

    public static Connection getSQLConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName ("org.h2.Driver");
            connection = DriverManager.getConnection(DeliveryBoard.connectionURL);
            return connection;
        } catch (SQLException ex) {
            System.out.println("[DeliveryBoard] Database Connection failed!");
            System.out.println("[DeliveryBoard] Please make sure the provided settings are valid.");
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("H2 Driver class not found");
            e.printStackTrace();
        }
        plugin.getPluginLoader().disablePlugin(plugin);

        return null;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DeliveryBoard] Error closing database connection!");
            ex.printStackTrace();
        }
    }

    public static void initializeDatabase() {
        Connection connection = getSQLConnection();
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS DB_DeliveryToken(playerUUID varchar(36), currencyAmount int, lastUpdated varchar(60))")) {
            statement.execute();
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[DeliveryBoard] Database Connection Successful");
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DeliveryBoard] Initialization failure!");
            ex.printStackTrace();
        }
    }

    public static void saveCurrency(int amount, String uuid) {
        Connection connection = TokenDatabase.getSQLConnection();
        try {
            assert connection != null;
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO DB_DeliveryToken(playerUUID, currencyAmount, lastUpdated) VALUES (?, ?, ?)")) {
                statement.setString(1, uuid);
                statement.setInt(2, amount);
                statement.setString(3, String.valueOf(LocalDateTime.now()));
                statement.execute();
            }
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DeliveryBoard] Could not save vault to database");
            ex.printStackTrace();
        }
    }

    public static void updateCurrency(int amount, String uuid) {
        Connection connection = TokenDatabase.getSQLConnection();

        if (amount < 0) amount = 0;

        try {
            assert connection != null;
            try (PreparedStatement statement = connection.prepareStatement("UPDATE DB_DeliveryToken SET currencyAmount = ?, lastUpdated = ? WHERE playerUUID = ?")) {
                statement.setInt(1, amount);
                statement.setString(2, String.valueOf(LocalDateTime.now()));
                statement.setString(3, uuid);

                statement.execute();
            }
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DeliveryBoard] Could not save vault to database");
            ex.printStackTrace();
        }
    }

    public static String getCurrencyAmount(String uuid) {
        Connection connection = TokenDatabase.getSQLConnection();
        try {
            assert connection != null;
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM DB_DeliveryToken WHERE playerUUID = ?")) {
                statement.setString(1, uuid);

                try (ResultSet rows = statement.executeQuery()) {
                    rows.next();
                    return rows.getString("currencyAmount");
                }
            }
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DeliveryBoard] Could not get vault data from database!");
            ex.printStackTrace();
        }

        return null;
    }

    public static boolean doesPlayerEntryExist(String uuid) {
        Connection connection = TokenDatabase.getSQLConnection();
        try {
            assert connection != null;
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM DB_DeliveryToken WHERE playerUUID = ?")) {
                statement.setString(1, uuid);

                try (ResultSet rows = statement.executeQuery()) {
                    return rows.next();
                }
            }
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DeliveryBoard] Could not check if vault exists in database");
            ex.printStackTrace();
        }

        return false;
    }
}
