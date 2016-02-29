package com.ravimandala.labs.chirp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ravimandala.labs.chirp.R;
import com.ravimandala.labs.chirp.app.TwitterClientApplication;
import com.ravimandala.labs.chirp.fragment.UserTimelineFragment;
import com.ravimandala.labs.chirp.models.User;
import com.ravimandala.labs.chirp.net.TwitterClient;
import com.ravimandala.labs.chirp.utils.Constants;

import org.json.JSONObject;

import com.ravimandala.labs.chirp.fragment.UserHeaderFragment;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra(Constants.keyScreenName);
        Log.d(Constants.LOG_TAG, "Screen Name for Profile activity: " + screenName);
        client = TwitterClientApplication.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, JSONObject response) {
                user = User.fromJson(response);
                getSupportActionBar().setTitle(user.getHandle());

                UserHeaderFragment userHeaderFragment = UserHeaderFragment.newInstance(user);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flUserHeader, userHeaderFragment);
                ft.commit();
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject errorResponse) {
                Log.e(Constants.LOG_TAG, "Failed to get userInfo: " + errorResponse.toString());
                throwable.printStackTrace();
            }
        }, screenName);

        if (savedInstanceState == null) {
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flUserTimeline, userTimelineFragment);
            ft.commit();
        }
    }

    public void onFrameClicked(View view) {
        Toast.makeText(ProfileActivity.this, "Fragment clicked", Toast.LENGTH_SHORT).show();
    }
}
