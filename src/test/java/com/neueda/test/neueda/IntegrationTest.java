package com.neueda.test.neueda;

import com.neueda.test.neueda.domain.dto.ATM;
import com.neueda.test.neueda.domain.dto.Account;
import com.neueda.test.neueda.domain.request.AccountRequest;
import com.neueda.test.neueda.domain.request.WithdrawRequest;
import com.neueda.test.neueda.domain.response.AccountResponse;
import com.neueda.test.neueda.domain.response.WithdrawResponse;
import com.neueda.test.neueda.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = NeuedaApplication.class)
@ActiveProfiles("integration")
public class IntegrationTest {

    final private static int port = 8080;
    final private static String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;


    @Test
    public void handleAccountNumberNotFoundRequest() {

        AccountRequest accountRequest =  new AccountRequest();
        accountRequest.setAccountNumber("1234");
        accountRequest.setPin(1234);

        String responseString = this.restTemplate.postForObject(baseUrl + port + "/request", accountRequest, String.class);

        assertEquals(responseString, "AccountNumber not found");
    }

    @Test
    public void handlePinInvalidRequest() {

        AccountRequest accountRequest =  new AccountRequest();
        accountRequest.setAccountNumber("123456789");
        accountRequest.setPin(123);

        String responseString = this.restTemplate.postForObject(baseUrl + port + "/request", accountRequest, String.class);

        assertEquals(responseString, "Pin is incorrect");
    }

    @Test
    public void handleMultiplesOfFiveRequest() {
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("123456789");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(501);

        String responseString = this.restTemplate.postForObject(baseUrl + port + "/withdraw", withdrawRequest, String.class);

        assertEquals(responseString, "Hello User, Please enter withdrawal amount in multiples of five");
    }

    @Test
    public void handleAtmMoneylessRequest() {
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("123456789");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(1505);

        String responseString = this.restTemplate.postForObject(baseUrl + port + "/withdraw", withdrawRequest, String.class);

        assertEquals(responseString, "Hello User, Entered amount is more than the ATM capacity, Please visit another ATM");
    }

    @Test
    public void handleAccountBalanceRequest() {
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("123456789");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(1005);

        String responseString = this.restTemplate.postForObject(baseUrl + port + "/withdraw", withdrawRequest, String.class);

        assertEquals(responseString, "Hello User, the amount you entered is more than the actual balance");
    }

    @Test
    public void handleDispenseExactAmountRequest() {
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("123456789");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(710);

        int noteCount[] = {10, 30, 0, 1};
        ATM.setNoteCounts(noteCount);

        String responseString = this.restTemplate.postForObject(baseUrl + port + "/withdraw", withdrawRequest, String.class);

        assertEquals(responseString, "Hello User, Sorry!!! we could not dispense the exact amount you requested due to shortage of notes in the ATM machine");
    }

    @Test
    public void getBalanceRequest() {
        AccountRequest accountRequest =  new AccountRequest();
        accountRequest.setAccountNumber("123456789");
        accountRequest.setPin(1234);

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setOpenBalance(800);
        accountResponse.setMaxWithdrawBalance(1000);

        String responseString = this.restTemplate.postForObject(baseUrl + port + "/request", accountRequest, String.class);

        String checkresponse = accountResponse.toString();
        assertEquals(responseString, checkresponse);
    }

    @Test
    public void getWithdrawRequest() {
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("123456789");
        withdrawRequest.setPin(1234);
        withdrawRequest.setWithdrawAmount(500);

        WithdrawResponse withdrawResponse = new WithdrawResponse();
        withdrawResponse.setReturnAmount(500);
        withdrawResponse.setFiftyCount(10);
        withdrawResponse.setTwentyCount(0);
        withdrawResponse.setFiveCount(0);
        withdrawResponse.setTenCount(0);

        WithdrawResponse withdrawResponse1 = this.restTemplate.postForObject(baseUrl + port + "/withdraw", withdrawRequest, WithdrawResponse.class);

        assertEquals(withdrawResponse1, withdrawResponse);

        Account account = new Account();
        account.setAccountNumber("123456789");
        account.setPin(1234);
        account.setOpenBalance(800);
        account.setOverDraft(200);
        accountRepository.save(account);

        ATM.setAtm_Balance(1500);
        ATM.setNoteCounts(new int[]{10, 30, 30, 20});
    }

}
