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
                    "#>Refresh\n" +
                    "REFRESH_COMMAND_DESCRIPTION: 'Refresh a specific delivery.'\n" +
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
                    "#Token Manager\n" +
                    "TOKEN_GIVE_DESCRIPTION: 'Give Delivery Tokens to a player'\n" +
                    "TOKEN_RESET_DESCRIPTION: 'Reset Delivery Tokens to default for a player'\n" +
                    "TOKEN_TAKE_DESCRIPTION: 'Take certain amount of Delivery Tokens from a player'\n" +
                    "TOKEN_BALANCE_DESCRIPTION: 'Shows the amount of tokens a player has'\n" +
                    "TOKEN_BALANCE_NO_PERM: '&cYou do not have permission to view balance of another player'\n" +
                    "TOKEN_BALANCE: '&eDelivery Token Balance: &a'\n" +
                    "\n" +
                    "#Skipping\n" +
                    "SKIP_SUCCESSFUL: '&bSuccessfully skipped the delivery.'\n" +
                    "INSUFFICIENT_TOKENS: '&cYou do not have enough items/currency to skip!'\n" +
                    "\n" +
                    "#Refresh Reminder\n" +
                    "REFRESH_REMINDER: '&b{DELIVERY} has been refreshed'\n" +
                    "\n" +
                    "#Paginated Menu\n" +
                    "ON_FIRST_PAGE: \"&cYou are already on the first page!\"\n" +
                    "ON_LAST_PAGE: \"&cYou are already on the last page!\"\n" +
                    "PGN_NEXT_PAGE: \"&aNext Page >\"\n" +
                    "PGN_PREVIOUS_PAGE: \"&a< Previous Page\"\n" +
                    "PGN_CLOSE_PAGE: \"&4Close\"\n" +
                    "PGN_INFO_ITEM: '&fInstructions' #Players will not see this\n" +
                    "PGN_INFO_DETAILS: #Players will not see this\n" +
                    "  - '&f[&b&lLeft Click&f] &7inventory to add item.'\n" +
                    "  - '&f[&c&lRight Click&f] &7GUI reward to remove item.'\n" +
                    "  - ''\n" +
                    "  - '&7Having multiple instances of the same item'\n" +
                    "  - '&7will make them appear as one in the edit GUI'\n" +
                    "\n" +
                    "#Hourly Delivery Menu\n" +
                    "EMPTY_SUBMISSION: '&cCannot submit with empty delivery!'\n" +
                    "WRONG_SUBMISSION: '&cThe item is the not the one that needs to be delivered!'\n" +
                    "SUCCESSFUL_SUBMISSION: '<#40FB85>Delivery Successful!'\n" +
                    "ICON_ITEM_LORE:\n" +
                    "  - '&7Deliver the required item'\n" +
                    "  - '&7to claim your reward.'\n" +
                    "SUBMIT_ITEM_DISPLAY: '<#40FB85>&lConfirm Delivery'\n" +
                    "SUBMIT_ITEM_LORE:\n" +
                    "  - '&e========= WARNING ========='\n" +
                    "  - '&fThe item will be deducted'\n" +
                    "  - '&ffrom your inventory.'\n" +
                    "  - '&e=========================='\n" +
                    "SKIP_ITEM_DISPLAY: '<#40FB85>&lSkip Delivery'\n" +
                    "SKIP_ITEM_LORE:\n" +
                    "  - '&7Skip this delivery'\n" +
                    "  - '&7for &e{SKIP_COST}&7 tokens.'\n" +
                    "BACK_ITEM_DISPLAY: '&c⇦ Back'\n" +
                    "\n" +
                    "#Delivery Board Menu\n" +
                    "DB_DELIVERY_ALREADY_COMPLETE: '&cYou have already completed the delivery!'\n" +
                    "\n" +
                    "#>Hourly Item\n" +
                    "DB_ITEM_LORE:\n" +
                    "  - '&e---------------------------'\n" +
                    "  - '&7The town needs new deliveries'\n" +
                    "  - '&7all the time. Provide what the'\n" +
                    "  - '&7townsfolk needs and get rewarded.'\n" +
                    "  - '&e---------------------------'\n" +
                    "DB_ITEM_LORE_FOOTER_COMPLETE:\n" +
                    "  - '<#40FB85>COMPLETED'\n" +
                    "  - '&7Refreshes In: &a{DURATION}'      #{DURATION} returns the time remaining\n" +
                    "  - '&7Completed: &e{CURRENT_SUBMISSIONS}&7/&e{MAX_SUBMISSIONS}'\n" +
                    "DB_ITEM_LORE_FOOTER_INCOMPLETE:\n" +
                    "  - '&7Time Left: &e{DURATION}'         #{DURATION} returns the time remaining\n" +
                    "  - '&7Completed: &e{CURRENT_SUBMISSIONS}&7/&e{MAX_SUBMISSIONS}'");
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
