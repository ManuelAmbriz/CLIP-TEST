package com.example.clip.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.clip.request.PaymentRequest;
import com.example.clip.response.ClipResponse;
import com.example.clip.service.TransactionService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/clip")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    

    @RequestMapping(value = "/createPayload", method = RequestMethod.POST)
    public ClipResponse create(HttpServletRequest request, @RequestBody PaymentRequest paymentRequest) {
         return transactionService.createPayload(paymentRequest);
    }
    
    @RequestMapping(value = "/getUsersWithPayload", method = RequestMethod.GET)
    public ClipResponse getUsersWithPayload() {
    	 return  transactionService.getUsersWithPayload();
    }
    
    @RequestMapping(value = "/disbursementProcess", method = RequestMethod.GET)
    public ClipResponse disbursementProcess() {
    	 return  transactionService.disbursementProcess();
    }

    @RequestMapping(value = "/getReportByUser", method = RequestMethod.GET)
    public ClipResponse getReportByUser() {
    	 return  transactionService.getReportByUser();
    }

}
