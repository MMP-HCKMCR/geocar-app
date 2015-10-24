package com.geocar.alex.geocarapp;

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
import android.widget.Toast;

import com.geocar.alex.geocarapp.web.IAsyncTask;
import com.geocar.alex.geocarapp.web.WebRequest;

public class Home extends AppCompatActivity implements IAsyncTask.OnPostExecuteListener
{

    private ListView mDrawerList = null;
    private ArrayAdapter<String> mAdapter = null;
    private ActionBarDrawerToggle mDrawerToggle = null;
    private DrawerLayout mDrawerLayout = null;
    private String mActivityTitle = "Home";
    private String mSessionId = "";

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

    private void goHome()
    {

    }

    private void goLeaderboard()
    {

    }

    private void goTransactions()
    {

    }

    private void goAchievements()
    {

    }

    private void signOut()
    {
        try
        {
            String data = "{\"SessionId\":\"" + mSessionId + "\"}";
            WebRequest.send("http://geocar.is-a-techie.com/api/logout", data, this);
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
    public <T> void onPostExecute(IAsyncTask asyncTask, T result) {

    }
}
