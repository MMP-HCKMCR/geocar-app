package com.geocar.alex.geocarapp.dto;

import com.geocar.alex.geocarapp.LogCat;
import com.geocar.alex.geocarapp.json.JsonDocument;

import java.util.Locale;

/**
 * Created by Badgerati on 24/10/2015.
 */
public class LoginResult extends BaseResult
{

    public boolean success = false;
    public String sessionId = "";
    public int userId = 0;

    public LoginResult(JsonDocument document)
    {
        LogCat.log(this,document);
        errorMessage = document.getString("ErrorMessage");
        success = document.getBoolean("Success");
        sessionId = document.getString("SessionId");
        userId = document.getInt("UserId");
        LogCat.log(this,errorMessage);
    }

}
