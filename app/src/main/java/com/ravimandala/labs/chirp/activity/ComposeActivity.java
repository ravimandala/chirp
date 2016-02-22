package com.ravimandala.labs.chirp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ravimandala.labs.chirp.R;
import com.ravimandala.labs.chirp.app.TwitterClientApplication;
import com.ravimandala.labs.chirp.models.Tweet;
import com.ravimandala.labs.chirp.net.TwitterClient;
import com.ravimandala.labs.chirp.utils.Constants;

import org.json.JSONObject;

public class ComposeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
    }

    public void onTweetClicked(View view) {
        TextView tvTweetText = (TextView) findViewById(R.id.etNewTweet);

        TwitterClientApplication.getRestClient().postUpdate(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, JSONObject jsonObject) {
                Log.d(Constants.LOG_TAG, "Successfully posted Tweet!!!");
                Intent intent = new Intent();
                intent.putExtra("new_tweet", Tweet.fromJson(jsonObject));

                setResult(RESULT_OK, intent);
                Toast.makeText(ComposeActivity.this, "Successfully posted the tweet!!!", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Log.d(Constants.LOG_TAG, "Failed with " + s);
                throwable.printStackTrace();
            }
        }, tvTweetText.getText().toString());
    }
}
