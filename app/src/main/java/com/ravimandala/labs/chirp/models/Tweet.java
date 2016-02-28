package com.ravimandala.labs.chirp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Tweet")
public class Tweet extends Model implements Parcelable {
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

    public static Tweet fromJson(JSONObject json) {
        Tweet tweet = new Tweet();
        try {
            tweet.uid = json.getLong("id");
            tweet.body = json.getString("text");
            tweet.createdAt = json.getString("created_at");
            JSONObject jsonUser = json.optJSONObject("user");
            if (jsonUser != null) {
                tweet.user = User.fromJson(jsonUser);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    @Override
    public String toString() {
        return "UID: " + uid + "; Username: " + user.getUsername() + "; Body: " + body;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i=0; i<jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.optJSONObject(i)));
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.uid);
        dest.writeString(this.body);
        dest.writeString(this.createdAt);
        dest.writeParcelable(this.user, flags);
    }

    protected Tweet(Parcel in) {
        this.uid = in.readLong();
        this.body = in.readString();
        this.createdAt = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }

        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}
