package com.geocar.alex.geocarapp.dto;

import com.geocar.alex.geocarapp.json.JsonDocument;

import java.util.Calendar;

/**
 * Created by Alex on 10/25/2015.
 */
public class LeaderboardEntryResult extends BaseResult
{
    public int position;
    public String firstName;
    public String surName;
    public Calendar lastScoreTime;
    public int score;
    public boolean isCurrentUser;

    public LeaderboardEntryResult(JsonDocument document)
    {
        this.position = document.getInt("Position");
        this.firstName = document.getString("FirstName");
        this.surName = document.getString("Surname");
        this.lastScoreTime = document.getCalendar("LastScoreTime");
        this.score = document.getInt("Score");
        this.isCurrentUser = document.getBoolean("IsCurrentUser");
    }
}
