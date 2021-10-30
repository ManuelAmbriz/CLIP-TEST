package com.example.clip.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "amount")
    @JsonProperty("payment")
    private BigDecimal amount;
    
    @Column(name = "disbursement")
    @JsonProperty("disbursement")
    private BigDecimal disbursement;

    @Column(name = "user_id")
    @JsonProperty("user_id")
    private String userId;

    @Column(name = "user_name")
    @JsonProperty("user_name")
    private String userName;
    
    @Column(name = "status")
    @JsonProperty("status")
    private String status;
    
    

}
