package com.neueda.test.neueda.services;

import com.neueda.test.neueda.domain.dto.ATM;
import com.neueda.test.neueda.domain.dto.Account;
import com.neueda.test.neueda.domain.exception.*;
import com.neueda.test.neueda.domain.request.WithdrawRequest;
import com.neueda.test.neueda.domain.response.WithdrawResponse;
import com.neueda.test.neueda.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WithdrawServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private WithdrawService withdrawService;

    @Test
    public void testWithdrawRequestMultiplesOfFive() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(800);
        account.setOverDraft(200);

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("12345");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(501);

        when(accountRepository.findByAccountNumber(withdrawRequest.getAccountNumber())).thenReturn(account);

        Assertions.assertThrows(MultiplesOfFiveException.class, () -> withdrawService.getWithdrawRequest(withdrawRequest));

        verify(accountRepository, times(1)).findByAccountNumber(withdrawRequest.getAccountNumber());
    }

    @Test
    public void testWithdrawRequestAtmMoneyless() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(800);
        account.setOverDraft(200);

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("12345");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(1505);

        when(accountRepository.findByAccountNumber(withdrawRequest.getAccountNumber())).thenReturn(account);

        Assertions.assertThrows(AtmMoneylessException.class, () -> withdrawService.getWithdrawRequest(withdrawRequest));

        verify(accountRepository, times(1)).findByAccountNumber(withdrawRequest.getAccountNumber());
    }

    @Test
    public void testWithdrawRequestAccountBalance() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(800);
        account.setOverDraft(200);

        ATM.setAtm_Balance(1500);

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("12345");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(1005);

        when(accountRepository.findByAccountNumber(withdrawRequest.getAccountNumber())).thenReturn(account);

        Assertions.assertThrows(AccountBalanceException.class, () -> withdrawService.getWithdrawRequest(withdrawRequest));

        verify(accountRepository, times(1)).findByAccountNumber(withdrawRequest.getAccountNumber());
    }

    @Test
    public void testWithdrawRequestDispenseExactAmount() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(800);
        account.setOverDraft(200);

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("12345");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(110);

        ATM.setAtm_Balance(1500);
        ATM.setNoteCounts(new int[]{10, 30, 0, 1});

        when(accountRepository.findByAccountNumber(withdrawRequest.getAccountNumber())).thenReturn(account);

        Assertions.assertThrows(DispenseExactAmountException.class, () -> withdrawService.getWithdrawRequest(withdrawRequest));

        verify(accountRepository, times(1)).findByAccountNumber(withdrawRequest.getAccountNumber());

    }

    @Test
    public void testWithdrawRequest() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(800);
        account.setOverDraft(200);

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("12345");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(500);

        ATM.setAtm_Balance(1500);
        ATM.setNoteCounts(new int[]{10, 30, 30, 20});

        when(accountRepository.findByAccountNumber(withdrawRequest.getAccountNumber())).thenReturn(account);

        when(accountRepository.save(account)).thenReturn(account);

        WithdrawResponse withdrawResponse = withdrawService.getWithdrawRequest(withdrawRequest);

        Assertions.assertNotNull(withdrawResponse);
        Assertions.assertEquals(withdrawResponse.getReturnAmount(), withdrawRequest.getWithdrawAmount());
        Assertions.assertEquals(withdrawResponse.getFiftyCount(), 10);
        Assertions.assertEquals(withdrawResponse.getTwentyCount(), 0);
        Assertions.assertEquals(withdrawResponse.getTenCount(), 0);
        Assertions.assertEquals(withdrawResponse.getFiveCount(), 0);

        verify(accountRepository, times(1)).findByAccountNumber(withdrawRequest.getAccountNumber());

        verify(accountRepository, times(1)).save(account);


    }


}
