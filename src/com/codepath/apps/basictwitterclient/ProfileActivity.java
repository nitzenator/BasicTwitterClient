package com.codepath.apps.basictwitterclient;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitterclient.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	private TwitterClient client;
	private ImageView profileImageView;
	private TextView tvUsername;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		client = new TwitterClient(this);
		profileImageView = (ImageView) findViewById(R.id.ivProfileImage);
		tvUsername = (TextView) findViewById(R.id.tvUsername);
		loadProfileInfo();
		
	}

	private void loadProfileInfo() {
		client.getMyInfo(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJSON(json);
				getActionBar().setTitle("@" + u.getScreenName());
				
				profileImageView.setImageResource(android.R.color.transparent);
				tvUsername.setText(u.getName());
				
				ImageLoader imageLoader = ImageLoader.getInstance();
				imageLoader.displayImage(u.getProfileImageUrl(), profileImageView);
				
				// Begin the transaction
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				// Replace the container with the new fragment
				ft.replace(R.id.userTimelineHolder, UserTimelineFragment.getInstance(u.getUid()));
				// or ft.add(R.id.your_placeholder, new FooFragment());
				// Execute the changes specified
				ft.commit();
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
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
