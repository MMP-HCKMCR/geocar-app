package com.geocar.alex.geocarapp.dto;

import com.geocar.alex.geocarapp.json.JsonDocument;

import java.util.ArrayList;

/**
 * Created by Badgerati on 25/10/2015.
 */
public class AchievementResult extends BaseResult
{

    public ArrayList<AchievementEntryResult> remainingAchievements = null;
    public ArrayList<AchievementEntryResult> usersAchievements = null;


    public AchievementResult(JsonDocument document)
    {
        ArrayList<JsonDocument> remaining = document.getArray("RemainingAchievements");
        remainingAchievements = new ArrayList<>(remaining.size());

        for (int i = 0; i < remaining.size(); i++)
        {
            remainingAchievements.add(new AchievementEntryResult(remaining.get(i)));
        }

        ArrayList<JsonDocument> users = document.getArray("UsersAchievements");
        usersAchievements = new ArrayList<>(users.size());

        for (int i = 0; i < users.size(); i++)
        {
            usersAchievements.add(new AchievementEntryResult(users.get(i)));
        }
    }

}
