package com.example.clip.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClipResponse {

	@JsonProperty("success")
	private boolean isSuccess;
    
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("object")
    private Object object;
	
    public ClipResponse(boolean isSuccess, String description) {
		this.isSuccess = isSuccess;
		this.description = description;
	}

	public ClipResponse(boolean isSuccess, String description, Object object) {
		this.isSuccess = isSuccess;
		this.description = description;
		this.object = object;
	}

	public ClipResponse() {
		super();
	}
	
	
}
