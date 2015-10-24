package com.geocar.alex.geocarapp.web;

import com.geocar.alex.geocarapp.LogCat;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by Badgerati on 24/10/2015.
 */
public class WebTask extends BaseAsyncTask<Request, Void, WebResponse>
{

    public WebTask(String tag)
    {
        super(tag);
    }

    @Override
    public WebResponse doInBackground(Request... requests)
    {
        try
        {
            if (requests == null || requests.length != 1)
            {
                return null;
            }

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(requests[0]).execute();
            return new WebResponse(response, requests[0].urlString());
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }

        return null;
    }

}
