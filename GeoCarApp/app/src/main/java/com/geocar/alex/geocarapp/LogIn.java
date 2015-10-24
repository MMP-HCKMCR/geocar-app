package com.geocar.alex.geocarapp;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.UUID;

public class LogIn extends AppCompatActivity implements View.OnClickListener
{

    private EditText mEmail = null;
    private EditText mPassword = null;
    private Button mSignIn = null;
    private TextView mRegister = null;
    private BeaconManager beaconManager;
    private EstimoteManager estimoteManager;
    private Region region;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEmail = (EditText)findViewById(R.id.email_txt);
        mPassword = (EditText)findViewById(R.id.password_txt);

        mSignIn = (Button)findViewById(R.id.signin_btn);
        mSignIn.setOnClickListener(this);

        mRegister = (TextView)findViewById(R.id.register_lnk);
        mRegister.setPaintFlags(mRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mRegister.setOnClickListener(this);

        estimoteManager = new EstimoteManager();

        beaconManager = new BeaconManager(getApplicationContext());
        region = new Region("Range Region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
        estimoteManager.startRanging(getApplicationContext());
    }

    @Override
    public void onClick(View v)
    {
        if (mSignIn.equals(v))
        {
            doSignIn();
        }
        else if (mRegister.equals(v))
        {
            doRegister();
        }
    }

    private void doSignIn()
    {
        //TODO: Send call to backend
        LogCat.log(this, "HERE");
    }

    private void doRegister()
    {
        //TODO: Register activity
        LogCat.log(this, "HERE2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }


    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        beaconManager.stopRanging(region);
        super.onDestroy();
    }
}
