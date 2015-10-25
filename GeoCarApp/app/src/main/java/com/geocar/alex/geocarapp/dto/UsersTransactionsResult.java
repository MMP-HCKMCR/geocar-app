package com.geocar.alex.geocarapp.dto;

import com.geocar.alex.geocarapp.json.JsonDocument;

import java.util.ArrayList;

/**
 * Created by Alex on 10/25/2015.
 */
public class UsersTransactionsResult extends BaseResult
{
    public ArrayList<TransactionEntryResult> transactions;

    public UsersTransactionsResult(JsonDocument document) {
        transactions = createTransactions(document.getArray("TransactionDetails"));

    }

    public ArrayList<TransactionEntryResult> createTransactions(ArrayList<JsonDocument> documents)
    {
        ArrayList<TransactionEntryResult> currentTrans = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++)
        {
            currentTrans.add(new TransactionEntryResult(documents.get(i)));
        }
        return currentTrans;
    }
}
