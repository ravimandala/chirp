package com.ravimandala.labs.chirp.net;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ravimandala.labs.chirp.R;
import com.codepath.oauth.OAuthBaseClient;
import com.ravimandala.labs.chirp.utils.Constants;

public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.SSL.class;
    public static final String REST_CALLBACK_URL = "oauth://chirp";

    public TwitterClient(Context context) {
        super(context,
                REST_API_CLASS,
                context.getResources().getString(R.string.base_url),
                context.getResources().getString(R.string.api_key),
                context.getResources().getString(R.string.api_secret),
                REST_CALLBACK_URL);
        Log.d(Constants.LOG_TAG, "Created a Twitter Client");
    }

    public void getTimeline(AsyncHttpResponseHandler handler, long sinceId, long maxId, int count) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        Log.d(Constants.LOG_TAG, "Sending API call to " + apiUrl);
        RequestParams params = new RequestParams();
        params.put("count", String.valueOf(count));
        if (maxId != -1) {
            params.put("max_id", String.valueOf(maxId));
        }
        if (sinceId != -1) {
            params.put("since_id", String.valueOf(sinceId));
        }
        client.get(apiUrl, null, handler);
    }
}
