package com.ravimandala.labs.chirp.fragment;


import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ravimandala.labs.chirp.models.Tweet;
import com.ravimandala.labs.chirp.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MentionsTimelineFragment extends TweetsListFragment {
    protected void populateTimeline(long sinceId, long maxId, final int index) {
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                int currSize = adapter.getItemCount();
                List<Tweet> fetchedTweets = Tweet.fromJsonArray(jsonArray);
                Log.d(Constants.LOG_TAG, "Fetched " + fetchedTweets.size() + " tweets");
                if (fetchedTweets.size() > 0) {
                    tweets.addAll(index, fetchedTweets);
                    adapter.notifyItemRangeInserted(index, tweets.size() - currSize);
                    Log.d(Constants.LOG_TAG, "Tweet count increased from " + currSize + " to " + tweets.size());
                    swipeContainer.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                throwable.printStackTrace();
            }
        }, sinceId, maxId, Constants.fetchCount);
    }
}
