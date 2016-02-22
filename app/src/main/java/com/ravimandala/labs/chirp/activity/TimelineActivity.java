package com.ravimandala.labs.chirp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
import java.util.List;

public class TimelineActivity extends Activity {

    private TwitterClient client;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    TweetAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        rvTweets = (RecyclerView) findViewById(R.id.rvTweets);

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
        loadMoreTweets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    private void customLoadMoreDataFromApi(int page) {
        loadMoreTweets();

        int currSize = adapter.getItemCount();
        adapter.notifyItemRangeInserted(currSize, tweets.size() - 1);
    }


    private void loadMoreTweets() {
        long sinceId = 1;
        long maxId = -1;
        int tweetCount = tweets.size();
        if (tweets.size() > 0) {
            maxId = tweets.get(tweetCount-1).getUid() - 1;
        }

        populateTimeline(sinceId, maxId, tweetCount-1);
    }

    private void populateTimeline(int index) {
        long sinceId = 1;
        long maxId = -1;
        int tweetCount = tweets.size();

        if (index == -1 && tweetCount > 0)  {
            sinceId = tweets.get(0).getUid() - 1;
        } else if (index >= 0 && tweetCount > index) {
            if (tweetCount > index + 1) {
                maxId = tweets.get(index+1).getUid() - 1;
            }
            sinceId = tweets.get(index).getUid();
        } else {
            Log.e(Constants.LOG_TAG, "Invalid request to get tweets at index: " + index + "; current tweetCount: " + tweetCount);
            return;
        }
        populateTimeline(sinceId, maxId, index);
    }

    private void populateTimeline(long sinceId, long maxId, final int index) {
        client.getTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                int currSize = adapter.getItemCount();
                List<Tweet> fetchedTweets = Tweet.fromJsonArray(jsonArray);
                Log.d(Constants.LOG_TAG, "Fetched " + fetchedTweets.size() + " tweets");
                if (fetchedTweets.size() > 0) {
                    tweets.addAll(index + 1, fetchedTweets);
                    adapter.notifyItemRangeInserted(index + 1, tweets.size() - currSize);
                    Log.d(Constants.LOG_TAG, "Tweet count increased from " + currSize + " to " + tweets.size());
                }
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                throwable.printStackTrace();
            }
        }, sinceId, maxId, Constants.fetchCount);
    }

    public void onComposeClick(MenuItem item) {
        Intent intent = new Intent(this, ComposeActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Constants.LOG_TAG, "Back with requestCode: " + requestCode + "; resultCode: " + resultCode);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    tweets.add(0, (Tweet) data.getParcelableExtra("new_tweet"));
                    adapter.notifyItemRangeInserted(0, 1);
                    rvTweets.scrollToPosition(0);
//                    populateTimeline(0);
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
