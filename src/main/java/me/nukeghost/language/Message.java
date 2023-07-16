package me.nukeghost.language;

import me.nukeghost.utils.ColorUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Message {
    ////Commands
//    public static String BOARD_COMMAND_DESCRIPTION;
//    public static String HELP_COMMAND_DESCRIPTION;

    //From YML
    //Commands
    //Board
    public static String BOARD_COMMAND_DESCRIPTION;
    //Help
    public static String HELP_COMMAND_DESCRIPTION;
    //Reload
    public static String RELOAD_COMMAND_DESCRIPTION;
    public static String RELOAD_SUCCESSFULL;
    public static String RELOAD_FAILED;
    public static String RELOAD_NO_PERM;
    //Setboard
    public static String SETBOARD_COMMAND_DESCRIPTION;
    public static String BOARD_SETUP_SUCCESSFULL;
    public static String BOARD_OVERRIDE_PREVIOUS;

    //Command Manager
    public static String CMDM_NO_PERM_DEFAULT;
    public static String CMDM_INVALID_COMMAND;
    public static String CMDM_HELP_SUGGEST;

    //Hourly Delivery Menu
    public static String HRD_EMPTY_SUBMISSION;
    public static String HRD_WRONG_SUBMISSION;
    public static String HRD_SUCCESSFUL_SUBMISSION;
    public static List<String> HRD_ICON_ITEM_LORE;
    public static String HRD_SUBMIT_ITEM_DISPLAY;
    public static List<String> HRD_SUBMIT_ITEM_LORE;
    public static String HRD_BACK_ITEM_DISPLAY;

    //Three hourly Delivery Menu
    public static String THD_EMPTY_SUBMISSION;
    public static String THD_WRONG_SUBMISSION;
    public static String THD_SUCCESSFUL_SUBMISSION;
    public static List<String> THD_ICON_ITEM_LORE;
    public static String THD_SUBMIT_ITEM_DISPLAY;
    public static List<String> THD_SUBMIT_ITEM_LORE;
    public static String THD_BACK_ITEM_DISPLAY;

    //Six hourly Delivery Menu
    public static String SHD_EMPTY_SUBMISSION;
    public static String SHD_WRONG_SUBMISSION;
    public static String SHD_SUCCESSFUL_SUBMISSION;
    public static List<String> SHD_ICON_ITEM_LORE;
    public static String SHD_SUBMIT_ITEM_DISPLAY;
    public static List<String> SHD_SUBMIT_ITEM_LORE;
    public static String SHD_BACK_ITEM_DISPLAY;

    //Delivery Board Menu
    public static String DB_DELIVERY_ALREADY_COMPLETE;

    //Hourly Item
    public static List<String> DB_HOURLY_ITEM_LORE;
    public static List<String> DB_HOURLY_ITEM_LORE_FOOTER_COMPLETE;
    public static List<String> DB_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE;

    //Three hourly Item
    public static List<String> DB_THREE_HOURLY_ITEM_LORE;
    public static List<String> DB_THREE_HOURLY_ITEM_LORE_FOOTER_COMPLETE;
    public static List<String> DB_THREE_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE;

    //Six hourly Item
    public static List<String> DB_SIX_HOURLY_ITEM_LORE;
    public static List<String> DB_SIX_HOURLY_ITEM_LORE_FOOTER_COMPLETE;
    public static List<String> DB_SIX_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE;
    //From YML - End
    public static void loadMessages() {
        FileConfiguration lang = LanguageConfig.getLangConfig();
        //BOARD_COMMAND_DESCRIPTION = lang.getString("BOARD_COMMAND_DESCRIPTION", "Default board desc loaded");

        //YML
//Commands
        //Board
        BOARD_COMMAND_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("BOARD_COMMAND_DESCRIPTION")));
        //Help
        HELP_COMMAND_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("HELP_COMMAND_DESCRIPTION")));
        //Reload
        RELOAD_COMMAND_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("RELOAD_COMMAND_DESCRIPTION")));
        RELOAD_SUCCESSFULL = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("RELOAD_SUCCESSFULL")));
        RELOAD_FAILED = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("RELOAD_FAILED")));
        RELOAD_NO_PERM = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("RELOAD_NO_PERM")));
        //Setboard
        SETBOARD_COMMAND_DESCRIPTION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("SETBOARD_COMMAND_DESCRIPTION")));
        BOARD_SETUP_SUCCESSFULL = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("BOARD_SETUP_SUCCESSFULL")));
        BOARD_OVERRIDE_PREVIOUS = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("BOARD_OVERRIDE_PREVIOUS")));

//Command Manager
        CMDM_NO_PERM_DEFAULT = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("CMDM_NO_PERM_DEFAULT")));
        CMDM_INVALID_COMMAND = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("CMDM_INVALID_COMMAND")));
        CMDM_HELP_SUGGEST = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("CMDM_HELP_SUGGEST")));

//Hourly Delivery Menu
        HRD_EMPTY_SUBMISSION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("HRD_EMPTY_SUBMISSION")));
        HRD_WRONG_SUBMISSION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("HRD_WRONG_SUBMISSION")));
        HRD_SUCCESSFUL_SUBMISSION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("HRD_SUCCESSFUL_SUBMISSION")));
        HRD_ICON_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("HRD_ICON_ITEM_LORE")));
        HRD_SUBMIT_ITEM_DISPLAY = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("HRD_SUBMIT_ITEM_DISPLAY")));
        HRD_SUBMIT_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("HRD_SUBMIT_ITEM_LORE")));
        HRD_BACK_ITEM_DISPLAY = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("HRD_BACK_ITEM_DISPLAY")));

//Three hourly Delivery Menu
        THD_EMPTY_SUBMISSION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("THD_EMPTY_SUBMISSION")));
        THD_WRONG_SUBMISSION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("THD_WRONG_SUBMISSION")));
        THD_SUCCESSFUL_SUBMISSION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("THD_SUCCESSFUL_SUBMISSION")));
        THD_ICON_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("THD_ICON_ITEM_LORE")));
        THD_SUBMIT_ITEM_DISPLAY = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("THD_SUBMIT_ITEM_DISPLAY")));
        THD_SUBMIT_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("THD_SUBMIT_ITEM_LORE")));
        THD_BACK_ITEM_DISPLAY = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("THD_BACK_ITEM_DISPLAY")));

//Six hourly Delivery Menu
        SHD_EMPTY_SUBMISSION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("SHD_EMPTY_SUBMISSION")));
        SHD_WRONG_SUBMISSION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("SHD_WRONG_SUBMISSION")));
        SHD_SUCCESSFUL_SUBMISSION = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("SHD_SUCCESSFUL_SUBMISSION")));
        SHD_ICON_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("SHD_ICON_ITEM_LORE")));
        SHD_SUBMIT_ITEM_DISPLAY = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("SHD_SUBMIT_ITEM_DISPLAY")));
        SHD_SUBMIT_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("SHD_SUBMIT_ITEM_LORE")));
        SHD_BACK_ITEM_DISPLAY = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("SHD_BACK_ITEM_DISPLAY")));

//Delivery Board Menu
        DB_DELIVERY_ALREADY_COMPLETE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getString("DB_DELIVERY_ALREADY_COMPLETE")));

        //Hourly Item
        DB_HOURLY_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("DB_HOURLY_ITEM_LORE")));
        DB_HOURLY_ITEM_LORE_FOOTER_COMPLETE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("DB_HOURLY_ITEM_LORE_FOOTER_COMPLETE")));
        DB_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("DB_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE")));
        DB_THREE_HOURLY_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("DB_THREE_HOURLY_ITEM_LORE")));
        DB_THREE_HOURLY_ITEM_LORE_FOOTER_COMPLETE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("DB_THREE_HOURLY_ITEM_LORE_FOOTER_COMPLETE")));
        DB_THREE_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("DB_THREE_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE")));
        //Six hourly Item
        DB_SIX_HOURLY_ITEM_LORE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("DB_SIX_HOURLY_ITEM_LORE")));
        DB_SIX_HOURLY_ITEM_LORE_FOOTER_COMPLETE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("DB_SIX_HOURLY_ITEM_LORE_FOOTER_COMPLETE")));
        DB_SIX_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(lang.getStringList("DB_SIX_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE")));

        //YML - End
    }
}