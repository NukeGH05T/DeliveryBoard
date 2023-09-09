package me.nukeghost.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static me.nukeghost.DeliveryBoard.plugin;

public class PlayerData {

    private static File file;
    private static FileConfiguration customFile;
    public static void setup(){
        file = new File(plugin.getDataFolder(), "player-data.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getLogger().severe(e.toString());
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return customFile;
    }

    public static void save(){
        try{
            customFile.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }

    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
