package com.codepath.apps.basictwitterclient.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.codepath.apps.basictwitterclient.EndlessScrollListener;
import com.codepath.apps.basictwitterclient.R;
import com.codepath.apps.basictwitterclient.TweetArrayAdapter;
import com.codepath.apps.basictwitterclient.TwitterClient;
import com.codepath.apps.basictwitterclient.models.Tweet;
import com.codepath.apps.basictwitterclient.models.User;

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
	
	// ...
		  // Define the listener of the interface type
		  // listener is the activity itself
		  private OnItemSelectedListener listener;
		  
		  // Define the events that the fragment will use to communicate
		  public interface OnItemSelectedListener {
		    public void onTweetItemSelected(User u);
		  }
		  
		  // Store the listener (activity) that will have events fired once the fragment is attached
		  @Override
		  public void onAttach(Activity activity) {
		    super.onAttach(activity);
		      if (activity instanceof OnItemSelectedListener) {
		        listener = (OnItemSelectedListener) activity;
		      } else {
		        throw new ClassCastException(activity.toString()
		            + " must implement HomeTimelineFragment.OnItemSelectedListener");
		      }
		  }
		  
		// Now we can fire the event when the user selects something in the fragment
		public void onProfileImageClick(User u) {
		     listener.onTweetItemSelected(u);
		}

	
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
		
		lvTweets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Tweet tweet = aTweets.getItem(position);
				User user = tweet.getUser();
				onProfileImageClick(user);
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
