package com.neueda.test.neueda.services;

import com.neueda.test.neueda.domain.dto.ATM;
import com.neueda.test.neueda.domain.dto.Account;
import com.neueda.test.neueda.domain.exception.*;
import com.neueda.test.neueda.domain.request.WithdrawRequest;
import com.neueda.test.neueda.domain.response.WithdrawResponse;
import com.neueda.test.neueda.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawService {


    @Autowired
    AccountRepository accountRepository;

    WithdrawResponse withdrawResponse = new WithdrawResponse();

    public WithdrawResponse getWithdrawRequest(WithdrawRequest withdrawRequest) throws
            AccountBalanceException, AtmMoneylessException, DispenseExactAmountException, MultiplesOfFiveException {

        Account account = accountRepository.findByAccountNumber(withdrawRequest.getAccountNumber());
        //Valid cases
        if (withdrawRequest.getWithdrawAmount() % 5 != 0) {
            throw new MultiplesOfFiveException("Hello User, Please enter withdrawal amount in multiples of five");
        }

        if (withdrawRequest.getWithdrawAmount() > ATM.getAtm_Balance()) {
            throw new AtmMoneylessException("Hello User, Entered amount is more than the ATM capacity, Please visit another ATM");
        }

        if (withdrawRequest.getWithdrawAmount() > (account.getOpenBalance() + account.getOverDraft())) {
            throw new AccountBalanceException("Hello User, the amount you entered is more than the actual balance");
        }

        computeMinimumDispense(withdrawRequest);

        if (withdrawRequest.getWithdrawAmount() <= ATM.getAtm_Balance()) {
            if ((withdrawRequest.getWithdrawAmount() < 0 && withdrawRequest.getWithdrawAmount() <= (account.getOpenBalance() + account.getOverDraft())) || (withdrawRequest.getWithdrawAmount() <= account.getOpenBalance()) || (withdrawRequest.getWithdrawAmount() > account.getOpenBalance() && withdrawRequest.getWithdrawAmount() <= (account.getOpenBalance() + account.getOverDraft()))) {
                account.setOpenBalance(account.getOpenBalance() - withdrawRequest.getWithdrawAmount());
                ATM.setAtm_Balance(ATM.getAtm_Balance() - withdrawRequest.getWithdrawAmount());
                withdrawResponse.setReturnAmount(withdrawRequest.getWithdrawAmount());
                accountRepository.save(account);
            } else {
                throw new InternalErrorException("Hello User, something went wrong, please retry with with different withdrawal amount");
            }
        }
        return withdrawResponse;
    }


    public void computeMinimumDispense(WithdrawRequest withdrawRequest) {
        int withdrawAmount = withdrawRequest.getWithdrawAmount();
        int[] noteValues = ATM.getNoteValues();
        int[] noteCount = ATM.getNoteCounts();
        int[] tmpCount = noteCount.clone();

        int noteCountholder[] = new int[noteCount.length];
        int modHandler = 0;
        for (int i = 0; i < noteValues.length && withdrawAmount != 0; i++) {
            if (withdrawAmount >= noteValues[i]) {
                if (withdrawAmount / noteValues[i] <= tmpCount[i]) {
                    noteCountholder[i] = withdrawAmount / noteValues[i];
                    tmpCount[i] -= withdrawAmount / noteValues[i];
                    withdrawAmount = withdrawAmount % noteValues[i];
                } else {
                    noteCountholder[i] = tmpCount[i];
                    modHandler = withdrawAmount % noteValues[i];
                    withdrawAmount = (withdrawAmount / noteValues[i] - tmpCount[i]) * noteValues[i] + modHandler;
                    tmpCount[i] -= tmpCount[i];
                }
            }
        }
        if (withdrawAmount > 0) {
            throw new DispenseExactAmountException("Hello User, Sorry!!! we could not dispense the exact amount you requested due to shortage of notes in the ATM machine");
        }
        noteCount = tmpCount;
        ATM.setNoteCounts(noteCount);
        withdrawResponse.setFiftyCount(noteCountholder[0]);
        withdrawResponse.setTwentyCount(noteCountholder[1]);
        withdrawResponse.setTenCount(noteCountholder[2]);
        withdrawResponse.setFiveCount(noteCountholder[3]);
    }
}
