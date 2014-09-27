package com.codepath.apps.basictwitterclient.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.codepath.apps.basictwitterclient.EndlessScrollListener;
import com.codepath.apps.basictwitterclient.R;
import com.codepath.apps.basictwitterclient.TweetArrayAdapter;
import com.codepath.apps.basictwitterclient.TwitterClient;
import com.codepath.apps.basictwitterclient.models.Tweet;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public abstract class TweetsListFragment extends Fragment {
	protected TwitterClient client;
	private ArrayList<Tweet> tweets;
	protected ArrayAdapter<Tweet> aTweets;
	protected PullToRefreshListView lvTweets;
	
	
	protected long curr_max_id;
	
	public static final int TWEET_COUNT = 12;
	public static final int REQUEST_CODE = 1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (PullToRefreshListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call listView.onRefreshComplete() when
                // once the network request has completed successfully.
            	aTweets.clear();
            	populateTimeline(true, -1, TWEET_COUNT);
            }
        });
		
		
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
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		client = new TwitterClient(getActivity());
		
	}
	
	protected void customLoadMoreDataFromApi() {
		populateTimeline(false, this.curr_max_id, TWEET_COUNT);
	}

	protected abstract void populateTimeline(boolean b, long curr_max_id2, int tweetCount) ;

	

}
