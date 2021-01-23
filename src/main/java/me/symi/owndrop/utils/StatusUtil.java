package me.symi.owndrop.utils;

import me.symi.owndrop.Main;

public class StatusUtil {

    public static String getCheckMark()
    {
        return Main.getInstance().getConfigManager().getCheck_mark();
    }

    public static String getCrossMark()
    {
        return Main.getInstance().getConfigManager().getCross_mark();
    }

}
