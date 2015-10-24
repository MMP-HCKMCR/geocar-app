package com.geocar.alex.geocarapp.helpers;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Badgerati on 24/10/2015.
 */
public class ToastHelper
{

    public static void show(Context context, String value)
    {
        Toast toast = Toast.makeText(context, value, Toast.LENGTH_SHORT);
        toast.show();
    }

}
