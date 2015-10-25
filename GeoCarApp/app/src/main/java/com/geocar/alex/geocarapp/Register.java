package com.geocar.alex.geocarapp;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.geocar.alex.geocarapp.dto.RegisterUserResult;
import com.geocar.alex.geocarapp.helpers.ToastHelper;
import com.geocar.alex.geocarapp.json.JsonDocument;
import com.geocar.alex.geocarapp.web.IAsyncTask;
import com.geocar.alex.geocarapp.web.WebRequest;

public class Register extends AppCompatActivity implements View.OnClickListener, IAsyncTask.OnPostExecuteListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerBtn = (Button)findViewById(R.id.register_btn);
        final EditText emailTextBox = (EditText)findViewById(R.id.email_txt);
        final EditText passwordTextBox = (EditText)findViewById(R.id.password_txt);
        final EditText bookingTextBox = (EditText)findViewById(R.id.booking_txt);

        final Register _this = this;
        registerBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                try
                {
                    String data = "{"
                            + "\"EmailAddress\":\"" + emailTextBox.getText() + "\","
                            + "\"Password\":\"" + passwordTextBox.getText() + "\","
                            + "\"BookingReference\":\"" + bookingTextBox.getText() + "\""
                            + "}";
                    LogCat.log(this, data);
                    WebRequest.send("http://geocar.is-a-techie.com/api/registeruser", data, "registeruser", _this);
                }
                catch (Exception ex)
                {
                    LogCat.error(this, ex);
                }
            }
        });
    }

    @Override
    public <T> void onPostExecute(IAsyncTask asyncTask, T result, String tag) {
        if(tag.equals("registeruser"))
        {
            RegisterUserResult _result = new RegisterUserResult((JsonDocument)result);

            if(_result.isSuccessful())
            {
                Intent i = new Intent(this, Home.class);
                i.putExtra("SessionId", _result.sessionId);
                startActivity(i);
                finish();
            }
            else
            {
                ToastHelper.show(this, "Registration unsuccessful");
            }
        }
    }

    @Override
    public void onClick(View v)
    {

    }
}
