package com.geocar.alex.geocarapp;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geocar.alex.geocarapp.web.IAsyncTask;
import com.geocar.alex.geocarapp.web.WebRequest;
import com.geocar.alex.geocarapp.web.WebResponse;

import java.io.IOException;

public class LogIn extends AppCompatActivity implements View.OnClickListener, IAsyncTask.OnPostExecuteListener
{

    private EditText mEmail = null;
    private EditText mPassword = null;
    private Button mSignIn = null;
    private TextView mRegister = null;


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

        try
        {
            WebRequest.send("http://geocar.is-a-techie.com/api/marco", "", this);
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }
    }

    private void doRegister()
    {
        //TODO: Register activity
        LogCat.log(this, "HERE2");
    }

    @Override
    public <T> void onPostExecute(IAsyncTask asyncTask, T result)
    {
        try
        {
            LogCat.log(this, ((WebResponse) result).getBody());
        }
        catch (IOException ex)
        {
            LogCat.error(this, ex);
        }
    }
}
