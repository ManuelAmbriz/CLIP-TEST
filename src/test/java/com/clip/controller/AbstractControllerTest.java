package com.clip.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.clip.ClipApplication;
import com.example.clip.service.TransactionService;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClipApplication.class)
public abstract class AbstractControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected TransactionService transactionService;


	@Before
	public void setUp() {
		Mockito.reset(transactionService);
	}

}
