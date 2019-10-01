package com.qa.apitests;

import java.io.File;
import java.util.HashMap;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.dto.GetResponse;
import com.java.dto.PostResponse;
import com.java.dto.PutResponse;
import com.qa.client.HttpUtil;
import com.qa.client.SchemaValidationUtil;
import com.qa.constants.Constants;
import com.qa.constants.MethodTypes;
import com.qa.data.Users;

public class GetTestActual {
	
	String url = "https://reqres.in/api/users";
	String urlBR = "https://reqres.in/api/register"; 
	CloseableHttpResponse closeableHttpResponse;
	String entity;
	HashMap<String, String> hashMap = new HashMap<String, String>();
	HashMap<String, String> headerMap = new HashMap<String, String>();
	HttpUtil httpUtil = new HttpUtil();
	ObjectMapper mapper =new ObjectMapper();

	
	@Test
	public void getTestCase() throws Exception {
		
		GetResponse responseObj = httpUtil.execute(url, MethodTypes.GET, mapper.writeValueAsString(null), headerMap, GetResponse.class);
		System.out.println(responseObj.getTotal());
		System.out.println(responseObj.getHeaderArray());
		
		// Do the Schema Validation
		File inputSchemaFilePath = new File(Constants.schemaDir
				+ Constants.FileSeparator+"GetTest.schema");
		SchemaValidationUtil.performSchemaValidation(mapper.writeValueAsString(responseObj), inputSchemaFilePath);
		
		System.out.println("Schema validation is successful");
	}
	
	@Test
	public void postTestCase() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Users users = new Users("Hehe", "leader");

		PostResponse responseObj = httpUtil.execute(url, MethodTypes.POST, objectMapper.writeValueAsString(users), headerMap, PostResponse.class);
		System.out.println(responseObj.getStatusCode());
		System.out.println(responseObj.getCreatedAt());
	}
	
	@Test
	public void putTestCase() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Users users = new Users("morpheus", "zion resident");

		PutResponse responseObj = httpUtil.execute(url+"/2", MethodTypes.PUT, objectMapper.writeValueAsString(users), headerMap, PutResponse.class);
		System.out.println(responseObj.getStatusCode());
		System.out.println(responseObj.getHeaderArray());
	}
	
	@Test
	public void badRequestTestCase() throws Exception {
		
		headerMap.put("Content-Type", "application/json");
		
		ObjectMapper objectMapper = new ObjectMapper();
		Users users = new Users("Hey", "leader");
		entity = objectMapper.writeValueAsString(users);
		
		closeableHttpResponse = httpUtil.postErrorRequest(urlBR, entity, headerMap);
		
		System.out.println("Status Code -->"+closeableHttpResponse.getStatusLine().getStatusCode());
		
		String response = EntityUtils.toString(closeableHttpResponse.getEntity());
		
		HashMap<String,Object> result = new ObjectMapper().readValue(response, new TypeReference<HashMap<String, Object>>(){});
		System.out.println("Json Object is -->"+result);
		
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		
		for (Header header : headerArray) {
			hashMap.put(header.getName(), header.getValue());
		}
		
		System.out.println("Header Array is -->"+hashMap);
	}

}
