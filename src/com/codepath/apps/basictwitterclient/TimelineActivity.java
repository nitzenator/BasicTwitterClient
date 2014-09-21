package com.codepath.apps.basictwitterclient;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.basictwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	protected long curr_max_id;
	
	public static final int TWEET_COUNT = 12;
	private static final int REQUEST_CODE = 1;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
				customLoadMoreDataFromApi(); 
                // or customLoadMoreDataFromApi(totalItemsCount); 
				
			}
		});
		populateTimeline(true, -1, TWEET_COUNT);
	}

	protected void customLoadMoreDataFromApi() {
		populateTimeline(false, this.curr_max_id, TWEET_COUNT);
	}

	private void populateTimeline(boolean initial, long max_id, int count) {
		client.getHomeTimeline(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
				//Get the id and set the current_max_id
				Tweet lastTweet = tweets.get(tweets.size() - 1);
				TimelineActivity.this.curr_max_id = lastTweet.getUid();
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s);
			}
		}, max_id, count, initial);
		
	}
	
	
	public void onCompose(MenuItem mi){
		Intent composeIntent = new Intent(this, ComposeTweetActivity.class);
		//composeIntent.putExtra("settings", settings);
		startActivityForResult(composeIntent, REQUEST_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  // REQUEST_CODE is defined above
		  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
		     Tweet composedTweet = (Tweet) data.getSerializableExtra("posted_tweet");
		     aTweets.insert(composedTweet, 0);
		     Toast.makeText(this, R.string.tweet_success, Toast.LENGTH_SHORT).show();
		  }
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return super.onCreateOptionsMenu(menu);
	}

}
