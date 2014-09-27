package com.codepath.apps.basictwitterclient;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.basictwitterclient.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitterclient.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitterclient.fragments.TweetsListFragment;
import com.codepath.apps.basictwitterclient.models.Tweet;

public class TimelineActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
	}

	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "HomeTimelineFragment",
						HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "second",
			    		MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
		
	
	public void onProfileView(MenuItem mi){
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivity(intent);
	}

	public void onCompose(MenuItem mi){
		Intent composeIntent = new Intent(this, ComposeTweetActivity.class);
		//composeIntent.putExtra("settings", settings);
		startActivityForResult(composeIntent, TweetsListFragment.REQUEST_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  // REQUEST_CODE is defined above
		  if (resultCode == RESULT_OK && requestCode == TweetsListFragment.REQUEST_CODE) {
		     Tweet composedTweet = (Tweet) data.getSerializableExtra("posted_tweet");
		     HomeTimelineFragment homeFragment = (HomeTimelineFragment) getSupportFragmentManager().findFragmentByTag("HomeTimelineFragment");
			 homeFragment.addTweet(composedTweet);
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
