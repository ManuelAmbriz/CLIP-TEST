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
public class Report {
	
	@JsonProperty("payments_sum")
	private double paymentsSum;

	@JsonProperty("new_payments_amount")
	private double newPaymentsAmount;

}
