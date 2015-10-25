package com.geocar.alex.geocarapp.web;

import com.geocar.alex.geocarapp.LogCat;
import com.geocar.alex.geocarapp.json.JsonDocument;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by Badgerati on 24/10/2015.
 */
public class WebTask extends BaseAsyncTask<Request, Void, JsonDocument>
{

    public WebTask(String tag)
    {
        super(tag);
    }

    @Override
    public JsonDocument doInBackground(Request... requests)
    {
        try
        {
            if (requests == null || requests.length != 1)
            {
                return null;
            }

            Random random = new Random(Calendar.getInstance().getTimeInMillis());
            Thread.sleep(1000 + random.nextInt(1000));
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(requests[0]).execute();
            WebResponse _response = new WebResponse(response, requests[0].urlString());

            return _response.getBody();
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }

        return null;
    }

}
