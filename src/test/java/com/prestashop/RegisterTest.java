package com.prestashop;

import com.microsoft.playwright.Frame;
import org.junit.jupiter.api.Test;

public class RegisterTest extends BaseTe {

  @Test
  public void test1_registerNewUser_and_logout() {
    // Step 1-2: open the site and go to Sign in, then Create account :contentReference[oaicite:1]{index=1}
    page.navigate("https://demo.prestashop.com/");

    // Wait for iframe and then use the Frame
    page.locator("iframe#framelive").waitFor();
    Frame shop = page.frame("framelive");

    // Sometimes a newsletter popup appears; close if present (safe/no-fail)
    if (shop.locator("button:has-text('Close')").count() > 0) {
      try { shop.locator("button:has-text('Close')").first().click(); } catch (Exception ignored) {}
    }
    if (shop.locator("button[aria-label='Close']").count() > 0) {
      try { shop.locator("button[aria-label='Close']").first().click(); } catch (Exception ignored) {}
    }

    // Go to Login page
    shop.locator("div.user-info a:has-text('Sign in')").click();

    // IMPORTANT: iframe often reloads after navigation → re-grab frame
    page.locator("iframe#framelive").waitFor();
    shop = page.frame("framelive");

    // Click "No account? Create one here"
    shop.locator("a:has-text('Create one here')").click();

    // Again re-grab frame to avoid TargetClosedError
    page.locator("iframe#framelive").waitFor();
    shop = page.frame("framelive");

    // Now fill the registration form
    RegisterPage register = new RegisterPage(shop);

    register.assertRegisterTitleOrHeading();
    register.assertFormOpened();
    TestData testData = new TestData();
    testData.email = "tarazhijolia+" + System.currentTimeMillis() + "@gmail.com";
    testData.password = "Jolia123!";

    register.fillMandatoryAndSubmit("Jolia", "Tarazhi", testData.email, testData.password);

    // Step: verify logged in + logout :contentReference[oaicite:2]{index=2}
    register.assertLoggedIn();
    register.logoutAndAssertSignInVisible();


  }
}
