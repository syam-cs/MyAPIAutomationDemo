package com.qa.data;

import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractResponse {
	
	private String content;
	private int statusCode;
	HashMap<String, String> headerArray = new HashMap<String, String>();
	
	public HashMap<String, String> getHeaderArray() {
		return headerArray;
	}

	public void setHeaderArray(HashMap<String, String> headerArray) {
		this.headerArray = headerArray;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}


}
