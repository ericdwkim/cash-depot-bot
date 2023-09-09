package com.txb.cashdepot;
public class WebsiteCredentials {
	
	private String website, username, pw;
	
	public WebsiteCredentials(String website, String username, String pw)
	{
		this.website = website;
		this.username = username;
		this.pw = pw;
	}

	public String getWebsite() {
		return website;
	}

	public String getUsername() {
		return username;
	}

	public String getPw() {
		return pw;
	}
}
