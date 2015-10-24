package com.geocar.alex.geocarapp.dto;

import com.geocar.alex.geocarapp.dto.LeaderboardEntryResult;
import com.geocar.alex.geocarapp.json.JsonDocument;

import java.util.ArrayList;

/**
 * Created by Alex on 10/24/2015.
 */
public class LeaderboardResult extends BaseResult
{

    public boolean success = false;
    public int currentRanking = -1;
    public ArrayList<LeaderboardEntryResult> twoAbove;
    public ArrayList<LeaderboardEntryResult> twoBelow;
    public ArrayList<LeaderboardEntryResult> topTen;

    public LeaderboardResult(JsonDocument document)
    {
        errorMessage = document.getString("ErrorMessage");
        success = document.getBoolean("Success");
        currentRanking = document.getInt("CurrentRanking");
        twoAbove = createLeaderboardEntries(document.getArray("twoAbove"));
        twoBelow = createLeaderboardEntries(document.getArray("twoBelow"));
        topTen = createLeaderboardEntries(document.getArray("topTen"));
    }

    public ArrayList<LeaderboardEntryResult> createLeaderboardEntries(ArrayList<JsonDocument> documents)
    {
        ArrayList<LeaderboardEntryResult> entryArray = new ArrayList<>();

        for (int i = 0; i < documents.size();i++)
        {
            LeaderboardEntryResult newEntry = new LeaderboardEntryResult(documents.get(i));
            entryArray.add(newEntry);
        }

        return entryArray;
    }
}
