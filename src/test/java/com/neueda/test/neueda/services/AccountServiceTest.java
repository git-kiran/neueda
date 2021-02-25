package com.neueda.test.neueda.services;

import com.neueda.test.neueda.domain.dto.Account;
import com.neueda.test.neueda.domain.exception.AccountNumberNotFoundException;
import com.neueda.test.neueda.domain.exception.PinInvalidException;
import com.neueda.test.neueda.domain.request.AccountRequest;
import com.neueda.test.neueda.domain.response.AccountResponse;
import com.neueda.test.neueda.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testAccountRequest() {

        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(500);
        account.setOverDraft(500);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber("12345");
        accountRequest.setPin(1234);

        when(accountRepository.findByAccountNumber(accountRequest.getAccountNumber())).thenReturn(account);

        AccountResponse accountResponse = accountService.getRequest(accountRequest);

        Assertions.assertNotNull(accountResponse);
        Assertions.assertEquals(accountResponse.getOpenBalance(), account.getOpenBalance());
        Assertions.assertEquals(accountResponse.getMaxWithdrawBalance(), (account.getOpenBalance() + account.getOverDraft()));

        verify(accountRepository, times(1)).findByAccountNumber(accountRequest.getAccountNumber());
    }

    @Test
    public void testAccountNumberNotFoundRequest() {

        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(500);
        account.setOverDraft(500);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber("123");
        accountRequest.setPin(1234);

        when(accountRepository.findByAccountNumber(accountRequest.getAccountNumber())).thenReturn(null);

        Assertions.assertThrows(AccountNumberNotFoundException.class, () -> accountService.getRequest(accountRequest));

        verify(accountRepository, times(1)).findByAccountNumber(accountRequest.getAccountNumber());

    }

    @Test
    public void testAccountPinNotFound() {

        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(500);
        account.setOverDraft(500);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber("12345");
        accountRequest.setPin(123);

        when(accountRepository.findByAccountNumber(accountRequest.getAccountNumber())).thenReturn(account);

        Assertions.assertThrows(PinInvalidException.class, () -> accountService.getRequest(accountRequest));

        verify(accountRepository, times(1)).findByAccountNumber(accountRequest.getAccountNumber());
    }

}
