package com.geocar.alex.geocarapp.dto;

import com.geocar.alex.geocarapp.LogCat;
import com.geocar.alex.geocarapp.json.JsonDocument;

import java.util.ArrayList;

/**
 * Created by Badgerati on 24/10/2015.
 */
public class RegisterTagResult extends BaseResult
{

    public boolean success = false;
    public int lockoutTime = 0;
    public int newPointsTotal = 0;
    public int pointsScored = 0;
    public int ranking = 0;
    public int usablePoints = 0;
    public ArrayList<String> achievements = null;

    public RegisterTagResult(JsonDocument document)
    {
        errorMessage = document.getString("ErrorMessage");
        success = document.getBoolean("Success");
        lockoutTime = document.getInt("LockoutTime");
        newPointsTotal = document.getInt("NewPointsTotal");
        pointsScored = document.getInt("PointsScored");
        ranking = document.getInt("Ranking");
        usablePoints = document.getInt("UsablePoints");
        achievements = document.getStringArray("Achievements");
    }

}
