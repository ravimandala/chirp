package com.ravimandala.labs.chirp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.ravimandala.labs.chirp.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "user")
public class User extends Model implements Parcelable {
    @Column(name = "uid")
    private long uid;
    @Column(name = "username")
    private String username;
    @Column(name = "handle")
    private String handle;
    @Column(name = "tagline")
    private String tagline;
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    @Column(name = "followers_count")
    private int followersCount;
    @Column(name = "following_count")
    private int followingCount;
    @Column(name = "tweet_count")
    private int tweetCount;

    public static User fromJson(JSONObject json) {
        User user = new User();
        try {
            user.uid = json.getLong(Constants.paramId);
            user.username = json.getString(Constants.paramName);
            user.handle = json.getString(Constants.keyScreenName);
            user.tagline = json.getString(Constants.keyTagline);
            user.profileImageUrl = json.getString(Constants.keyProfileUrl);
            user.followersCount = (json.optString(Constants.keyFollowers) == null) ? 0 : Integer.parseInt(json.optString(Constants.keyFollowers));
            user.followingCount = (json.optString(Constants.keyFollowing) == null) ? 0 : Integer.parseInt(json.optString(Constants.keyFollowing));
            user.tweetCount = (json.optString(Constants.keyTweets) == null) ? 0 : Integer.parseInt(json.optString(Constants.keyTweets));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return user;
    }

    public String getHandle() {
        return " @" + handle;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public int getTweetCount() {
        return tweetCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.uid);
        dest.writeString(this.username);
        dest.writeString(this.handle);
        dest.writeString(this.tagline);
        dest.writeString(this.profileImageUrl);
        dest.writeInt(this.followersCount);
        dest.writeInt(this.followingCount);
        dest.writeInt(this.tweetCount);
    }

    public User() {
    }

    public String getTagline() {
        return tagline;
    }

    protected User(Parcel in) {
        this.uid = in.readLong();
        this.username = in.readString();
        this.handle = in.readString();
        this.tagline = in.readString();

        this.profileImageUrl = in.readString();
        this.followersCount = in.readInt();
        this.followingCount = in.readInt();
        this.tweetCount = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
