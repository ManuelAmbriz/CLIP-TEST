package com.example.clip.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

	@JsonProperty("user_id")
	private String userId;

	@JsonProperty("user_name")
	private String userName;

	
}
