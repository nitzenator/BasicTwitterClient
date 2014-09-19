package com.codepath.apps.basictwitterclient;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.basictwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
		lvTweets.setAdapter(aTweets);
		populateTimeline();
	}

	private void populateTimeline() {
		client.getHomeTimeline(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
