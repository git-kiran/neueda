package com.neueda.test.neueda.domain.dto;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

public class AccountTest {
    @Test
    public void gettersAndSettersShouldFunctionCorrectly() {
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(Account.class);
    }
}
