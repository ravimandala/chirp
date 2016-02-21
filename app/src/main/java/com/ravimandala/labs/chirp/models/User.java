package com.ravimandala.labs.chirp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "user")
public class User extends Model {
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
        return handle;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getUsername() {
        return username;
    }
}
