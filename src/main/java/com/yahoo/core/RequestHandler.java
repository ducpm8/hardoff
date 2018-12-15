package com.yahoo.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class RequestHandler {
	
  private static final String USER_AGENT = "Mozilla/5.0";
  
  public static StringBuffer response(String url) throws IOException{
	
	//String url = "https://netmall.hardoff.co.jp/cate/00010002/?p=1&s=1&pl=54";

	HttpClient client = HttpClients.custom()
	        .setDefaultRequestConfig(RequestConfig.custom()
	            .setCookieSpec(CookieSpecs.STANDARD).build())
	        .build();
	
	HttpGet request = new HttpGet(url);

	request.addHeader("User-Agent", USER_AGENT);
	HttpResponse response = client.execute(request);

//	System.out.println("Response Code : "
//	                + response.getStatusLine().getStatusCode());

	HttpEntity entity = response.getEntity(); 
	InputStream is = entity.getContent(); 
	BufferedReader in = new BufferedReader(new InputStreamReader(is));
	
	StringBuffer result = new StringBuffer();
	String line = "";
	while ((line = in.readLine()) != null) {
		result.append(line);
	}
	
	in.close();
	is.close();
	
	return result;
  }
}
