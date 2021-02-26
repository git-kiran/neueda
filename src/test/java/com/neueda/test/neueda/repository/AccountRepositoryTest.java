package com.neueda.test.neueda.repository;

import com.neueda.test.neueda.domain.dto.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testfindByAccountNumber() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(500);
        account.setOverDraft(500);
        entityManager.persist(account);
        Account account1 = accountRepository.findByAccountNumber(account.getAccountNumber());
        assertEquals(account.getPin(), account1.getPin());
        assertEquals(account.getOpenBalance(), account1.getOpenBalance());
        assertEquals(account.getOverDraft(), account1.getOverDraft());
    }

    @Test
    public void testUpdate() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(500);
        account.setOverDraft(500);
        entityManager.persist(account);
        account.setOpenBalance(100);
        Account account1 = accountRepository.save(account);
        assertEquals(account.getPin(), account1.getPin());
        assertEquals(account.getOpenBalance(), account1.getOpenBalance());
        assertEquals(account.getOverDraft(), account1.getOverDraft());
    }
}
