package com.neueda.test.neueda.services;

import com.neueda.test.neueda.domain.dto.Account;
import com.neueda.test.neueda.domain.exception.AccountBalanceException;
import com.neueda.test.neueda.domain.exception.PinInvalidException;
import com.neueda.test.neueda.domain.exception.AccountNumberNotFoundException;
import com.neueda.test.neueda.domain.request.AccountRequest;
import com.neueda.test.neueda.domain.response.AccountResponse;
import com.neueda.test.neueda.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public AccountResponse getRequest(AccountRequest accountRequest) throws AccountBalanceException, PinInvalidException {
        Account account = accountRepository.findByAccountNumber(accountRequest.getAccountNumber());
        if (!Objects.nonNull(account)) {
            throw new AccountNumberNotFoundException("AccountNumber not found");
        }
        AccountResponse accountResponse = new AccountResponse();
        if (account.getPin() == accountRequest.getPin()) {
            accountResponse.setOpenBalance(account.getOpenBalance());
            accountResponse.setMaxWithdrawBalance(account.getOpenBalance() + account.getOverDraft());
        } else {
            throw new PinInvalidException("Pin is incorrect");
        }
        return accountResponse;
    }
}
