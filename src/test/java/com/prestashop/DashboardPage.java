package com.prestashop;

import com.microsoft.playwright.Frame;
import static org.junit.jupiter.api.Assertions.*;

public class DashboardPage {

  private final Frame shop;

  public DashboardPage(Frame shop) {
    this.shop = shop;
  }

  /* ---------- Navigation ---------- */

  public void hoverAccessoriesAndClickHomeAccessories() {

	  // top menu Accessories
	  shop.locator("#category-6 > a.dropdown-item[data-depth='0']").hover();

	  // submenu Home Accessories
	  shop.locator("#category-8 > a.dropdown-item[data-depth='1']").click();

	  // wait for navigation confirmation
	  shop.locator("h1:has-text('Home Accessories')").waitFor();
	  System.out.println("Accessories matches: " + shop.locator("h1:has-text('Home Accessories')").count());

	  
	}


  public void assertOnHomeAccessoriesPage() {
	  shop.locator("h1:has-text('Home Accessories')").waitFor();
    assertTrue(
    		shop.locator("h1:has-text('Home Accessories')").count() > 0,
      "Not on Home Accessories page"
    );
  }

  /* ---------- Sorting ---------- */

  public void sortByNameAToZ() {
	  // Open the sort dropdown
	  shop.locator("button.select-title").click();

	  // Click "Name, A to Z"
	  shop.locator("a.select-list:has-text('Name, A to Z')").click();

	}


  /* ---------- Product grid helpers ---------- */

  public int getVisibleProductsCount() {
	  shop.locator("div.products").waitFor();
	  shop.locator("article.product-miniature").first().waitFor();
	  return shop.locator("article.product-miniature").count();
	}

	public void assertProductCount(int expected) {
	  System.out.println("total products: " + getVisibleProductsCount());

	  org.junit.jupiter.api.Assertions.assertEquals(
	      expected,
	      getVisibleProductsCount(),
	      "Unexpected product count"
	  );
	}



	/* ---------- Filters ---------- */

	public void checkPolyesterFilter() {
		  
		  // Click the Polyester filter link (this navigates the iframe)
		  shop.locator("#search_filters").waitFor();
//Polyester
		  //shop.locator("a.select-list:has-text('Polyester')").click();
		  System.out.println("total products: " + shop.locator(
				    "#search_filters section.facet[data-name='Composition'] a.js-search-link:has-text('Polyester')"
				));

		  // Click the checkbox via label (checkbox itself is hidden)
		  //rregullo selektoret 
		  shop.locator(
				    "#search_filters section.facet[data-name='Composition'] a.js-search-link:has-text('Polyester')"
				).first().click();
		  
		}


	public void uncheckPolyesterFilter() {
	  // Clicking again removes the filter (PrestaShop behavior)
	  shop.locator(
	      "#search_filters section[data-name='Composition'] a.js-search-link:has-text('Polyester')"
	  ).click();
	}

  /* ---------- Wishlist ---------- */

		public void addProductToWishlistByIndex(int index) {
			  var product = shop.locator("article.product-miniature").nth(index);
			  product.scrollIntoViewIfNeeded();
			  product.locator("button.wishlist-button-add").click();
			}

			public void assertWishlistNotification() {
			  shop.locator("text=Product added").first().waitFor();
			}


  /* ---------- Cart ---------- */

			public void addProductToCartByIndex(int index) {
				  var product = shop.locator("article.product-miniature").nth(index);
				  product.scrollIntoViewIfNeeded();

				  // exact HTML selector
				  product.locator("a.quick-view.js-quick-view[data-link-action='quickview']")
				         .click();

				  // Modal iframe
				  shop.page().locator(".modal iframe").waitFor();
				  var modal = shop.page().frameLocator(".modal iframe");

				  modal.locator("button:has-text('Add to cart')").click();

				  shop.page()
				      .locator("text=Product successfully added to your shopping cart")
				      .waitFor();

				  shop.page()
				      .locator("button:has-text('Continue shopping')")
				      .click();
				}

			public void assertCartCount(int expected) {
				  shop.locator("span.cart-products-count").waitFor();

				  for (int i = 0; i < 10; i++) {
				    String text = shop.locator("span.cart-products-count").innerText();
				    if (text.contains(String.valueOf(expected))) break;
				    shop.page().waitForTimeout(300);
				  }

				  String cartText = shop.locator("span.cart-products-count").innerText();
				  org.junit.jupiter.api.Assertions.assertTrue(
				      cartText.contains(String.valueOf(expected)),
				      "Cart count mismatch: " + cartText
				  );
				}



}
