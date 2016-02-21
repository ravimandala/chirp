package com.ravimandala.labs.chirp.models;

import android.text.format.DateUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Table(name = "Tweet")
public class Tweet extends Model {
    @Column(name = "uid")
    private long uid;
    @Column(name = "body")
    private String body;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "user")
    private User user;

    public Tweet() {
        super();
    }

    public Tweet(JSONObject json) throws JSONException {
        super();

        this.uid = json.getLong("id");
        this.body = json.getString("text");
        this.createdAt = json.getString("created_at");
        JSONObject jsonUser = json.optJSONObject("user");
        if (jsonUser != null) {
             this.user = User.fromJson(jsonUser);
        }
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i=0; i<jsonArray.length(); i++) {
            try {
                tweets.add(new Tweet(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return tweets;
    }

    public static Tweet byTweetUid(long uid) {
        return new Select().from(Tweet.class).where("uid = ?", uid).executeSingle();
    }

    public static ArrayList<Tweet> recentItems() {
        return new Select().from(Tweet.class).orderBy("id DESC").limit("300").execute();
    }

    public static ArrayList<Tweet> fetchTweets(int i) {
        return null;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getUid() {
        return uid;
    }

    public String getRelativeTime() {
        String relativeTime = "x ago";
        DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        try {
            relativeTime = DateUtils.getRelativeTimeSpanString(dateFormat.parse(createdAt).getTime()).toString();
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return relativeTime;
    }

    public User getUser() {
        return user;
    }
}