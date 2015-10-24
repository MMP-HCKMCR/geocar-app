package com.geocar.alex.geocarapp.dto;

/**
 * Created by Badgerati on 24/10/2015.
 */
public class BaseResult
{

    public String errorMessage = "";

    public BaseResult()
    {

    }

    public boolean isSuccessful()
    {
        return errorMessage == null || errorMessage.equals("");
    }

}
