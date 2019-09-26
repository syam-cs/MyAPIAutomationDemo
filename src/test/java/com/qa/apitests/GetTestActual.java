package com.qa.apitests;

import java.io.IOException;
import java.util.HashMap;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONException;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.dto.GetResponse;
import com.qa.client.HttpUtil;
import com.qa.client.RestClient;
import com.qa.constants.MethodTypes;

public class GetTestActual {
	
	String url = "https://reqres.in/api/users";
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	String response;
	HashMap<String, String> hashMap = new HashMap<String, String>();
	HashMap<String, String> headerMap = new HashMap<String, String>();
	HttpUtil httpUtil = new HttpUtil();
	ObjectMapper mapper =new ObjectMapper();

	
	@Test
	public void getTestCase() throws ClientProtocolException, IOException, JSONException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		GetResponse responseObj = httpUtil.execute(url, MethodTypes.GET, mapper.writeValueAsString(null), headerMap, GetResponse.class);
		System.out.println(responseObj.getTotal());
		System.out.println(responseObj.getHeaderArray());
	}

}
