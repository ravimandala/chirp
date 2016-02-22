package com.ravimandala.labs.chirp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ravimandala.labs.chirp.R;
import com.ravimandala.labs.chirp.models.Tweet;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    List<Tweet> tweets;
    Context context;

    public TweetAdapter(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tweetView = inflater.inflate(R.layout.content_timeline, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Tweet tweet = tweets.get(position);

        // Set item views based on the data model
        holder.tvUsername.setText(tweet.getUser().getUsername());
        holder.tvHandle.setText(tweet.getUser().getHandle());
        holder.tvRelativeTime.setText(tweet.getRelativeTime());
        holder.tvTweetText.setText(tweet.getBody());
        Glide.with(context)
                .load(tweet.getUser().getProfileImageUrl())
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .transform(new CircleTransform())
                .into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return tweets == null ? 0 : tweets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

//        @Bind(R.id.ivProfileImage)
        public ImageView ivProfileImage;

//        @Bind(R.id.tvUsername)
        public TextView tvUsername;

//        @Bind(R.id.tvHandle)
        public TextView tvHandle;

//        @Bind(R.id.tvRelativeTime)
        public TextView tvRelativeTime;

        public TextView tvTweetText;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvHandle = (TextView) itemView.findViewById(R.id.tvHandle);
            tvRelativeTime = (TextView) itemView.findViewById(R.id.tvRelativeTime);
            tvTweetText = (TextView) itemView.findViewById(R.id.tvTweetText);

//            ButterKnife.bind(itemView);
        }
    }
}
