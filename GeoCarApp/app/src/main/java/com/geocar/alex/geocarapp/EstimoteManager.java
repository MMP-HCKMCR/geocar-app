package com.geocar.alex.geocarapp;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.geocar.alex.geocarapp.dto.RegisterTagResult;
import com.geocar.alex.geocarapp.json.JsonDocument;
import com.geocar.alex.geocarapp.web.IAsyncTask;
import com.geocar.alex.geocarapp.web.WebRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EstimoteManager implements IAsyncTask.OnPostExecuteListener
{

    private BeaconManager mBeaconManager = null;
    private Region mRegion = null;
    private String mSessionId = "";
    private Context mContext = null;
    private HashMap<String, Long> mBeaconCache = null;


    public EstimoteManager(String sessionId)
    {
        mSessionId = sessionId;
        mBeaconCache = new HashMap<>(50);
    }

    protected void startRanging(Context context)
    {
        mContext = context;
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
                        long millis = Calendar.getInstance().getTimeInMillis();

                        if (mBeaconCache.containsKey(id) && mBeaconCache.get(id) > millis)
                        {
                            continue;
                        }

                        mBeaconCache.put(id, millis);

                        String data = "{"
                                + "\"SessionId\":\"" + mSessionId + "\","
                                + "\"BeaconId\":\"" + beacon.getProximityUUID() + "\","
                                + "\"BeaconMajorVersion\":\"" + beacon.getMajor() + "\","
                                + "\"BeaconMinorVersion\":\"" + beacon.getMinor() + "\""
                                + "}";

                        WebRequest.send("http://geocar.is-a-techie.com/api/registertag", data, id, _this);
                    }
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
            RegisterTagResult _result = new RegisterTagResult((JsonDocument)result);
            long millis = Calendar.getInstance().getTimeInMillis();

            if (_result.errorMessage != null && _result.errorMessage.toLowerCase().contains("timeout"))
            {
                millis += 30000;
            }
            else
            {
                int lockout = (_result.lockoutTime == 0 ? 1 : _result.lockoutTime);
                millis += ((_result.isSuccessful() ? lockout : 60) * 60000);
            }

            mBeaconCache.put(tag, millis);

            if (_result.isSuccessful() && _result.pointsScored > 0)
            {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                        .setSmallIcon(android.support.design.R.drawable.abc_btn_rating_star_on_mtrl_alpha)
                        .setContentTitle("GeoCar Points")
                        .setContentText("You gained " + _result.pointsScored + " points! Total: " + _result.usablePoints);
                NotificationManager manager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(1, builder.build());
            }

            if (_result.isSuccessful() && _result.achievements != null && _result.achievements.size() > 0)
            {
                for (int i = 0; i < _result.achievements.size(); i++)
                {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                            .setSmallIcon(android.support.design.R.drawable.abc_btn_rating_star_on_mtrl_alpha)
                            .setContentTitle("GeoCar Achievement Earned")
                            .setContentText(_result.achievements.get(i));
                    NotificationManager manager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(100 + i, builder.build());
                }
            }
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }
    }

}
