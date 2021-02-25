package com.neueda.test.neueda.controller;

import com.neueda.test.neueda.domain.exception.InternalErrorException;
import com.neueda.test.neueda.domain.request.AccountRequest;
import com.neueda.test.neueda.domain.request.WithdrawRequest;
import com.neueda.test.neueda.domain.response.WithdrawResponse;
import com.neueda.test.neueda.services.AccountService;
import com.neueda.test.neueda.services.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    WithdrawService withdrawService;

    @PostMapping(path = "/request", consumes = "application/json", produces = "application/json")
    public String getRequest(@Valid @RequestBody AccountRequest accountRequest) {
        return accountService.getRequest(accountRequest).toString();
    }

    @PostMapping(path = "/withdraw", consumes = "application/json", produces = "application/json")
    public WithdrawResponse withdrawAmount(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        AccountRequest accountRequest = new AccountRequest(withdrawRequest.getAccountNumber() , withdrawRequest.getPin());
        if(!Objects.nonNull(accountService.getRequest(accountRequest))){
            throw new InternalErrorException("Hello User, something went wrong, please retry with with different withdrawal amount");
        }
        return withdrawService.getWithdrawRequest(withdrawRequest);
    }
}
