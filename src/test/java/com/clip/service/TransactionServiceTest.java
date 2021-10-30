package com.clip.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.clip.ClipApplication;
import com.example.clip.model.Payment;
import com.example.clip.model.Report;
import com.example.clip.model.User;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.request.PaymentRequest;
import com.example.clip.response.ClipResponse;
import com.example.clip.service.TransactionService;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClipApplication.class)
public class TransactionServiceTest {
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	TransactionService transactionService;
	
	@Test
	public void shouldCreatePayment() {
		PaymentRequest paymentRequest = new PaymentRequest("003", "Itzel Zarate", new BigDecimal(100));		
		ClipResponse clipResponse = transactionService.createPayload(paymentRequest);

		assertThat("Comment id shouldn't be null", clipResponse, notNullValue());
	}


	@Test
	public void shouldReturnUsersWithPayload() {
		List<User> users = new ArrayList<User>();
		PaymentRequest paymentRequest = new PaymentRequest("004", "Sandra Baca", new BigDecimal(100));
		transactionService.createPayload(paymentRequest);
		ClipResponse response = transactionService.getUsersWithPayload();
		users = (List<User>) response.getObject();
		
		assertThat("There should be one comment", users, hasSize(1));
		assertThat(users.get(0).getUserId(), equalTo("004"));
		assertThat(users.get(0).getUserName(), equalTo("Sandra Baca"));
	}
	
	@Test
	public void shouldReturnReportByUser() {
		
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
		
		
		ClipResponse response = transactionService.getReportByUser();
		resultReport = (Map<String, Report>) response.getObject();
		
		
		
		assertThat("There should be one comment", resultReport.size(), equalTo(3));
		assertThat(resultReport.get("001").getPaymentsSum(), equalTo(400D));
		assertThat(resultReport.get("001").getNewPaymentsAmount(), equalTo(200D));
		assertThat(resultReport.get("002").getPaymentsSum(), equalTo(500D));
		assertThat(resultReport.get("002").getNewPaymentsAmount(), equalTo(100D));
	}
	
	@Test
	public void shouldReturnDisbursementProcess() {
		 transactionService.disbursementProcess();
		
		Map<String, List<Payment>> paymentByUser = new HashMap<String, List<Payment>>();
		PaymentRequest paymentRequest_1 = new PaymentRequest("001", "Manuel Ambriz", new BigDecimal(100));
		transactionService.createPayload(paymentRequest_1);
		
		PaymentRequest paymentRequest_2 = new PaymentRequest("002", "Alejandro Baca", new BigDecimal(200));
		transactionService.createPayload(paymentRequest_2);

			
		ClipResponse response = transactionService.disbursementProcess();
		paymentByUser =  (Map<String, List<Payment>>) response.getObject();
		
		
		
		assertThat("There should be one comment", paymentByUser.size(), equalTo(2));
		assertThat(paymentByUser.get("001").size() , equalTo(1));
		assertThat(paymentByUser.get("002").size() , equalTo(1));
		assertThat(paymentByUser.get("001").get(0).getAmount().toString() , equalTo( "100.00"));
		assertThat(paymentByUser.get("001").get(0).getDisbursement().toString() , equalTo( "96.50"));
		assertThat(paymentByUser.get("002").get(0).getAmount().toString() , equalTo("200.00"));
		assertThat(paymentByUser.get("002").get(0).getDisbursement().toString() , equalTo("193.00"));
	}
}
