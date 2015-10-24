package com.geocar.alex.geocarapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.geocar.alex.geocarapp.web.IAsyncTask;

public class Register extends AppCompatActivity implements View.OnClickListener, IAsyncTask.OnPostExecuteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public <T> void onPostExecute(IAsyncTask asyncTask, T result, String tag) {

    }

    @Override
    public void onClick(View v) {

    }
}
