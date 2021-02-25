package com.neueda.test.neueda.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Column(name = "PIN")
    private int pin;
    @Column(name = "OPEN_BALANCE")
    private int openBalance;
    @Column(name = "OVERDRAFT")
    private int overDraft;
}
