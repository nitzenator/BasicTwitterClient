package com.codepath.apps.basictwitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.basictwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.util.Log;

public class MentionsTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	protected void populateTimeline(boolean initial, long max_id, int count) {
		client.getMentionsTimeline(new JsonHttpResponseHandler(){
			
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


}
