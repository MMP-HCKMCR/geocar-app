package com.geocar.alex.geocarapp.web;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

/**
 * Created by Badgerati on 24/10/2015.
 */
public class WebRequest
{

    public static void send(String url, String data, IAsyncTask.OnPostExecuteListener listener)
    {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), data);
        Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();

        WebTask task = new WebTask();
        task.setOnPostExecuteListener(listener);
        task.executor(request);
    }

}
