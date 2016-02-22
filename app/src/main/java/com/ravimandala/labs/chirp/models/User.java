package com.ravimandala.labs.chirp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    public static User fromJson(JSONObject json) {
        User user = new User();
        try {
            user.uid = json.getLong("id");
            user.username = json.getString("name");
            user.handle = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public long getUid() { return uid; }

    public String getHandle() {
        return " @" + handle;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getUsername() {
        return username;
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
        dest.writeString(this.profileImageUrl);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.uid = in.readLong();
        this.username = in.readString();
        this.handle = in.readString();
        this.profileImageUrl = in.readString();
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
