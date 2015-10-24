package com.geocar.alex.geocarapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.geocar.alex.geocarapp.dto.LeaderboardResult;
import com.geocar.alex.geocarapp.dto.LogOutResult;
import com.geocar.alex.geocarapp.helpers.ToastHelper;
import com.geocar.alex.geocarapp.json.JsonDocument;
import com.geocar.alex.geocarapp.web.IAsyncTask;
import com.geocar.alex.geocarapp.web.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity implements IAsyncTask.OnPostExecuteListener
{

    private ListView mDrawerList = null;
    private ArrayAdapter<String> mAdapter = null;
    private ActionBarDrawerToggle mDrawerToggle = null;
    private DrawerLayout mDrawerLayout = null;
    private String mActivityTitle = "Home";
    private String mSessionId = "";
    private EstimoteManager mEstimoteManager = null;

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_home);

        mSessionId = getIntent().getExtras().getString("SessionId");

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = "Home";

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9400D3")));
        setActionBarName(mActivityTitle);

        mEstimoteManager = new EstimoteManager(mSessionId);
        mEstimoteManager.startRanging(getApplicationContext());
    }

    private void setActionBarName(String name)
    {
        if (name.toLowerCase().equals("home"))
        {
            name = "Welcome Someone";
        }

        getSupportActionBar().setTitle(name);
    }

    private void addDrawerItems()
    {
        String[] osArray = { "Home", "Leaderboard", "Transactions", "Achievements", "Sign Out" };
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        goHome();
                        break;
                    case 1:
                        goLeaderboard();
                        break;
                    case 2:
                        goTransactions();
                        break;
                    case 3:
                        goAchievements();
                        break;
                    case 4:
                        signOut();
                        break;
                    default:
                        goHome();
                        break;
                }

            }
        });
    }

    private void setVisible(int id)
    {
        RelativeLayout homeLayout = (RelativeLayout)findViewById(R.id.content_home);
        RelativeLayout lbLayout = (RelativeLayout)findViewById(R.id.content_lb);
        RelativeLayout achLayout = (RelativeLayout)findViewById(R.id.content_ach);
        RelativeLayout transLayout = (RelativeLayout)findViewById(R.id.content_trans);

        homeLayout.setVisibility(View.VISIBLE);
        lbLayout.setVisibility(View.VISIBLE);
        achLayout.setVisibility(View.VISIBLE);
        transLayout.setVisibility(View.VISIBLE);

        RelativeLayout visibleLayout = (RelativeLayout)findViewById(id);

        visibleLayout.setVisibility(View.VISIBLE);
    }

    private void goHome()
    {
       setVisible(R.id.content_home);

        ListView recentTrans = (ListView)findViewById(R.id.recentTransactions);

        List<Map<String, String>> data = new ArrayList<>();
        Map<String,String> item = new HashMap<>(2);

        item.put("description", "Test Description 1");
        item.put("date", "10/24/2015");

        //TODO Fill 'data' with values from UserInfo call.
        data.add(item);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                data,
                android.R.layout.simple_list_item_2,
                new String[]{"description" , "date"},
                new int[]{android.R.id.text1,android.R.id.text2}
        );

        recentTrans.setAdapter(simpleAdapter);
    }

    private void goLeaderboard()
    {
        setVisible(R.id.content_lb);

        try
        {
            String data = "{\"SessionId\":\"" + mSessionId + "\"}";
            WebRequest.send("http://geocar.is-a-techie.com/api/getleaderboard", data, "getleaderboard" , this);
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }
    }

    private void goTransactions()
    {
        setVisible(R.id.content_trans);
        //TODO Nav draw transaction click
    }

    private void goAchievements()
    {
        setVisible(R.id.content_ach);
        //TODO Nav draw achievements click
    }

    private void signOut()
    {
        try
        {
            String data = "{\"SessionId\":\"" + mSessionId + "\"}";
            WebRequest.send("http://geocar.is-a-techie.com/api/logout", data, "logout" , this);
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }
    }

    private void setupDrawer()
    {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close)
        {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setActionBarName("Navigation");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                setActionBarName(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mEstimoteManager.startRanging(getApplicationContext());
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mEstimoteManager.stopRanging(getApplicationContext());
    }

    @Override
    public <T> void onPostExecute(IAsyncTask asyncTask, T result, String tag)
    {
        try
        {
            if(tag.equals("logout"))
            {
                LogOutResult _result = new LogOutResult((JsonDocument)result);

                if (!_result.isSuccessful())
                {
                    ToastHelper.show(this, "Logout unsuccessful");
                }
                else
                {
                    Intent i = new Intent(this, LogIn.class);
                    mSessionId = "";
                    startActivity(i);
                    finish();
                }
            }
            else if(tag.equals("getleaderboard"))
            {
                LeaderboardResult _result = new LeaderboardResult((JsonDocument)result);
                if(!_result.isSuccessful())
                {

                }
                else
                {

                }
            }
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }
    }
}
