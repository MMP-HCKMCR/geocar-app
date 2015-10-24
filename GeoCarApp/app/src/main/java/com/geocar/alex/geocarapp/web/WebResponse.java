package com.geocar.alex.geocarapp.web;

import com.geocar.alex.geocarapp.json.JsonDocument;
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

    public JsonDocument getBody() throws IOException
    {
        return new JsonDocument(mResponse.body().string());
    }

    public String getMessage()
    {
        return mResponse.message();
    }

}
