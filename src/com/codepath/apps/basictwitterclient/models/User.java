package com.codepath.apps.basictwitterclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5710623111728367282L;
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private int numFollowers;
	private int numFollowing;

	public static User fromJSON(JSONObject jsonObject) {
		User user = new User();
		try{
			user.setName(jsonObject.getString("name"));
			user.setUid(jsonObject.getLong("id"));
			user.setScreenName(jsonObject.getString("screen_name"));
			user.setProfileImageUrl(jsonObject.getString("profile_image_url"));
			user.setNumFollowers(jsonObject.getInt("followers_count"));
			user.setNumFollowing(jsonObject.getInt("friends_count"));
			
			
		}catch (JSONException e){
			e.printStackTrace();
			return null;
		}
		
		return user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public int getNumFollowers() {
		return numFollowers;
	}

	public void setNumFollowers(int numFollowers) {
		this.numFollowers = numFollowers;
	}

	public int getNumFollowing() {
		return numFollowing;
	}

	public void setNumFollowing(int numFollowing) {
		this.numFollowing = numFollowing;
	}

}
