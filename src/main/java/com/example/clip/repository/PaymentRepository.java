package com.example.clip.repository;

import com.example.clip.model.Payment;
import com.example.clip.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	@Query("select DISTINCT new com.example.clip.model.User(a.userId, a.userName) from Payment a")
	List<User> getUsersWithPayload();

	List<Payment> findByStatus(String status);

}
