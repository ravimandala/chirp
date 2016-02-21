package com.ravimandala.labs.chirp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ravimandala.labs.chirp.R;
import com.ravimandala.labs.chirp.adapter.TweetAdapter;
import com.ravimandala.labs.chirp.app.TwitterClientApplication;
import com.ravimandala.labs.chirp.listener.EndlessRecyclerViewScrollListener;
import com.ravimandala.labs.chirp.models.Tweet;
import com.ravimandala.labs.chirp.net.TwitterClient;
import com.ravimandala.labs.chirp.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends Activity {

    private TwitterClient client;
    ArrayList<Tweet> tweets;
    TweetAdapter adapter;
    LinearLayoutManager layoutManager;
    private int fetchCount = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        RecyclerView rvTweets = (RecyclerView) findViewById(R.id.rvTweets);

        tweets = new ArrayList<>();
        adapter = new TweetAdapter(tweets);
        rvTweets.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(layoutManager);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d(Constants.LOG_TAG, "onLoadMore invoked with page = " + page + "; totalItemsCount = " + totalItemsCount);
                customLoadMoreDataFromApi(page);
            }
        });
        client = TwitterClientApplication.getRestClient();
        populateTimeline();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    private void customLoadMoreDataFromApi(int page) {
        populateTimeline();

        int currSize = adapter.getItemCount();
        adapter.notifyItemRangeInserted(currSize, tweets.size()-1);
    }

    private void populateTimeline() {
        long sinceId = 1;
        long maxId = -1;
        int tweetCount = tweets.size();
        if (tweets.size() > 0) {
            maxId = tweets.get(tweetCount-1).getUid() - 1;
        }

        client.getTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                int currSize = adapter.getItemCount();
                tweets.addAll(Tweet.fromJsonArray(jsonArray));
                adapter.notifyItemRangeInserted(currSize, tweets.size() - 1);
                Log.d(Constants.LOG_TAG, "Tweet count increased from " + currSize + " to " + tweets.size());
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                throwable.printStackTrace();
            }
        }, sinceId, maxId, fetchCount);
    }

    public void onComposeClick(MenuItem item) {
        Toast.makeText(TimelineActivity.this, "Do something to compose a new Tweet now", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ComposeActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Constants.LOG_TAG, "Back with requestCode: " + requestCode + "; resultCode: " + resultCode);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(TimelineActivity.this, "Back in Timeline", Toast.LENGTH_LONG).show();
                }
                return;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miCompose:
                onComposeClick(item);
                return true;
            default:
                return super.onMenuItemSelected(featureId, item);
        }
    }
}
