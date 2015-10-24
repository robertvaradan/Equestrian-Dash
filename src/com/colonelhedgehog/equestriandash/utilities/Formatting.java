package com.colonelhedgehog.equestriandash.utilities;

/**
 * Created by ColonelHedgehog on 9/7/15.
 */
public class Formatting
{
    public static String getPlaceColor(int place)
    {
        String string = String.valueOf(place);

        switch(place)
        {
            case 1: return "§6§l";
            case 2: return "§f§l";
            case 3: return "§c§l";
            case 4: return "§9";
            case 5: return "§5";
            case 6: return "§4";
            default: return "§7";
        }
    }

    public static String getPlaceSuffix(int place)
    {
        String suffix = "th";
        String string = String.valueOf(place);

        if (string.endsWith("11") || string.endsWith("12") || string.endsWith("14"))
        {
            return "th";
        }

        if (string.endsWith("1"))
        {
            suffix = "st";

        }
        else if (string.endsWith("2"))
        {
            suffix = "nd";

        }
        else if (string.endsWith("3"))
        {
            suffix = "rd";
        }

        return suffix;
    }

}
