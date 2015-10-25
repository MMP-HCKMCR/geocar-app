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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.geocar.alex.geocarapp.dto.LeaderboardEntryResult;
import com.geocar.alex.geocarapp.dto.LeaderboardResult;
import com.geocar.alex.geocarapp.dto.LogOutResult;
import com.geocar.alex.geocarapp.dto.TransactionEntryResult;
import com.geocar.alex.geocarapp.dto.UserInfoResult;
import com.geocar.alex.geocarapp.dto.UsersTransactionsResult;
import com.geocar.alex.geocarapp.helpers.ToastHelper;
import com.geocar.alex.geocarapp.json.JsonDocument;
import com.geocar.alex.geocarapp.web.IAsyncTask;
import com.geocar.alex.geocarapp.web.WebRequest;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
    private UserInfoResult mUserInfo = null;


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

        goHome();

        ImageButton refreshUserInfo = (ImageButton)findViewById(R.id.refreshUserInfo);

        refreshUserInfo.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    private void setActionBarName(String name)
    {
        if (name.toLowerCase().equals("home"))
        {
            name = "Welcome " + (mUserInfo == null ? "" : mUserInfo.firstName);
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

                mDrawerLayout.closeDrawers();
            }
        });
    }

    private void setVisible(int id)
    {
        RelativeLayout homeLayout = (RelativeLayout)findViewById(R.id.content_home);
        RelativeLayout lbLayout = (RelativeLayout)findViewById(R.id.content_lb);
        RelativeLayout achLayout = (RelativeLayout)findViewById(R.id.content_ach);
        RelativeLayout transLayout = (RelativeLayout)findViewById(R.id.content_trans);

        homeLayout.setVisibility(View.GONE);
        lbLayout.setVisibility(View.GONE);
        achLayout.setVisibility(View.GONE);
        transLayout.setVisibility(View.GONE);

        RelativeLayout visibleLayout = (RelativeLayout)findViewById(id);

        visibleLayout.setVisibility(View.VISIBLE);
    }

    private void goHome()
    {
        mActivityTitle = "Home";

        try
        {
            String data = "{\"SessionId\":\"" + mSessionId + "\"}";
            WebRequest.send("http://geocar.is-a-techie.com/api/userinfo", data, "userinfo" , this);
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }
    }

    private void completeHome()
    {
        if(mUserInfo == null)
        {
            return;
        }

        LogCat.log(this, mUserInfo.totalPoints);

        mActivityTitle = "Home";
        setActionBarName(mActivityTitle);
        setVisible(R.id.content_home);

        ListView recentTrans = (ListView)findViewById(R.id.recentTransactions);
        TextView currentPoints = (TextView)findViewById(R.id.currentPoints);
        TextView useablePoints = (TextView)findViewById(R.id.useablePoints);

        currentPoints.setText(""+mUserInfo.totalPoints);
        DecimalFormat df = new DecimalFormat("#.00");
        double useableCurrency = ((double)mUserInfo.usablePoints * 0.005d);
        useablePoints.setText(""+mUserInfo.usablePoints + " (£"+df.format(useableCurrency)+")");

        List<Map<String, String>> data = new ArrayList<>();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        for (int i = 0; i < mUserInfo.last5Transactions.size(); i++)
        {
            TransactionEntryResult currentTrans = mUserInfo.last5Transactions.get(i);
            Map<String,String> item = new HashMap<>(2);
            item.put("amount", ""+ currentTrans.points+" points ("+currentTrans.transactionType+")");
            item.put("date", simpleFormat.format(currentTrans.timeCaptured.getTime()));

            data.add(item);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                data,
                android.R.layout.simple_list_item_2,
                new String[] { "amount" , "date" },
                new int[] { android.R.id.text1, android.R.id.text2 });

        recentTrans.setAdapter(simpleAdapter);
    }

    private void goLeaderboard()
    {
        mActivityTitle = "Leaderboard";

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
    private void completeLeaderboard(LeaderboardResult result)
    {
        TextView currentRank = (TextView)findViewById(R.id.currentRank);
        currentRank.setText(Integer.toString(result.currentRanking));
        ListView topTenList = (ListView)findViewById(R.id.topTen);
        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 0; i<result.topTen.size();i++)
        {
            LeaderboardEntryResult currentEntry =  result.topTen.get(i);
            Map<String,String> row = new HashMap<>(2);
            row.put("description", currentEntry.position + ": "+currentEntry.firstName+" "+currentEntry.surName);
            row.put("score", Integer.toString(currentEntry.score));
            data.add(row);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                data,
                android.R.layout.simple_list_item_2,
                new String[]{"description" , "score"},
                new int[]{android.R.id.text1,android.R.id.text2}
        );

        topTenList.setAdapter(simpleAdapter);
        setVisible(R.id.content_lb);
    }

    private void goTransactions()
    {
        mActivityTitle = "Transactions";

        try
        {
            String data = "{\"SessionId\":\"" + mSessionId + "\"}";
            WebRequest.send("http://geocar.is-a-techie.com/api/getusertransactions", data, "getusertransactions" , this);
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }
    }

    private void completeTransactions(UsersTransactionsResult result)
    {
        ListView transactionList = (ListView)findViewById(R.id.transactionList);
        List<Map<String, String>> data = new ArrayList<>();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        LogCat.log(this, "SIZE: "+result.transactions);

        for (int i = 0; i<result.transactions.size(); i++)
        {
            TransactionEntryResult currentTrans =  result.transactions.get(i);
            Map<String,String> item = new HashMap<>(2);
            item.put("amount", ""+ currentTrans.points+" points ("+currentTrans.transactionType+")");
            item.put("date", simpleFormat.format(currentTrans.timeCaptured.getTime()));

            data.add(item);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                data,
                android.R.layout.simple_list_item_2,
                new String[]{"amount" , "date"},
                new int[]{android.R.id.text1,android.R.id.text2}
        );

        transactionList.setAdapter(simpleAdapter);

        setVisible(R.id.content_trans);
    }

    private void goAchievements()
    {
        mActivityTitle = "Achievements";
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
            if (tag.equals("logout"))
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
                LeaderboardResult _result = new LeaderboardResult((JsonDocument) result);

                if (!_result.isSuccessful())
                {
                    ToastHelper.show(this, "Leaderboard retrieval unsuccessful");
                }
                else
                {
                    completeLeaderboard(_result);
                }
            }
            else if (tag.equals("achievements"))
            {

            }
            else if (tag.equals("getusertransactions"))
            {
                UsersTransactionsResult _result = new UsersTransactionsResult((JsonDocument) result);
                if(_result.isSuccessful())
                {
                    completeTransactions(_result);
                }
            }
            else if (tag.equals("userinfo"))
            {
                mUserInfo = new UserInfoResult((JsonDocument)result);
                if (mUserInfo.isSuccessful())
                {
                    completeHome();
                }
            }
        }
        catch (Exception ex)
        {
            LogCat.error(this, ex);
        }
    }
}
