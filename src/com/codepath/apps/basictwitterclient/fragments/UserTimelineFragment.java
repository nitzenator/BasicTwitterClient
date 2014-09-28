package com.codepath.apps.basictwitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.apps.basictwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	private long userId;

	public static UserTimelineFragment getInstance(long userId){
		UserTimelineFragment fragment = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putLong("user_id", userId);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUserId(getArguments().getLong("user_id"));
	}
	
	@Override
	protected void populateTimeline(boolean initial, long max_id, int count) {
		client.getUserTimeline(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray json) {
				Log.d("debug", json.toString());
				ArrayList<Tweet> allTweets = Tweet.fromJSONArray(json);
				if(allTweets.size() > 0){
					aTweets.addAll(allTweets);
					//Get the id and set the current_max_id
					Tweet lastTweet = allTweets.get(allTweets.size() - 1);
					curr_max_id = lastTweet.getUid();
				}
				lvTweets.onRefreshComplete();
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s);
			}
		}, getUserId(), max_id, count, initial);
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

		
  }
