package com.geocar.alex.geocarapp.web;

import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Badgerati on 24/10/2015.
 */
public class WebResponse
{

    private Response mResponse = null;
    private String mUrl = "";


    public WebResponse(Response response, String url)
    {
        mResponse = response;
        mUrl = url;
    }

    public int getStatusCode()
    {
        return mResponse.code();
    }

    public boolean isSuccessful()
    {
        return mResponse.isSuccessful();
    }

    public String getUrl()
    {
        return mUrl;
    }

    public String getBody() throws IOException
    {
        return mResponse.body().string();
    }

    public String getMessage()
    {
        return mResponse.message();
    }

}
