package com.ravimandala.labs.chirp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ravimandala.labs.chirp.R;
import com.ravimandala.labs.chirp.models.User;


public class UserHeaderFragment extends Fragment {

    public static UserHeaderFragment newInstance(User user) {
        UserHeaderFragment userHeaderFragment = new UserHeaderFragment();

        Bundle args = new Bundle();
        args.putParcelable("user", user);
        userHeaderFragment.setArguments(args);
        return userHeaderFragment;
    }

    public UserHeaderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_header, parent, false);

        ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivUserProfileImage);
        TextView tvProfileUsername = (TextView) v.findViewById(R.id.tvProfileUsername);
        TextView tvTagLine = (TextView) v.findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) v.findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) v.findViewById(R.id.tvFollowing);
        TextView tvTweets = (TextView) v.findViewById(R.id.tvTweets);

        User user = (User) getArguments().getParcelable("user");
        Glide.with(getContext())
                .load(user.getProfileImageUrl())
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .transform(new CircleTransform())
                .into(ivProfileImage);
        tvProfileUsername.setText(user.getUsername());
        tvTagLine.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText("Following " + user.getFollowingCount());
        tvTweets.setText(user.getTweetCount() + " Tweets");
        return v;
    }

}
