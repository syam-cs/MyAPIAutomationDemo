package com.qa.apitests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.BaseAdapter;
import com.qa.client.RestClient;

public class GetTest extends BaseAdapter {
	
	BaseAdapter baseAdapter;
	String url = "https://reqres.in/api/users";
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	String response;
	HashMap<String, String> hashMap = new HashMap<String, String>();
	HashMap<String, String> headerMap = new HashMap<String, String>();
	
	@BeforeMethod
	public void setUp() throws FileNotFoundException {
		baseAdapter = new BaseAdapter();
//		url = prop.getProperty("URL")+prop.getProperty("serviceURL");
	}
	
	@Test
	public void getTestCase() throws ClientProtocolException, IOException, JSONException {
		restClient = new RestClient();
		headerMap.put("Content-Type", "application/json");
		closeableHttpResponse = restClient.get(url, headerMap);
		
		System.out.println("Status Code -->"+closeableHttpResponse.getStatusLine().getStatusCode());
		
		response = EntityUtils.toString(closeableHttpResponse.getEntity());
		//System.out.println(response);
		
		JSONObject jsonObject = new JSONObject(response);
		System.out.println("Json Object is -->"+jsonObject);
		
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		
		for (Header header : headerArray) {
			hashMap.put(header.getName(), header.getValue());
		}
		
		System.out.println("Header Array is -->"+hashMap);
	}
	
	
}
