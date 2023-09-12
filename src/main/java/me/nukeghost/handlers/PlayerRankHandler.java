package me.nukeghost.handlers;

import me.nukeghost.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class PlayerRankHandler {
    FileConfiguration data;
    Map<String, Integer> localUUIDPointMap = new HashMap<>();

    public PlayerRankHandler() {
        this.data = PlayerData.get();

        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            this.localUUIDPointMap.put(offlinePlayer.getUniqueId().toString(), data.getInt(offlinePlayer.getUniqueId().toString() + ".completed"));
        }
    }

    public Map<String, Integer> sortByValue() {

        //Converting Map to List of Maps
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(localUUIDPointMap.entrySet());

        // Sorting list with Collections.sort(), provide a custom Comparator
        //    switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        //Looping the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
