package com.neueda.test.neueda.domain.request;

import com.neueda.test.neueda.domain.dto.Account;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

public class WithdrawRequestTest {
    @Test
    public void gettersAndSettersShouldFunctionCorrectly() {
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(WithdrawRequest.class);
    }
}
