package me.nukeghost.database;

import me.nukeghost.DeliveryBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;
import java.time.LocalDateTime;

import static me.nukeghost.DeliveryBoard.plugin;

public class Database {
    public static Connection getSQLConnection() {
        try {
            Class.forName ("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DeliveryBoard.connectionURL);
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

    public static void initializeDatabase() {
        Connection connection = getSQLConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS DB_DeliveryToken(playerUUID varchar(36), currencyAmount int, lastUpdated varchar(60))");
            statement.execute();
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[DeliveryBoard] Database Connection Successful");
            connection.close();
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DeliveryBoard] Initialization failure!");
            ex.printStackTrace();
        }
    }

    public static void saveCurrency(int amount, String uuid) {
        Connection connection = Database.getSQLConnection();
        try {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO DB_DeliveryToken(playerUUID, currencyAmount, lastUpdated) VALUES (?, ?, ?)");
            statement.setString(1, uuid);
            statement.setInt(2, amount);
            statement.setString(3, String.valueOf(LocalDateTime.now()));
            statement.execute();
            connection.close();
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DeliveryBoard] Could not save vault to database");
            ex.printStackTrace();
        }
    }

    public static void updateCurrency(int amount, String uuid) {
        Connection connection = Database.getSQLConnection();

        if (amount < 0) amount = 0;

        try {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement("UPDATE DB_DeliveryToken SET currencyAmount = ?, lastUpdated = ? WHERE playerUUID = ?");
            statement.setInt(1, amount);
            statement.setString(2, String.valueOf(LocalDateTime.now()));
            statement.setString(3, uuid);

            statement.execute();
            connection.close();
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DeliveryBoard] Could not save vault to database");
            ex.printStackTrace();
        }
    }

    public static String getCurrencyAmount(String uuid) {
        Connection connection = Database.getSQLConnection();
        try {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM DB_DeliveryToken WHERE playerUUID = ?");
            statement.setString(1, uuid);

            ResultSet rows = statement.executeQuery();
            rows.next();
            String currencyAmount = rows.getString("currencyAmount");
            rows.close();
            connection.close();
            return currencyAmount;

        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DeliveryBoard] Could not get vault data from database!");
            ex.printStackTrace();
        }

        return null;
    }

    public static boolean doesPlayerEntryExist(String uuid) {
        Connection connection = Database.getSQLConnection();
        try {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM DB_DeliveryToken WHERE playerUUID = ?");
            statement.setString(1, uuid);

            ResultSet rows = statement.executeQuery();
            boolean exists = rows.next();
            rows.close();
            connection.close();
            return exists;

        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DeliveryBoard] Could not check if vault exists in database");
            ex.printStackTrace();
        }

        return false;
    }
}
