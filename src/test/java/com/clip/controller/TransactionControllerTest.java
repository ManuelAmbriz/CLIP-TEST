package com.clip.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.example.clip.model.Report;
import com.example.clip.model.User;
import com.example.clip.request.PaymentRequest;
import com.example.clip.response.ClipResponse;

public class TransactionControllerTest extends AbstractControllerTest {

	@Test
	public void shouldCreatePayment() throws Exception {

		String commentBody = "{\n" + "    \"userId\": \"001\",\n" + "    \"userName\": \"Manuel Ambriz\",\n"
				+ "    \"amount\": 100\n" + "}";
		PaymentRequest paymentRequest = new PaymentRequest("001", "Manuel Ambriz", new BigDecimal(100));
		ClipResponse response = new ClipResponse(true, "OK");
		// when
		when(transactionService.createPayload(paymentRequest)).thenReturn(response);

		// then
		mockMvc.perform(post("/api/clip/createPayload").content(commentBody).contentType(APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldReturnUsersWithPayload() throws Exception {

		List<User> users = new ArrayList<User>();
		PaymentRequest paymentRequest = new PaymentRequest("001", "Manuel Ambriz", new BigDecimal(100));
		transactionService.createPayload(paymentRequest);
		ClipResponse response = new ClipResponse(true, "OK", users);

		// when
		when(transactionService.getUsersWithPayload()).thenReturn(response);

		// then
		// then
		mockMvc.perform(get("/api/clip/getUsersWithPayload").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldReturnReportByUser() throws Exception {

		Map<String, Report> resultReport = new HashMap<String, Report>();
		PaymentRequest paymentRequest_1 = new PaymentRequest("001", "Manuel Ambriz", new BigDecimal(100));
		transactionService.createPayload(paymentRequest_1);
		PaymentRequest paymentRequest_2 = new PaymentRequest("001", "Manuel Ambriz", new BigDecimal(100));
		transactionService.createPayload(paymentRequest_2);
		PaymentRequest paymentRequest_3 = new PaymentRequest("002", "Alejandro Baca", new BigDecimal(200));
		transactionService.createPayload(paymentRequest_3);
		PaymentRequest paymentRequest_4 = new PaymentRequest("002", "Alejandro Baca", new BigDecimal(200));
		transactionService.createPayload(paymentRequest_4);
		
		transactionService.disbursementProcess();
		
		PaymentRequest paymentRequest_5 = new PaymentRequest("001", "Manuel Ambriz", new BigDecimal(200));
		transactionService.createPayload(paymentRequest_5);
		PaymentRequest paymentRequest_6 = new PaymentRequest("002", "Alejandro Baca", new BigDecimal(100));
		transactionService.createPayload(paymentRequest_6);
		
		resultReport.put("001", new Report(400D, 200D));
		resultReport.put("002", new Report(500D, 100D));
		
		ClipResponse response = new ClipResponse(true, "OK", resultReport);

		// when
		when(transactionService.getReportByUser()).thenReturn(response);

		// then
		// then
		mockMvc.perform(get("/api/clip/getReportByUser").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturnUnauthorized() throws Exception {
		mockMvc.perform(get("/api/clip/disbursementProcess").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}
}
