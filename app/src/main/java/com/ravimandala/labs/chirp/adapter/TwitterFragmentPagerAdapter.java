package com.ravimandala.labs.chirp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ravimandala.labs.chirp.fragment.HomeTimelineFragment;
import com.ravimandala.labs.chirp.fragment.MentionsTimelineFragment;
import com.ravimandala.labs.chirp.fragment.UserTimelineFragment;

public class TwitterFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] tabTitles = {"Timeline", "Mentions", "My Tweets"};
    HomeTimelineFragment homeTimelineFragment;
    MentionsTimelineFragment mentionsTimelineFragment;
    UserTimelineFragment userTimelineFragment;

    public HomeTimelineFragment getHomeTimelineFragment() {
        return homeTimelineFragment;
    }

    public MentionsTimelineFragment getMentionsTimelineFragment() {
        return mentionsTimelineFragment;
    }

    public UserTimelineFragment getUserTimelineFragment() {
        return userTimelineFragment;
    }

    public TwitterFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                homeTimelineFragment = new HomeTimelineFragment();
                return homeTimelineFragment;
            case 1:
                mentionsTimelineFragment = new MentionsTimelineFragment();
                return mentionsTimelineFragment;
            case 2:
                userTimelineFragment = new UserTimelineFragment();
                return userTimelineFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (position >= 0 && position < tabTitles.length) ? tabTitles[position] : "Unknown";
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
