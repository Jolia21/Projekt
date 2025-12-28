package com.prestashop;

import com.microsoft.playwright.Frame;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTe {

  @Test
  public void test2_login_with_credentials_from_test1() {

    HomePage home = new HomePage(page);
    home.open();

    Frame shop = home.shopFrame();
    home.waitShopReady(shop);
    home.closePopupsIfAny(shop);

    LoginPage loginPage = new LoginPage(shop);
    loginPage.openLogin();

    loginPage.login(TestData.email, TestData.password);

    RegisterPage registerPage = new RegisterPage(shop);
    registerPage.assertLoggedIn();
  }
}
