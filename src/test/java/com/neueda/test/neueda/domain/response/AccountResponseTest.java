package com.neueda.test.neueda.domain.response;

import com.neueda.test.neueda.domain.dto.Account;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

public class AccountResponseTest {
    @Test
    public void gettersAndSettersShouldFunctionCorrectly() {
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(AccountResponse.class);
    }
}
