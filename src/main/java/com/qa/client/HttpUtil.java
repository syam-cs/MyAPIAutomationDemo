package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.qa.config.HttpConfig;
import com.qa.constants.MethodTypes;
import com.qa.data.AbstractResponse;

public class HttpUtil {
	
	CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
	ObjectMapper mapper = new ObjectMapper();
	ObjectWriter writer;
	AbstractResponse handler;
	HttpResponse httpResponse;

	public  <T extends AbstractResponse> T execute(String URI, MethodTypes type, String body, HashMap<String, String> headers, Class<T> responseClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException {		T response = null;
		
		HttpConfig config = new HttpConfig();
		config.setUrl(URI);
		config.setTimeout(10000);
		
		switch (type) {
		case POST:
			try {
				T responseExecute = post(config, body, headers, responseClass);
				response = (T) mapper.readValue(responseExecute.getContent(), responseClass);
				response.setStatusCode(responseExecute.getStatusCode());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case PUT:
			try {
				T responseExecute = put(config, body, headers, responseClass);
				response = (T) mapper.readValue(responseExecute.getContent(), responseClass);
				response.setStatusCode(responseExecute.getStatusCode());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case GET:
			try {
				T responseExecute = get(config,headers, responseClass);
				response = (T) mapper.readValue(responseExecute.getContent(), responseClass);
				response.setStatusCode(responseExecute.getStatusCode());
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		default:
			break;
		}
		
		return response;
	}
	
	private <T extends AbstractResponse> T get(HttpConfig httpConfig, HashMap<String, String> headers, Class<T> responseClass) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		T responseGet = null;
		HashMap<String, String> hashMap = new HashMap<String, String>();
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(httpConfig.getTimeout())
				.setConnectTimeout(httpConfig.getTimeout()).build();

		HttpGet httpGet = new HttpGet(httpConfig.getUrl());
		httpGet.setConfig(requestConfig);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");

		CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet);
		
		Header[] headerArray = httpResponse.getAllHeaders();
		for (Header header : headerArray) {
			hashMap.put(header.getName(), header.getValue());
		}
		
		HttpEntity responseEntity = httpResponse.getEntity();
		String responseExecute = EntityUtils.toString(responseEntity);
		responseGet = (T) Class.forName(responseClass.getName()).newInstance();
		
		responseGet.setContent(responseExecute);
		responseGet.setStatusCode(httpResponse.getStatusLine().getStatusCode());
		responseGet.setHeaderArray(hashMap);
			
		return responseGet;
		} 

	private <T extends AbstractResponse> T post(HttpConfig httpConfig, String jsonString, HashMap<String, String> headers, Class<T> responseClass) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		T responsePost = null;
		HashMap<String, String> hashMap = new HashMap<String, String>();
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(httpConfig.getTimeout())
				.setConnectTimeout(httpConfig.getTimeout()).build()
				;


		HttpPost httpPost = new HttpPost(httpConfig.getUrl());
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setEntity(new StringEntity(jsonString));
		
		CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);
		
		Header[] headerArray = httpResponse.getAllHeaders();
		for (Header header : headerArray) {
			hashMap.put(header.getName(), header.getValue());
		}
		
		HttpEntity responseEntity = httpResponse.getEntity();
		String responseExecute = EntityUtils.toString(responseEntity);
		responsePost = (T) Class.forName(responseClass.getName()).newInstance();
		
		responsePost.setContent(responseExecute);
		responsePost.setStatusCode(httpResponse.getStatusLine().getStatusCode());
		responsePost.setHeaderArray(hashMap);
		
		return responsePost;
		}
	
	private <T extends AbstractResponse> T put(HttpConfig httpConfig, String jsonString, HashMap<String, String> headers, Class<T> responseClass) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		T responsePut = null;
		HashMap<String, String> hashMap = new HashMap<String, String>();
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(httpConfig.getTimeout())
				.setConnectTimeout(httpConfig.getTimeout()).build()
				;


		HttpPut httpPut = new HttpPut(httpConfig.getUrl());
		httpPut.setConfig(requestConfig);
		httpPut.setHeader("Accept", "application/json");
		httpPut.setHeader("Content-type", "application/json");
		httpPut.setEntity(new StringEntity(jsonString));
		
		CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPut);
		
		Header[] headerArray = httpResponse.getAllHeaders();
		for (Header header : headerArray) {
			hashMap.put(header.getName(), header.getValue());
		}
		
		HttpEntity responseEntity = httpResponse.getEntity();
		String responseExecute = EntityUtils.toString(responseEntity);
		responsePut = (T) Class.forName(responseClass.getName()).newInstance();
		
		responsePut.setContent(responseExecute);
		responsePut.setStatusCode(httpResponse.getStatusLine().getStatusCode());
		responsePut.setHeaderArray(hashMap);
		
		return responsePut;
	}
	
	//Post Method with headers
	public CloseableHttpResponse postErrorRequest(String url, String entity, HashMap<String, String> headerMap) throws ClientProtocolException, IOException, JSONException {
		closeableHttpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(entity));
		
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}
		
		CloseableHttpResponse closeableHttpResponse =  closeableHttpClient.execute(httpPost	);
		return closeableHttpResponse;
	}
	
	//Put Method with headers
	public CloseableHttpResponse putErrorRequest(String url, String entity, HashMap<String, String> headerMap) throws ClientProtocolException, IOException, JSONException {
		closeableHttpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
		httpPut.setEntity(new StringEntity(entity));
		
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpPut.addHeader(entry.getKey(), entry.getValue());
		}
		
		CloseableHttpResponse closeableHttpResponse =  closeableHttpClient.execute(httpPut);
		return closeableHttpResponse;
	}
	
}
