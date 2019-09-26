package com.qa.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class RestClient {
	CloseableHttpClient closeableHttpClient;
	String response;
	HashMap <String, String> hashMap = new HashMap<String, String>();
	
	//Get Method without headers
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException, JSONException {
		closeableHttpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse closeableHttpResponse =  closeableHttpClient.execute(httpGet);
		return closeableHttpResponse;
	}
	
	//Get Method with headers
		public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException, JSONException {
			closeableHttpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpGet.addHeader(entry.getKey(), entry.getValue());
			}
			
			CloseableHttpResponse closeableHttpResponse =  closeableHttpClient.execute(httpGet);
			return closeableHttpResponse;
		}
		
		//Post Method with headers
		public CloseableHttpResponse post(String url, String entity, HashMap<String, String> headerMap) throws ClientProtocolException, IOException, JSONException {
			closeableHttpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(entity));
			
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
			
			CloseableHttpResponse closeableHttpResponse =  closeableHttpClient.execute(httpPost	);
			return closeableHttpResponse;
		}


}
