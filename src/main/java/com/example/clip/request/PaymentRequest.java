package com.example.clip.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import com.example.clip.enumeration.StatusPaymentEnum;
import com.example.clip.model.Payment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class PaymentRequest {

	@JsonProperty("userId")
    String userId;
	
	@JsonProperty("userName")
    String userName;
	
	@JsonProperty("amount")
    BigDecimal amount;
    
    
    public Payment convertToPayment() throws Exception {
    	Payment payment = new Payment();

    	payment.setUserId(this.userId);
    	payment.setAmount(this.amount);
    	payment.setUserName(this.userName);
    	payment.setStatus(StatusPaymentEnum.NEW.name());
		return payment;
	}
}
