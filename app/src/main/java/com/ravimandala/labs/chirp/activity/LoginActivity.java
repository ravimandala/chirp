package com.ravimandala.labs.chirp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActivity;
import com.ravimandala.labs.chirp.R;
import com.ravimandala.labs.chirp.net.TwitterClient;
import com.ravimandala.labs.chirp.utils.Constants;

public class LoginActivity extends OAuthLoginActivity<TwitterClient> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onLoginSuccess() {
        Log.d(Constants.LOG_TAG, "Login Successful!!");
        Toast.makeText(LoginActivity.this, "Login success!!!", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, TimelineActivity.class);
    	startActivity(i);
    }

    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }

    public void loginToTwitter(View view) {
        Log.d(Constants.LOG_TAG, "Connecting...");
        getClient().connect();
    }
}
