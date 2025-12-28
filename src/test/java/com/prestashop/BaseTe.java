package com.prestashop;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTe {
  protected Playwright playwright;
  protected Browser browser;
  protected BrowserContext context;
  protected Page page;
  protected static TestData testData = new TestData();

  @BeforeEach
  public void setUp() {
    playwright = Playwright.create();

    browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
        .setHeadless(false));

    context = browser.newContext(new Browser.NewContextOptions()
        .setViewportSize(1280, 720));

    page = context.newPage();

    // ✅ rrit timeoutet (shumë e rëndësishme për demo.prestashop)
    page.setDefaultTimeout(60000);
    page.setDefaultNavigationTimeout(60000);
  }

  @AfterEach
  public void tearDown() {
    if (context != null) context.close();
    if (browser != null) browser.close();
    if (playwright != null) playwright.close();
  }
}
