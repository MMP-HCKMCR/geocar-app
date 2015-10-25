package com.geocar.alex.geocarapp.dto;

import com.geocar.alex.geocarapp.json.JsonDocument;

import java.util.ArrayList;

/**
 * Created by Badgerati on 25/10/2015.
 */
public class UserInfoResult extends BaseResult
{

    public boolean success = false;
    public String email = "";
    public String firstName = "";
    public String surname = "";
    public int totalPoints = 0;
    public int usablePoints = 0;
    public ArrayList<TransactionEntryResult> last5Transactions = null;


    public UserInfoResult(JsonDocument document)
    {
        errorMessage = document.getString("ErrorMessage");
        success = document.getBoolean("Success");
        email = document.getString("Email");
        firstName = document.getString("FirstName");
        surname = document.getString("Surname");
        totalPoints = document.getInt("TotalPoints");
        usablePoints = document.getInt("UsablePoints");

        ArrayList<JsonDocument> trans = document.getArray("Last5Transactions");
        last5Transactions = new ArrayList<>(trans.size());

        for (int i = 0; i < trans.size(); i++)
        {
            last5Transactions.add(new TransactionEntryResult(trans.get(i)));
        }
    }

}
