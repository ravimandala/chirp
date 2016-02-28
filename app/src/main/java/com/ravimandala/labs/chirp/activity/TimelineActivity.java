package com.ravimandala.labs.chirp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.ravimandala.labs.chirp.R;
import com.ravimandala.labs.chirp.adapter.TwitterFragmentPagerAdapter;
import com.ravimandala.labs.chirp.models.Tweet;
import com.ravimandala.labs.chirp.utils.Constants;

public class TimelineActivity extends AppCompatActivity {
    TwitterFragmentPagerAdapter twitterFragmentPagerAdapter;
    ViewPager vpViewPager;
    PagerSlidingTabStrip tsTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        twitterFragmentPagerAdapter = new TwitterFragmentPagerAdapter(getSupportFragmentManager());
        vpViewPager = (ViewPager) findViewById(R.id.viewpager);
        vpViewPager.setAdapter(twitterFragmentPagerAdapter);

        tsTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tsTabStrip.setViewPager(vpViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public void onComposeClick(MenuItem item) {
        openComposeActivity();
    }

    public void onProfileClick(MenuItem item) {
//        Intent intent = new Intent(this, ProfileActivity.class);
//        startActivity(intent);
    }

    public void onComposeClick(View view) {
        openComposeActivity();
    }

    private void openComposeActivity() {
        Intent intent = new Intent(this, ComposeActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Constants.LOG_TAG, "Back with requestCode: " + requestCode + "; resultCode: " + resultCode);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    twitterFragmentPagerAdapter.getHomeTimelineFragment()
                            .add(0, (Tweet) data.getParcelableExtra("new_tweet"));
                    vpViewPager.setCurrentItem(0);
                }
                return;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
