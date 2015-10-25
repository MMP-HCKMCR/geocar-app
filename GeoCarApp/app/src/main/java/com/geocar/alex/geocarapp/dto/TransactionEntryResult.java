package com.geocar.alex.geocarapp.dto;

import com.geocar.alex.geocarapp.json.JsonDocument;

import java.util.Calendar;

/**
 * Created by Badgerati on 25/10/2015.
 */
public class TransactionEntryResult extends BaseResult
{

    public int points = 0;
    public Calendar timeCaptured = null;
    public int transactionId = 0;
    public String transactionType = "";
    public int userId = 0;


    public TransactionEntryResult(JsonDocument document)
    {
        points = document.getInt("Points");
        timeCaptured = document.getCalendar("TimeCaptured");
        transactionId = document.getInt("TransactionId");
        transactionType = document.getString("TransactionType");
        userId = document.getInt("UserId");
    }

}
