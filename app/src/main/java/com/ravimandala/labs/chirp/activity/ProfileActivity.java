package com.ravimandala.labs.chirp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ravimandala.labs.chirp.R;
import com.ravimandala.labs.chirp.app.TwitterClientApplication;
import com.ravimandala.labs.chirp.fragment.UserTimelineFragment;
import com.ravimandala.labs.chirp.models.User;
import com.ravimandala.labs.chirp.net.TwitterClient;
import com.ravimandala.labs.chirp.utils.Constants;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        client = TwitterClientApplication.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, JSONObject response) {
                user = User.fromJson(response);
                getSupportActionBar().setTitle("@" + user.getUsername());
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject errorResponse) {
                Log.e(Constants.LOG_TAG, errorResponse.toString());
                throwable.printStackTrace();
            }
        });

        String screenName = getIntent().getStringExtra(Constants.paramScreenName);

        if (savedInstanceState == null) {
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, userTimelineFragment);
            ft.commit();
        }
    }

}
