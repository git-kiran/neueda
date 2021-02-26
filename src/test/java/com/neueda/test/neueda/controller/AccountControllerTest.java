package com.neueda.test.neueda.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neueda.test.neueda.domain.dto.ATM;
import com.neueda.test.neueda.domain.dto.Account;
import com.neueda.test.neueda.domain.exception.AccountNumberNotFoundException;
import com.neueda.test.neueda.domain.exception.AtmMoneylessException;
import com.neueda.test.neueda.domain.exception.MultiplesOfFiveException;
import com.neueda.test.neueda.domain.request.AccountRequest;
import com.neueda.test.neueda.domain.request.WithdrawRequest;
import com.neueda.test.neueda.domain.response.AccountResponse;
import com.neueda.test.neueda.domain.response.WithdrawResponse;
import com.neueda.test.neueda.services.AccountService;
import com.neueda.test.neueda.services.WithdrawService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.BDDMockito.given;

@WebMvcTest(AccountController.class)
@ActiveProfiles("test")
@AutoConfigureJsonTesters
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    AccountService accountService;

    @MockBean
    WithdrawService withdrawService;

    @Test
    public void getRequestTest() throws Exception {

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber("12345");
        accountRequest.setPin(1234);

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setOpenBalance(800);
        accountResponse.setMaxWithdrawBalance(1000);

        given(accountService.getRequest(accountRequest)).willReturn(accountResponse);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/request").contentType(MediaType.APPLICATION_JSON).content(asJsonString(accountRequest))).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        String checkresponse = accountResponse.toString();
        assertEquals(response.getContentAsString(), checkresponse);

    }

    @Test
    public void getwithdrawTest() throws Exception {

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("12345");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(500);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(withdrawRequest.getAccountNumber());
        accountRequest.setPin(withdrawRequest.getPin());

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setOpenBalance(500);
        accountResponse.setMaxWithdrawBalance(800);

        WithdrawResponse withdrawResponse = new WithdrawResponse();
        withdrawResponse.setReturnAmount(500);
        withdrawResponse.setFiftyCount(10);
        withdrawResponse.setTwentyCount(0);
        withdrawResponse.setTenCount(0);
        withdrawResponse.setFiveCount(0);

        given(accountService.getRequest(accountRequest)).willReturn(accountResponse);

        given(withdrawService.getWithdrawRequest(withdrawRequest)).willReturn(withdrawResponse);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/withdraw").contentType(MediaType.APPLICATION_JSON).content(asJsonString(withdrawRequest))).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        String checkresponse = asJsonString(withdrawResponse);
        assertEquals(response.getContentAsString(), checkresponse);
    }

    @Test
    public void getAccountNumberNotFoundTest() throws Exception {

        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(500);
        account.setOverDraft(300);

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("1234");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(500);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(withdrawRequest.getAccountNumber());
        accountRequest.setPin(withdrawRequest.getPin());

        given(accountService.getRequest(accountRequest)).willThrow(new AccountNumberNotFoundException("AccountNumber not found"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/withdraw").contentType(MediaType.APPLICATION_JSON).content(asJsonString(withdrawRequest))).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.FORBIDDEN.value());
        assertEquals(response.getContentAsString(), "AccountNumber not found");
    }

    @Test
    public void getPinInvalidTest() throws Exception {

        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(500);
        account.setOverDraft(300);

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("12345");
        withdrawRequest.setPin(123);
        withdrawRequest.setWithdrawAmount(500);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(withdrawRequest.getAccountNumber());
        accountRequest.setPin(withdrawRequest.getPin());

        given(accountService.getRequest(accountRequest)).willThrow(new AccountNumberNotFoundException("Pin is incorrect"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/withdraw").contentType(MediaType.APPLICATION_JSON).content(asJsonString(withdrawRequest))).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.FORBIDDEN.value());
        assertEquals(response.getContentAsString(), "Pin is incorrect");
    }

    @Test
    public void getMultiplesOfFiveTest() throws Exception {

        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(500);
        account.setOverDraft(300);

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("12345");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(501);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(withdrawRequest.getAccountNumber());
        accountRequest.setPin(withdrawRequest.getPin());

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setOpenBalance(500);
        accountResponse.setMaxWithdrawBalance(800);

        given(accountService.getRequest(accountRequest)).willReturn(accountResponse);

        given(withdrawService.getWithdrawRequest(withdrawRequest)).willThrow(new MultiplesOfFiveException("Hello User, Please enter withdrawal amount in multiples of five"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/withdraw").contentType(MediaType.APPLICATION_JSON).content(asJsonString(withdrawRequest))).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.FORBIDDEN.value());
        assertEquals(response.getContentAsString(), "Hello User, Please enter withdrawal amount in multiples of five");
    }

    @Test
    public void getAtmMoneyLessTest() throws Exception {

        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(500);
        account.setOverDraft(300);

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("12345");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(1505);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(withdrawRequest.getAccountNumber());
        accountRequest.setPin(withdrawRequest.getPin());

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setOpenBalance(500);
        accountResponse.setMaxWithdrawBalance(800);

        ATM.setAtm_Balance(1500);

        given(accountService.getRequest(accountRequest)).willReturn(accountResponse);

        given(withdrawService.getWithdrawRequest(withdrawRequest)).willThrow(new AtmMoneylessException("Hello User, Entered amount is more than the ATM capacity, Please visit another ATM"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/withdraw").contentType(MediaType.APPLICATION_JSON).content(asJsonString(withdrawRequest))).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.FORBIDDEN.value());
        assertEquals(response.getContentAsString(), "Hello User, Entered amount is more than the ATM capacity, Please visit another ATM");
    }


    @Test
    public void getDispenseExactAmountTest() throws Exception {

        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(500);
        account.setOverDraft(300);

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("12345");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(510);

        ATM.setTenCount(0);
        ATM.setFiveCount(1);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(withdrawRequest.getAccountNumber());
        accountRequest.setPin(withdrawRequest.getPin());

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setOpenBalance(500);
        accountResponse.setMaxWithdrawBalance(800);

        given(accountService.getRequest(accountRequest)).willReturn(accountResponse);

        given(withdrawService.getWithdrawRequest(withdrawRequest)).willThrow(new AtmMoneylessException("Hello User, Sorry!!! we could not dispense the exact amount you requested due to shortage of notes in the ATM machine"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/withdraw").contentType(MediaType.APPLICATION_JSON).content(asJsonString(withdrawRequest))).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.FORBIDDEN.value());
        assertEquals(response.getContentAsString(), "Hello User, Sorry!!! we could not dispense the exact amount you requested due to shortage of notes in the ATM machine");
    }

    @Test
    public void getAccountBalanceTest() throws Exception {

        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPin(1234);
        account.setOpenBalance(500);
        account.setOverDraft(300);

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("12345");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(900);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(withdrawRequest.getAccountNumber());
        accountRequest.setPin(withdrawRequest.getPin());

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setOpenBalance(500);
        accountResponse.setMaxWithdrawBalance(800);

        given(accountService.getRequest(accountRequest)).willReturn(accountResponse);

        given(withdrawService.getWithdrawRequest(withdrawRequest)).willThrow(new AtmMoneylessException("Hello User, Entered amount is more than the ATM capacity, Please visit another ATM"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/withdraw").contentType(MediaType.APPLICATION_JSON).content(asJsonString(withdrawRequest))).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.FORBIDDEN.value());
        assertEquals(response.getContentAsString(), "Hello User, Entered amount is more than the ATM capacity, Please visit another ATM");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
