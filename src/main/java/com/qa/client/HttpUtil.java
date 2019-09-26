package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
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
				response = (T) mapper.readValue(post(config, body, headers), responseClass);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case PUT:
			try {
				response = (T) mapper.readValue(put(config, body, headers), responseClass);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case GET:
			try {
//				response = (T) mapper.readValue(get(config, headers, statusCode), responseClass);
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

	private String post(HttpConfig httpConfig, String jsonString, HashMap<String, String> headers) throws IOException {

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(httpConfig.getTimeout())
				.setConnectTimeout(httpConfig.getTimeout()).build()
				;


		HttpPost httpPost = new HttpPost(httpConfig.getUrl());
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		if(Objects.nonNull(headers.get("jwt")))
			httpPost.setHeader(HttpHeaders.AUTHORIZATION,headers.get("jwt"));
		StringEntity entity = new StringEntity(jsonString);
		httpPost.setEntity(entity);

		try {
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
					int status = httpResponse.getStatusLine().getStatusCode();
					//Asserting the Status Code
//					Assert.assertEquals(status, statusCode, "Asserting the Status Code");
					HttpEntity responseEntity = httpResponse.getEntity();
					return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
					
				}
			};
			return closeableHttpClient.execute(httpPost, responseHandler);
		} finally {
			//closeableHttpClient.close();
		}
	}
	
	private String put(HttpConfig httpConfig, String jsonString, HashMap<String, String> headers) throws IOException {

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(httpConfig.getTimeout())
				.setConnectTimeout(httpConfig.getTimeout()).build()
				;


		HttpPut httpPut = new HttpPut(httpConfig.getUrl());
		httpPut.setConfig(requestConfig);
		httpPut.setHeader("Accept", "application/json");
		httpPut.setHeader("Content-type", "application/json");
		if(Objects.nonNull(headers.get("jwt")))
			httpPut.setHeader(HttpHeaders.AUTHORIZATION,headers.get("jwt"));
		StringEntity entity = new StringEntity(jsonString);
		httpPut.setEntity(entity);

		try {
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
					int status = httpResponse.getStatusLine().getStatusCode();
					//Asserting the Status Code
//					Assert.assertEquals(status, statusCode, "Asserting the Status Code");
					HttpEntity responseEntity = httpResponse.getEntity();
					return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
				}
			};
			return closeableHttpClient.execute(httpPut, responseHandler);
		} finally {
			//closeableHttpClient.close();
		}
	}
	
}
