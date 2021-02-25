package com.neueda.test.neueda.repository;

import com.neueda.test.neueda.domain.dto.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByAccountNumber(String accountNumber);
}
