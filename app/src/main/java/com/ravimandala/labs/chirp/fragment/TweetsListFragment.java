package com.ravimandala.labs.chirp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ravimandala.labs.chirp.R;
import com.ravimandala.labs.chirp.adapter.TweetAdapter;
import com.ravimandala.labs.chirp.app.TwitterClientApplication;
import com.ravimandala.labs.chirp.listener.EndlessRecyclerViewScrollListener;
import com.ravimandala.labs.chirp.models.Tweet;
import com.ravimandala.labs.chirp.net.TwitterClient;
import com.ravimandala.labs.chirp.utils.Constants;

import java.util.ArrayList;

public abstract class TweetsListFragment extends Fragment {
    TwitterClient client;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    TweetAdapter adapter;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout swipeContainer;

    public TweetsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);

        layoutManager = new LinearLayoutManager(getActivity());
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweets);
        rvTweets.setAdapter(adapter);
        rvTweets.setLayoutManager(layoutManager);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d(Constants.LOG_TAG, "onLoadMore invoked with page = " + page + "; totalItemsCount = " + totalItemsCount);
                populateTimeline(tweets.size());
            }
        });
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline(0);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tweets = new ArrayList<>();
        adapter = new TweetAdapter(tweets);
        client = TwitterClientApplication.getRestClient();
        populateTimeline(0);
    }

    protected void populateTimeline(int index) {
        long sinceId = 1;
        long maxId = -1;
        int tweetCount = tweets.size();

        if (index == 0)  {
            if (tweetCount > 0) {
                sinceId = tweets.get(0).getUid();
            }
        } else if (index > 0 && tweetCount > index) {
            if (tweetCount > index) {
                maxId = tweets.get(index).getUid() - 1;
            }
            sinceId = tweets.get(index).getUid();
        }
        populateTimeline(sinceId, maxId, index);
    }

    protected abstract void populateTimeline(long sinceId, long maxId, final int index);
}
