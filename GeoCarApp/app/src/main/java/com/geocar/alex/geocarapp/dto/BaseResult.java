package com.geocar.alex.geocarapp.dto;

/**
 * Created by Badgerati on 24/10/2015.
 */
public class BaseResult
{

    public int errorId = 0;

    public BaseResult()
    {

    }

    public boolean isSuccessful()
    {
        return errorId == 0;
    }

}
