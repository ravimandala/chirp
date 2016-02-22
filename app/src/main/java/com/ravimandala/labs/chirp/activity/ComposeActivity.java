package com.ravimandala.labs.chirp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ravimandala.labs.chirp.R;
import com.ravimandala.labs.chirp.app.TwitterClientApplication;
import com.ravimandala.labs.chirp.models.Tweet;
import com.ravimandala.labs.chirp.utils.Constants;

import org.json.JSONObject;

public class ComposeActivity extends Activity {

    EditText etTweetText;
    TextView tvCharsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etTweetText = (EditText) findViewById(R.id.etNewTweet);
        tvCharsLeft = (TextView) findViewById(R.id.tvCharsLeft);
        etTweetText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                int charsLeft = 140-s.length();
                tvCharsLeft.setText(String.valueOf(charsLeft));
                if (charsLeft < 0) {
                    tvCharsLeft.setTextColor(Color.RED);
                } else {
                    tvCharsLeft.setTextColor(Color.BLACK);
                }
            }
        });
    }

    public void onTweetClicked(View view) {
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
        }, etTweetText.getText().toString());
    }
}
