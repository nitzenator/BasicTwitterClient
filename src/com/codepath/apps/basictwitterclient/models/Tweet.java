package com.codepath.apps.basictwitterclient.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateUtils;

public class Tweet {

	private String body;
	private long uid;
	private String createdAt;
	private User user;
	
	
	public static Tweet fromJSON(JSONObject jsonObject){
		Tweet tweet = new Tweet();
		try{
			tweet.setBody(jsonObject.getString("text"));
			tweet.setUid(jsonObject.getLong("id"));
			tweet.setCreatedAt(jsonObject.getString("created_at"));
			tweet.setUser(User.fromJSON(jsonObject.getJSONObject("user")));
		}catch (JSONException e){
			e.printStackTrace();
			return null;
		}
		
		return tweet;
	}

	public String getRelativeTimeAgo() {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(getCreatedAt()).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static ArrayList<Tweet> fromJSONArray(JSONArray json) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		JSONObject tweetJson = null;
		for (int i = 0; i < json.length(); i++) {
			 try {
				tweetJson = json.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 Tweet newTweet = Tweet.fromJSON(tweetJson);
			 if(newTweet != null){
				 tweets.add(newTweet);
			 }
		}
		
		return tweets;
	}
	
	@Override
	public String toString() {
		
		return getBody() + "-" + getUser().getName();
	}


}
