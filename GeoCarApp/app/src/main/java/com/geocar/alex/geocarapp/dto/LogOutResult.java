package com.geocar.alex.geocarapp.dto;

import com.geocar.alex.geocarapp.json.JsonDocument;

/**
 * Created by Alex on 10/24/2015.
 */
public class LogOutResult extends BaseResult{

    public boolean success = false;

    public LogOutResult(JsonDocument document)
    {
        errorMessage = document.getString("ErrorMessage");
        success = document.getBoolean("Success");
    }
}
