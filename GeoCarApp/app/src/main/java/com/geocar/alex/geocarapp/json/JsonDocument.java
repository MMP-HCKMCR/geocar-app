package com.geocar.alex.geocarapp.json;

import com.geocar.alex.geocarapp.LogCat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Badgerati on 24/10/2015.
 */
public class JsonDocument
{

    private String mRaw = "";
    private JSONObject mJson = null;


    public JsonDocument(String raw)
    {
        mRaw = raw;

        try
        {
            mJson = new JSONObject(mRaw);
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
            mJson = null;
        }
    }

    public JsonDocument(JSONObject json)
    {
        mRaw = json == null ? "" : json.toString();
        mJson = json;
    }

    public ArrayList<JsonDocument> getArray(String tag)
    {
        if (mJson == null)
        {
            return null;
        }

        try
        {
            JSONArray array = mJson.getJSONArray(tag);
            ArrayList<JsonDocument> docs = new ArrayList<>(array.length());

            for (int i = 0; i < array.length(); i++)
            {
                docs.add(new JsonDocument(array.getJSONObject(i)));
            }

            return docs;
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }

        return null;
    }

    public JsonDocument getObject(String tag)
    {
        try
        {
            return new JsonDocument(mJson.getJSONObject(tag));
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }

        return null;
    }

    public String getString(String tag)
    {
        try
        {
            return mJson.getString(tag);
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }

        return null;
    }

    public int getInt(String tag)
    {
        try
        {
            return mJson.getInt(tag);
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }

        return 0;
    }

    public boolean getBoolean(String tag)
    {
        try
        {
            return mJson.getBoolean(tag);
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }

        return false;
    }

    public Calendar getCalendar(String tag)
    {
        try
        {
            String date = mJson.getString(tag);
            int ix1 = date.indexOf('(');
            int ix2 = date.indexOf(')');
            int ix3 = date.indexOf('+');

            if (ix3 != -1)
            {
                ix2 = ix3;
            }

            String subDate = date.substring(ix1 + 1, ix2);
            long millis = Long.valueOf(subDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);
            return calendar;
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }

        return Calendar.getInstance();
    }

    @Override
    public String toString()
    {
        return mRaw;
    }

}
