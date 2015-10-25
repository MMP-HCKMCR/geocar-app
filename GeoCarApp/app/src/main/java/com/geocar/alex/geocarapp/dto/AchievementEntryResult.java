package com.geocar.alex.geocarapp.dto;

import com.geocar.alex.geocarapp.json.JsonDocument;

/**
 * Created by Badgerati on 25/10/2015.
 */
public class AchievementEntryResult extends BaseResult
{

    public String description = "";
    public String name = "";


    public AchievementEntryResult(JsonDocument document)
    {
        description = document.getString("AchievementDescription");
        name = document.getString("AchievementName");
    }

}
