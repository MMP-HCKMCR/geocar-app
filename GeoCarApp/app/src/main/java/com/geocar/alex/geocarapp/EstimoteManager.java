package com.geocar.alex.geocarapp;

import android.content.Context;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

public class EstimoteManager {
    private BeaconManager beaconManager;
    private Region region;

    protected void startRanging(Context context) {

        beaconManager = new BeaconManager(context);

        region = new Region("Range Region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        LogCat.log(this, Integer.toString(list.get(i).getMajor()));
                    }
                } else {
                    LogCat.log(this, "List is EMPTY!");
                }
            }
        });
    }

    protected void stopRanging(Context context){
        beaconManager.stopRanging(region);
    }
}
