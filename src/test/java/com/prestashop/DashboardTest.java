package com.prestashop;

import com.microsoft.playwright.Frame;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DashboardTest extends BaseTe {

  @Test
  public void test3_dashboardTest() {

    // Precondition: logged in (reuse login logic or shared state)

    page.navigate("https://demo.prestashop.com/");
    page.locator("iframe#framelive").waitFor();
    Frame shop = page.frame("framelive");

    DashboardPage dashboard = new DashboardPage(shop);

    // 1–2 Hover + click
    dashboard.hoverAccessoriesAndClickHomeAccessories();

    // iframe reloads after navigation
    page.locator("iframe#framelive").waitFor();
    shop = page.frame("framelive");
    dashboard = new DashboardPage(shop);

    // 3 Verify page
    dashboard.assertOnHomeAccessoriesPage();

    // 4 Sort
    dashboard.sortByNameAToZ();

    // 5 Verify 8 items
    dashboard.assertProductCount(8);

    // 6–7 Filter polyester → 3 items
    dashboard.checkPolyesterFilter();
    dashboard.assertProductCount(3);

    // 8–9 Uncheck → back to 8
    dashboard.uncheckPolyesterFilter();
    dashboard.assertProductCount(8);

    // 10–11 Wishlist (2nd & 3rd items)
    dashboard.addProductToWishlistByIndex(1);
    dashboard.assertWishlistNotification();

    dashboard.addProductToWishlistByIndex(2);
    dashboard.assertWishlistNotification();

    // 12–13 Cart (3rd, 5th, 7th)
    dashboard.addProductToCartByIndex(2);
    dashboard.addProductToCartByIndex(4);
    dashboard.addProductToCartByIndex(6);

    // 14 Verify cart count
    dashboard.assertCartCount(3);
  }
}
