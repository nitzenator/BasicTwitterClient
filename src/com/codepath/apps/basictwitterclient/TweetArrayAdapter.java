package com.codepath.apps.basictwitterclient;

import java.util.List;

import com.codepath.apps.basictwitterclient.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	public TweetArrayAdapter(Context context, List<Tweet> objects) {
		super(context, 0, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		View v;
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.tweet_item, parent, false);
		}
		else{
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvTweetBody = (TextView) v.findViewById(R.id.tvTweetBody);
		TextView created_at = (TextView) v.findViewById(R.id.created_at);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getScreenName());
		tvTweetBody.setText(tweet.getBody());
		created_at.setText(tweet.getRelativeTimeAgo());
		
		return v;
	}

}
