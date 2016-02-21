package com.ravimandala.labs.chirp.net;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.ravimandala.labs.chirp.R;
import com.codepath.oauth.OAuthBaseClient;

public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.SSL.class;
    //    public static final String REST_URL = "http://www.Twitter.com/services";
    public static final String REST_CALLBACK_URL = "oauth://chirp";

    public TwitterClient(Context context) {
        super(context,
                REST_API_CLASS,
                context.getResources().getString(R.string.base_url),
                context.getResources().getString(R.string.api_key),
                context.getResources().getString(R.string.api_secret),
                REST_CALLBACK_URL);
        Log.d("RaviTwitter", "Created a Twitter Client");
//        setBaseUrl("https://api.Twitter.com/services/rest");
    }

//    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
//        String apiUrl = getApiUrl("?&format=json&nojsoncallback=1&api_key=YOUR API KEY HERE&method=Twitter.interestingness.getList");
//        Log.d("DEBUG", "Sending API call to " + apiUrl);
//        client.get(apiUrl, null, handler);
//    }
}
