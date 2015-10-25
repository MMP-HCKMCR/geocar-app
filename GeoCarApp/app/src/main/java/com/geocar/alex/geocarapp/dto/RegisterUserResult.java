package com.geocar.alex.geocarapp.dto;

import com.geocar.alex.geocarapp.LogCat;
import com.geocar.alex.geocarapp.json.JsonDocument;

/**
 * Created by Alex on 10/25/2015.
 */
public class RegisterUserResult extends BaseResult
{
    public String sessionId = "";

    public RegisterUserResult(JsonDocument document)
    {
        LogCat.log(this, document);
        errorMessage = document.getString("ErrorMessage");
        sessionId = document.getString("SessionId");
    }
}
