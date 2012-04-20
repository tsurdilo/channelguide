package org.jboss.channelguide.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class VideAdder {
	public static void main(String[] args) {
		
		try {
			//vid 1
//			String videourl = URLEncoder.encode("http://www.youtube.com/watch?feature=player_embedded&v=gf6ycCFG_yE", "UTF-8");
//			String videothumb = URLEncoder.encode("http://img.youtube.com/vi/gf6ycCFG_yE/3.jpg", "UTF-8");
//			String videotitle = URLEncoder.encode("jBPM Designer 2.1 released", "UTF-8");
//			String videocategory = "Designer";
			
			//vid 2
			String videourl = URLEncoder.encode("http://www.youtube.com/watch?v=m45sUNK1Vyg&feature=related", "UTF-8");
			String videothumb = URLEncoder.encode("http://img.youtube.com/vi/m45sUNK1Vyg/3.jpg", "UTF-8");
			String videotitle = URLEncoder.encode("jBPM5 Introduction", "UTF-8");
			String videocategory = "jBPM";
			
			String videoInfo = "http://channelguide-tsurdilo.rhcloud.com/channelguide/videos";
			videoInfo += "?vurl=" + videourl;
			videoInfo += "&vth=" + videothumb;
			videoInfo += "&vti=" + videotitle;
			videoInfo += "&vc=" + videocategory;
			
			URL videoURL = new URL(videoInfo);
			HttpURLConnection addVideoConnection = (HttpURLConnection) videoURL.openConnection();
			addVideoConnection.setRequestMethod("POST");
			addVideoConnection.connect();
			
			System.out.println("Response code: " + addVideoConnection.getResponseCode());
			
			BufferedReader sreader = new BufferedReader(new InputStreamReader(
					addVideoConnection.getInputStream(), "UTF-8"));
	        StringBuilder stringBuilder = new StringBuilder();

	        String line = null;
	        while ((line = sreader.readLine()) != null) {
	            stringBuilder.append(line + "\n");
	        }
	        
	        System.out.println("Response: " + stringBuilder.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
