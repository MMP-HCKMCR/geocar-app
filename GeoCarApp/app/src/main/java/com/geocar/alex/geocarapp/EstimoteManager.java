package com.geocar.alex.geocarapp;

import android.content.Context;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.geocar.alex.geocarapp.web.IAsyncTask;
import com.geocar.alex.geocarapp.web.WebRequest;

import java.util.List;
import java.util.UUID;

public class EstimoteManager implements IAsyncTask.OnPostExecuteListener
{

    private BeaconManager mBeaconManager = null;
    private Region mRegion = null;
    private String mSessionId = "";


    public EstimoteManager(String sessionId)
    {
        mSessionId = sessionId;
    }

    protected void startRanging(Context context)
    {
        mBeaconManager = new BeaconManager(context);
        mRegion = new Region("Range Region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);

        mBeaconManager.connect(new BeaconManager.ServiceReadyCallback()
        {
            @Override
            public void onServiceReady()
            {
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

                        String data = "{"
                                + "\"SessionId\":\"" + mSessionId + "\""
                                + "\"BeaconId\":\"" + beacon.getProximityUUID() + "\""
                                + "\"BeaconMajorVersion\":\"" + beacon.getMajor() + "\""
                                + "\"BeaconMinorVersion\":\"" + beacon.getMinor() + "\""
                                + "}";
                        WebRequest.send("http://geocar.is-a-techie.com/api/registertag", data, "registertag", _this);

                        LogCat.log(this, Integer.toString(list.get(i).getMajor()));
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
    }

}
