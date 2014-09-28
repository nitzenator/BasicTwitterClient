package com.codepath.apps.basictwitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.codepath.apps.basictwitterclient.models.Tweet;
import com.codepath.apps.basictwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	
	protected void populateTimeline(boolean initial, long max_id, int count) {
		client.getHomeTimeline(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray json) {
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
		}, max_id, count, initial);
		
	}

	public void addTweet(Tweet composedTweet) {
		aTweets.insert(composedTweet, 0);
		
	}

}
