package com.example.clip.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clip.enumeration.StatusPaymentEnum;
import com.example.clip.model.Payment;
import com.example.clip.model.Report;
import com.example.clip.model.User;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.request.PaymentRequest;
import com.example.clip.response.ClipResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionService {

	@Autowired
	PaymentRepository paymentRepository;
	
	public ClipResponse createPayload(PaymentRequest paymentRequest) {
		try {
			Payment payment = paymentRequest.convertToPayment();
			paymentRepository.save(payment);
			log.info("Payload Created Successfully");
			return new ClipResponse(true, "OK");
		} catch (PersistenceException ex) {
			return new ClipResponse(false, ex.getMessage());
		} catch (Exception e) {
			return new ClipResponse(false, e.getMessage());
		}

	}

	public ClipResponse getUsersWithPayload() {
		try {

			List<User> usersWithPayload = paymentRepository.getUsersWithPayload();
			log.info("Get User With Payload " + usersWithPayload.size());

			return new ClipResponse(true, "OK", usersWithPayload);

		} catch (Exception e) {
			return new ClipResponse(false, e.getMessage());
		}

	}

	public ClipResponse disbursementProcess() {
		try {
			List<Payment> payments = paymentRepository.findByStatus(StatusPaymentEnum.NEW.name());

			payments.stream().forEach(payment -> {
				payment.setDisbursement(
						payment.getAmount().multiply(new BigDecimal(0.965)).setScale(2, RoundingMode.UP));
				payment.setStatus(StatusPaymentEnum.PROCESSED.name());
				paymentRepository.save(payment);
			});

			Map<String, List<Payment>> paymentByUser = payments.stream().collect(Collectors
					.groupingBy(Payment::getUserId, Collectors.mapping(Function.identity(), Collectors.toList())));

			return new ClipResponse(true, "OK", paymentByUser);

		} catch (Exception e) {
			return new ClipResponse(false, e.getMessage());
		}

	}

	public ClipResponse getReportByUser() {
		try {
			List<Payment> paymentsAll = paymentRepository.findAll();

			Map<String, Report> resultReport = new HashMap<String, Report>();

			Map<String, List<Payment>> paymentByUser = paymentsAll.stream().collect(Collectors
					.groupingBy(Payment::getUserId, Collectors.mapping(Function.identity(), Collectors.toList())));

			paymentByUser.forEach((userId, payments) -> {
				double paymentsSum = payments.stream().mapToDouble(p -> p.getAmount().doubleValue()).sum();
				double newPayments = payments.stream().filter(p -> p.getStatus().equals(StatusPaymentEnum.NEW.name()))
						.mapToDouble(p -> p.getAmount().doubleValue()).sum();
				resultReport.put(userId, new Report(paymentsSum, newPayments));
			});

			return new ClipResponse(true, "OK", resultReport);

		} catch (Exception e) {
			return new ClipResponse(false, e.getMessage());
		}

	}

}
