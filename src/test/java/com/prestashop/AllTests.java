package com.prestashop;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    RegisterTest.class,
    LoginTest.class,
    DashboardTest.class
})
public class AllTests {
}

