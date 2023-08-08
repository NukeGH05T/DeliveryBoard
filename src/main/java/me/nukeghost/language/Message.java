package me.nukeghost.language;

import me.nukeghost.utils.ColorUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Message {
    //From YML
    //Commands
    //Add
    public static String ADD_COMMAND_DESCRIPTION;
    public static String ADD_INVALID_ITEM;
    //Edit
    public static String EDIT_COMMAND_DESCRIPTION;
    //Board
    public static String BOARD_COMMAND_DESCRIPTION;
    //Help
    public static String HELP_COMMAND_DESCRIPTION;
    //Reload
    public static String RELOAD_COMMAND_DESCRIPTION;
    public static String RELOAD_SUCCESSFULL;
    public static String RELOAD_FAILED;
    public static String RELOAD_NO_PERM;
    //Refresh
    public static String REFRESH_COMMAND_DESCRIPTION;
    //Setboard
    public static String SETBOARD_COMMAND_DESCRIPTION;
    public static String BOARD_SETUP_SUCCESSFULL;
    public static String BOARD_OVERRIDE_PREVIOUS;

    //Command Manager
    public static String CMDM_NO_PERM_DEFAULT;
    public static String CMDM_INVALID_COMMAND;
    public static String CMDM_HELP_SUGGEST;

    //Token Manager
    public static String TOKEN_GIVE_DESCRIPTION;
    public static String TOKEN_RESET_DESCRIPTION;
    public static String TOKEN_TAKE_DESCRIPTION;
    public static String TOKEN_BALANCE_DESCRIPTION;
    public static String TOKEN_BALANCE_NO_PERM;
    public static String TOKEN_BALANCE;
    public static String INSUFFICIENT_TOKENS;

    //Skip
    public static String SKIP_SUCCESSFUL;

    //Reminder
    public static String REFRESH_REMINDER;

    //Paginated Menu
    public static String ON_FIRST_PAGE;
    public static String ON_LAST_PAGE;
    public static String PGN_NEXT_PAGE;
    public static String PGN_PREVIOUS_PAGE;
    public static String PGN_CLOSE_PAGE;
    public static String PGN_INFO_ITEM;
    public static List<String> PGN_INFO_DETAILS;

    //Hourly Delivery Menu
    public static String EMPTY_SUBMISSION;
    public static String WRONG_SUBMISSION;
    public static String SUCCESSFUL_SUBMISSION;
    public static List<String> ICON_ITEM_LORE;
    public static String SUBMIT_ITEM_DISPLAY;
    public static List<String> SUBMIT_ITEM_LORE;
    public static String SKIP_ITEM_DISPLAY;
    public static List<String> SKIP_ITEM_LORE;
    public static String BACK_ITEM_DISPLAY;

    //Delivery Board Menu
    public static String DB_DELIVERY_ALREADY_COMPLETE;

    //Hourly Item
    public static List<String> DB_ITEM_LORE;
    public static List<String> DB_ITEM_LORE_FOOTER_COMPLETE;
    public static List<String> DB_ITEM_LORE_FOOTER_INCOMPLETE;
    //From YML - End
    public static void loadMessages() {
        FileConfiguration lang = LanguageConfig.getLangConfig();
        //BOARD_COMMAND_DESCRIPTION = lang.getString("BOARD_COMMAND_DESCRIPTION", "Default board desc loaded");

        //YML
        //Commands
        //Add
        ADD_COMMAND_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("ADD_COMMAND_DESCRIPTION")));
        ADD_INVALID_ITEM = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("ADD_INVALID_ITEM")));
        //Edit
        EDIT_COMMAND_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("EDIT_COMMAND_DESCRIPTION")));
        //Board
        BOARD_COMMAND_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("BOARD_COMMAND_DESCRIPTION")));
        //Help
        HELP_COMMAND_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("HELP_COMMAND_DESCRIPTION")));
        //Reload
        RELOAD_COMMAND_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("RELOAD_COMMAND_DESCRIPTION")));
        RELOAD_SUCCESSFULL = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("RELOAD_SUCCESSFULL")));
        RELOAD_FAILED = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("RELOAD_FAILED")));
        RELOAD_NO_PERM = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("RELOAD_NO_PERM")));
        //Refresh
        REFRESH_COMMAND_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("REFRESH_COMMAND_DESCRIPTION")));
        //Setboard
        SETBOARD_COMMAND_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("SETBOARD_COMMAND_DESCRIPTION")));
        BOARD_SETUP_SUCCESSFULL = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("BOARD_SETUP_SUCCESSFULL")));
        BOARD_OVERRIDE_PREVIOUS = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("BOARD_OVERRIDE_PREVIOUS")));

        //Command Manager
        CMDM_NO_PERM_DEFAULT = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("CMDM_NO_PERM_DEFAULT")));
        CMDM_INVALID_COMMAND = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("CMDM_INVALID_COMMAND")));
        CMDM_HELP_SUGGEST = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("CMDM_HELP_SUGGEST")));

        //Token Manager
        TOKEN_GIVE_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("TOKEN_GIVE_DESCRIPTION")));
        TOKEN_RESET_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("TOKEN_RESET_DESCRIPTION")));
        TOKEN_TAKE_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("TOKEN_TAKE_DESCRIPTION")));
        TOKEN_BALANCE_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("TOKEN_BALANCE_DESCRIPTION")));
        TOKEN_BALANCE_NO_PERM = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("TOKEN_BALANCE_NO_PERM")));
        TOKEN_BALANCE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("TOKEN_BALANCE")));
        INSUFFICIENT_TOKENS = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("INSUFFICIENT_TOKENS")));

        //Skip
        SKIP_SUCCESSFUL = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("SKIP_SUCCESSFUL")));

        //Reminder
        REFRESH_REMINDER = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("REFRESH_REMINDER")));

        //Paginated Menu
        ON_FIRST_PAGE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("ON_FIRST_PAGE")));
        ON_LAST_PAGE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("ON_LAST_PAGE")));
        PGN_NEXT_PAGE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("PGN_NEXT_PAGE")));
        PGN_PREVIOUS_PAGE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("PGN_PREVIOUS_PAGE")));
        PGN_CLOSE_PAGE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("PGN_CLOSE_PAGE")));
        PGN_INFO_ITEM = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("PGN_INFO_ITEM")));
        PGN_INFO_DETAILS = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("PGN_INFO_DETAILS")));

//Hourly Delivery Menu
        EMPTY_SUBMISSION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("EMPTY_SUBMISSION")));
        WRONG_SUBMISSION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("WRONG_SUBMISSION")));
        SUCCESSFUL_SUBMISSION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("SUCCESSFUL_SUBMISSION")));
        ICON_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("ICON_ITEM_LORE")));
        SUBMIT_ITEM_DISPLAY = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("SUBMIT_ITEM_DISPLAY")));
        SUBMIT_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("SUBMIT_ITEM_LORE")));
        SKIP_ITEM_DISPLAY = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("SKIP_ITEM_DISPLAY")));
        SKIP_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("SKIP_ITEM_LORE")));
        BACK_ITEM_DISPLAY = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("BACK_ITEM_DISPLAY")));


//Delivery Board Menu
        DB_DELIVERY_ALREADY_COMPLETE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("DB_DELIVERY_ALREADY_COMPLETE")));

        //Hourly Item
        DB_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("DB_ITEM_LORE")));
        DB_ITEM_LORE_FOOTER_COMPLETE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("DB_ITEM_LORE_FOOTER_COMPLETE")));
        DB_ITEM_LORE_FOOTER_INCOMPLETE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("DB_ITEM_LORE_FOOTER_INCOMPLETE")));

        //YML - End
    }
}