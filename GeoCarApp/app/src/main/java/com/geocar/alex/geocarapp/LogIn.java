package com.geocar.alex.geocarapp;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geocar.alex.geocarapp.dto.LoginResult;
import com.geocar.alex.geocarapp.helpers.ToastHelper;
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
        try
        {
            String data = "{\"EmailAddress\":\"" + mEmail.getText().toString() + "\", \"UserPassword\":\"" + mPassword.getText().toString() + "\"}";
            WebRequest.send("http://geocar.is-a-techie.com/api/login", data, "login", this);
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }
    }

    private void doRegister()
    {
        Intent i = new Intent(this, Register.class);
        startActivity(i);
    }

    @Override
    public <T> void onPostExecute(IAsyncTask asyncTask, T result, String tag)
    {
        try
        {
            LoginResult _result = new LoginResult(((WebResponse) result).getBody());

            if (!_result.isSuccessful())
            {
                ToastHelper.show(this, "Login unsuccessful");
            }
            else
            {
                Intent i = new Intent(this, Home.class);
                i.putExtra("SessionId", _result.sessionId);
                startActivity(i);
                finish();
            }
        }
        catch (IOException ex)
        {
            LogCat.error(this, ex);
            ToastHelper.show(this, "Login unsuccessful");
        }
    }

}
