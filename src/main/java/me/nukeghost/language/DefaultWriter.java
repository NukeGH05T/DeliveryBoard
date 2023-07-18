package me.nukeghost.language;

import me.nukeghost.DeliveryBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DefaultWriter {
    BufferedWriter bw;
    String langFileName = DeliveryBoard.plugin.getConfig().getString("lang-file");

    public void writeEnglishLang() {
        File file = new File(DeliveryBoard.plugin.getDataFolder() + "/language/" + langFileName);
        initializeWriter();
        write();
        closeWriter();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Initialized default fields for " + langFileName + ".");
    }

    private void initializeWriter() {
        try {
            bw = new BufferedWriter(new FileWriter(DeliveryBoard.plugin.getDataFolder() + "/language/" + langFileName));
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[DB] Something went wrong while writing the default lang file!");
            e.printStackTrace();
        }
    }

    private void write() {
        Bukkit.getLogger().info("Writing default lang");
        try {
            bw.write("#Commands\n" +
                    "#>Add\n" +
                    "ADD_COMMAND_DESCRIPTION: 'Store the item currently held item as a requirement for delivery.'\n" +
                    "ADD_INVALID_ITEM: 'Invalid Item or Plugin!'\n" +
                    "#>Edit\n" +
                    "EDIT_COMMAND_DESCRIPTION: 'Edit requirements of an active board'\n" +
                    "#>Board\n" +
                    "BOARD_COMMAND_DESCRIPTION: 'Shows the delivery board GUI'\n" +
                    "#>Help\n" +
                    "HELP_COMMAND_DESCRIPTION: 'Lists all available commands.'\n" +
                    "#>Reload\n" +
                    "RELOAD_COMMAND_DESCRIPTION: 'Reloads the plugin config file. It does not reload the language files. Please restart instead to update language files.'\n" +
                    "RELOAD_SUCCESSFULL: '&bConfig Reloaded! Please restart instead to update language files.'\n" +
                    "RELOAD_FAILED: '&4Reload failed. Please check console for details.'\n" +
                    "RELOAD_NO_PERM: '&cYou do not have permission to reload.'\n" +
                    "#>Setboard\n" +
                    "SETBOARD_COMMAND_DESCRIPTION: 'Sets the target block as a board that shows the delivery board on right click.'\n" +
                    "BOARD_SETUP_SUCCESSFULL: '&bBoard has been successfully setup.'\n" +
                    "BOARD_OVERRIDE_PREVIOUS: '&eOverriding previous board location at {BOARD_LOC}' #{BOARD_LOC} returns board's previous location.\n" +
                    "\n" +
                    "#Command Manager\n" +
                    "CMDM_NO_PERM_DEFAULT: '&cYou do not have permission to run that command!'\n" +
                    "CMDM_INVALID_COMMAND: '&cThat is not a valid command.'\n" +
                    "CMDM_HELP_SUGGEST: '&eDo &a/db help&e for more info.'\n" +
                    "\n" +
                    "#Paginated Menu\n" +
                    "ON_FIRST_PAGE: \"&cYou are already on the first page!\"\n" +
                    "ON_LAST_PAGE: \"&cYou are already on the last page!\"\n" +
                    "PGN_NEXT_PAGE: \"&aNext Page >\"\n" +
                    "PGN_PREVIOUS_PAGE: \"&a< Previous Page\"\n" +
                    "PGN_CLOSE_PAGE: \"&4Close\"\n" +
                    "PGN_INFO_ITEM: '&fInstructions'\n" +
                    "PGN_INFO_DETAILS:\n" +
                    "  - '&f[&b&lRight Click&f] &7 to add item.'\n" +
                    "  - '&f[&c&lRight Click&f] &7 to remove item.'\n" +
                    "  - ''\n" +
                    "  - '&7Having multiple instances of the same item'\n" +
                    "  - '&7will make them appear as one in the edit GUI'\n" +
                    "\n" +
                    "#Hourly Delivery Menu\n" +
                    "HRD_EMPTY_SUBMISSION: '&cCannot submit with empty delivery!'\n" +
                    "HRD_WRONG_SUBMISSION: '&cThe item is the not the one that needs to be delivered!'\n" +
                    "HRD_SUCCESSFUL_SUBMISSION: '<#40FB85>Delivery Successful!'\n" +
                    "HRD_ICON_ITEM_LORE:\n" +
                    "  - '&7Deliver the required item'\n" +
                    "  - '&7to claim your reward.'\n" +
                    "HRD_SUBMIT_ITEM_DISPLAY: '<#40FB85>&lConfirm Delivery'\n" +
                    "HRD_SUBMIT_ITEM_LORE:\n" +
                    "  - '&e========= WARNING ========='\n" +
                    "  - '&fThe item will be deducted'\n" +
                    "  - '&ffrom your inventory.'\n" +
                    "  - '&e=========================='\n" +
                    "HRD_BACK_ITEM_DISPLAY: '&c⇦ Back'\n" +
                    "\n" +
                    "#Three hourly Delivery Menu\n" +
                    "THD_EMPTY_SUBMISSION: '&cCannot submit with empty delivery!'\n" +
                    "THD_WRONG_SUBMISSION: '&cThe item is the not the one that needs to be delivered!'\n" +
                    "THD_SUCCESSFUL_SUBMISSION: '<#40FB85>Delivery Successful!'\n" +
                    "THD_ICON_ITEM_LORE:\n" +
                    "  - '&7Deliver the required item'\n" +
                    "  - '&7to claim your reward.'\n" +
                    "THD_SUBMIT_ITEM_DISPLAY: '<#40FB85>&lConfirm Delivery'\n" +
                    "THD_SUBMIT_ITEM_LORE:\n" +
                    "  - '&e========= WARNING ========='\n" +
                    "  - '&fThe item will be deducted'\n" +
                    "  - '&ffrom your inventory.'\n" +
                    "  - '&e=========================='\n" +
                    "THD_BACK_ITEM_DISPLAY: '⇦ Back'\n" +
                    "\n" +
                    "#Six hourly Delivery Menu\n" +
                    "SHD_EMPTY_SUBMISSION: '&cCannot submit with empty delivery!'\n" +
                    "SHD_WRONG_SUBMISSION: '&cThe item is the not the one that needs to be delivered!'\n" +
                    "SHD_SUCCESSFUL_SUBMISSION: '<#40FB85>Delivery Successful!'\n" +
                    "SHD_ICON_ITEM_LORE:\n" +
                    "  - '&7Deliver the required item'\n" +
                    "  - '&7to claim your reward.'\n" +
                    "SHD_SUBMIT_ITEM_DISPLAY: '<#40FB85>&lConfirm Delivery'\n" +
                    "SHD_SUBMIT_ITEM_LORE:\n" +
                    "  - '&e========= WARNING ========='\n" +
                    "  - '&fThe item will be deducted'\n" +
                    "  - '&ffrom your inventory.'\n" +
                    "  - '&e=========================='\n" +
                    "SHD_BACK_ITEM_DISPLAY: '&c⇦ Back'\n" +
                    "\n" +
                    "#Delivery Board Menu\n" +
                    "DB_DELIVERY_ALREADY_COMPLETE: '&cYou have already completed the delivery!'\n" +
                    "\n" +
                    "#>Hourly Item\n" +
                    "DB_HOURLY_ITEM_LORE:\n" +
                    "  - '&e---------------------------'\n" +
                    "  - '&7The town needs new deliveries'\n" +
                    "  - '&7all the time. Provide what the'\n" +
                    "  - '&7townsfolk needs and get rewarded.'\n" +
                    "  - '&e---------------------------'\n" +
                    "DB_HOURLY_ITEM_LORE_FOOTER_COMPLETE:\n" +
                    "  - '<#40FB85>COMPLETED'\n" +
                    "  - '&7Refreshes In: &a{DURATION}'      #{DURATION} returns the time remaining\n" +
                    "DB_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE:\n" +
                    "  - '&7Time Left: &e{DURATION}'         #{DURATION} returns the time remaining\n" +
                    "\n" +
                    "#>Three hourly Item\n" +
                    "DB_THREE_HOURLY_ITEM_LORE:\n" +
                    "  - '&e---------------------------'\n" +
                    "  - '&7The town needs new deliveries'\n" +
                    "  - '&7all the time. Provide what the'\n" +
                    "  - '&7townsfolk needs and get rewarded.'\n" +
                    "  - '&e---------------------------'\n" +
                    "DB_THREE_HOURLY_ITEM_LORE_FOOTER_COMPLETE:\n" +
                    "  - '<#40FB85>COMPLETED'\n" +
                    "  - '&7Refreshes In: &a{DURATION}'      #{DURATION} returns the time remaining\n" +
                    "DB_THREE_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE:\n" +
                    "  - '&7Time Left: &e{DURATION}'         #{DURATION} returns the time remaining\n" +
                    "\n" +
                    "#>Six hourly Item\n" +
                    "DB_SIX_HOURLY_ITEM_LORE:\n" +
                    "  - '&e---------------------------'\n" +
                    "  - '&7The town needs new deliveries'\n" +
                    "  - '&7all the time. Provide what the'\n" +
                    "  - '&7townsfolk needs and get rewarded.'\n" +
                    "  - '&e---------------------------'\n" +
                    "DB_SIX_HOURLY_ITEM_LORE_FOOTER_COMPLETE:\n" +
                    "  - '<#40FB85>COMPLETED'\n" +
                    "  - '&7Refreshes In: &a{DURATION}'      #{DURATION} returns the time remaining\n" +
                    "DB_SIX_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE:\n" +
                    "  - '&7Time Left: &e{DURATION}'         #{DURATION} returns the time remaining\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWriter() {
        try {
            Bukkit.getLogger().info("Closed writer");
            bw.close();
        } catch (IOException e) { e.printStackTrace();}
    }
}
