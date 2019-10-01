package com.java.dto;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.qa.data.AbstractResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"name",
"job",
"updatedAt"
})
public class PutResponse extends AbstractResponse {

@JsonProperty("name")
private String name;
@JsonProperty("job")
private String job;
@JsonProperty("updatedAt")
private String updatedAt;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

@JsonProperty("job")
public String getJob() {
return job;
}

@JsonProperty("job")
public void setJob(String job) {
this.job = job;
}

@JsonProperty("updatedAt")
public String getUpdatedAt() {
return updatedAt;
}

@JsonProperty("updatedAt")
public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
