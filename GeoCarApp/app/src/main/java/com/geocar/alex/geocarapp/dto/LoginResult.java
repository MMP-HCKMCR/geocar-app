package com.geocar.alex.geocarapp.dto;

import com.geocar.alex.geocarapp.json.JsonDocument;

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
        JsonDocument result = document.getObject("LoginResult");
        errorId = result.getInt("ErrorId");
        success = result.getBoolean("Success");
        sessionId = result.getString("SessionId");
        userId = result.getInt("UserId");
    }

}
