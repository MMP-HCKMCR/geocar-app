package com.geocar.alex.geocarapp;

import android.util.Log;

/**
 * Created by Badgerati on 24/10/2015.
 */
public class LogCat
{

    public static void log(Object tag, Object message)
    {
        Log.v(
                tag == null ? "NULL" : tag.getClass().getSimpleName(),
                message == null ? "NULL" : message.toString());
    }

    public static void error(Object tag, Object message)
    {
        Log.v(
                tag == null ? "NULL" : tag.getClass().getSimpleName(),
                "ERROR: " + (message == null ? "NULL" : message.toString()));
    }

}
