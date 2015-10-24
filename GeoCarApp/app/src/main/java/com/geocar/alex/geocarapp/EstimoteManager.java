package com.geocar.alex.geocarapp;

import android.content.Context;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.geocar.alex.geocarapp.json.JsonDocument;
import com.geocar.alex.geocarapp.web.IAsyncTask;
import com.geocar.alex.geocarapp.web.WebRequest;
import com.geocar.alex.geocarapp.web.WebResponse;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EstimoteManager implements IAsyncTask.OnPostExecuteListener
{

    private BeaconManager mBeaconManager = null;
    private Region mRegion = null;
    private String mSessionId = "";
    private HashMap<String, Long> mBeaconCache = null;


    public EstimoteManager(String sessionId)
    {
        mSessionId = sessionId;
        mBeaconCache = new HashMap<>(50);
    }

    protected void startRanging(Context context)
    {
        mBeaconManager = new BeaconManager(context);
        mRegion = new Region("Range Region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);

        mBeaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                mBeaconManager.startRanging(mRegion);
            }
        });

        final EstimoteManager _this = this;

        mBeaconManager.setRangingListener(new BeaconManager.RangingListener()
        {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list)
            {
                if (!list.isEmpty())
                {
                    for (int i = 0; i < list.size(); i++)
                    {
                        Beacon beacon = list.get(i);
                        String id = beacon.getMajor() + ":" + beacon.getMajor();

                        if (mBeaconCache.containsKey(id))
                        {
                            continue;
                        }

                        mBeaconCache.put(id, 0l);

                        String data = "{"
                                + "\"SessionId\":\"" + mSessionId + "\","
                                + "\"BeaconId\":\"" + beacon.getProximityUUID() + "\","
                                + "\"BeaconMajorVersion\":\"" + beacon.getMajor() + "\","
                                + "\"BeaconMinorVersion\":\"" + beacon.getMinor() + "\""
                                + "}";
                        LogCat.log(_this, data);
                        WebRequest.send("http://geocar.is-a-techie.com/api/registertag", data, "registertag", _this);
                    }
                }
                else
                {
                    LogCat.log(this, "List is EMPTY!");
                }
            }
        });
    }

    protected void stopRanging(Context context)
    {
        mBeaconManager.stopRanging(mRegion);
    }

    @Override
    public <T> void onPostExecute(IAsyncTask asyncTask, T result, String tag)
    {
        try
        {
            LogCat.log(this, ((JsonDocument)result));
        }
        catch (Exception ex)
        {
            LogCat.error(this, "here" + ex);
        }
    }

}
