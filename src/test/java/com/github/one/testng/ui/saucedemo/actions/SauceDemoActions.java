package com.github.one.testng.ui.saucedemo.actions;

import static java.text.MessageFormat.format;

import com.github.one.actions.device.AndroidDeviceActions;
import com.github.one.actions.device.DeviceActions;
import com.github.one.actions.drivers.NavigateActions;
import com.github.one.actions.drivers.WindowActions;
import com.github.one.actions.elements.*;
import com.github.one.enums.PlatformType;
import com.github.one.enums.SwipeDirection;
import com.github.one.manager.ParallelSession;
import com.github.one.testng.ui.saucedemo.pages.*;
import io.appium.java_client.android.nativekey.AndroidKey;

public class SauceDemoActions {
    private static final String       URL = "https://www.saucedemo.com";
    private final PlatformType platformType;

    public SauceDemoActions () {
        this.platformType = ParallelSession.getSession ()
            .getPlatformType ();
    }

    public void verifyAddToCart () {
        if (this.platformType == PlatformType.WEB) {
            ClickableActions.withMouse (HomePage.homePage ().getAddToCartButton ()).click ();
        } else {
            FingerActions.withFinger (HomePage.homePage ().getAddToCartDragHandle ()).dragTo (HomePage.homePage ().getCartDropZone ());
        }

        ElementActions.onElement (HomePage.homePage ().getProductPrice ()).verifyText ()
            .isEqualTo ("$29.99");
        ElementActions.onElement (HomePage.homePage ().getShoppingCartCount ()).verifyText ()
            .isEqualTo ("1");

        verifyProductDetailPage ();
        verifyProductCartPage ();
    }

    public void verifyCheckoutStep1 () {
        ClickableActions.withMouse (CartPage.cartPage ().getCheckout ()).click ();
        if (this.platformType == PlatformType.WEB) {
            NavigateActions.navigate ().verifyUrl ()
                .isEqualTo (format ("{0}/checkout-step-one.html", URL));
            ElementActions.onElement (CheckoutPage.checkoutPage ().getTitle ()).verifyText ()
                .ignoringCase ()
                .isEqualTo ("CHECKOUT: YOUR INFORMATION");
        }

        TextBoxActions.onTextBox (CheckoutPage.checkoutPage ().getFirstName ()).enterText ("Wasiq");
        TextBoxActions.onTextBox (CheckoutPage.checkoutPage ().getLastName ()).enterText ("Bhamla");
        TextBoxActions.onTextBox (CheckoutPage.checkoutPage ().getZipCode ()).enterText ("12345");
        ClickableActions.withMouse (CheckoutPage.checkoutPage ().getContinueButton ()).click ();
    }

    public void verifyCheckoutStep2 () {
        if (this.platformType != PlatformType.WEB) {
            FingerActions.withFinger (CheckoutPage.checkoutPage ().getFinish ()).swipeTill (SwipeDirection.UP);
        }
        ClickableActions.withMouse (CheckoutPage.checkoutPage ().getFinish ()).click ();

        if (this.platformType != PlatformType.WEB) {
            ElementActions.onElement (CheckoutPage.checkoutPage ().getCompleteHeader ()).verifyText ()
                .isEqualTo ("THANK YOU FOR YOU ORDER");
        } else {
            NavigateActions.navigate ().verifyUrl ()
                .isEqualTo (format ("{0}/checkout-complete.html", URL));
            ElementActions.onElement (CheckoutPage.checkoutPage ().getTitle ()).verifyText ()
                .ignoringCase ()
                .isEqualTo ("CHECKOUT: COMPLETE!");
            ElementActions.onElement (CheckoutPage.checkoutPage ().getCompleteHeader ()).verifyText ()
                .ignoringCase ()
                .contains ("THANK YOU FOR YOUR ORDER");
        }

        ElementActions.onElement (CheckoutPage.checkoutPage ().getCompleteText ()).verifyText ()
            .isEqualTo ("Your order has been dispatched, and will arrive just as fast as the pony can get there!");
    }

    public void verifyLogin (final String userName, final String password) {
        verifyNavigateToSite ();
        TextBoxActions.onTextBox (LoginPage.loginPage ().getUsername ()).enterText (userName);
        TextBoxActions.onTextBox (LoginPage.loginPage ().getPassword ()).enterText (password);
        if (this.platformType == PlatformType.ANDROID && DeviceActions.onDevice ().isKeyboardVisible ()) {
            AndroidDeviceActions.onAndroidDevice ().pressKey (AndroidKey.BACK);
        }
        ClickableActions.withMouse (LoginPage.loginPage ().getLoginButton ()).click ();
        verifyLoggedIn ();
    }

    public void verifySignOut () {
        ClickableActions.withMouse (HomePage.homePage ().getMenuButton ()).click ();
        ClickableActions.withMouse (HomePage.homePage ().getLogout ()).click ();
        if (this.platformType == PlatformType.WEB) {
            NavigateActions.navigate ().verifyUrl ()
                .startsWith (URL);
        }
        ElementActions.onElement (LoginPage.loginPage ().getUsername ()).verifyIsDisplayed ()
            .isTrue ();
    }

    private void verifyLoggedIn () {
        if (this.platformType == PlatformType.WEB) {
            NavigateActions.navigate ().verifyUrl ()
                .isEqualTo (format ("{0}/inventory.html", URL));
            WindowActions.onWindow ().verifyTitle ()
                .isEqualTo ("Swag Labs");
        }
        ElementActions.onElement (HomePage.homePage ().getMenuButton ()).verifyIsDisplayed ()
            .isTrue ();
        ElementActions.onElement (HomePage.homePage ().getMenuButton ()).verifyIsEnabled ()
            .isTrue ();
    }

    private void verifyNavigateToSite () {
        if (this.platformType == PlatformType.WEB) {
            NavigateActions.navigate ().to (URL);
            NavigateActions.navigate ().verifyUrl ()
                .startsWith (URL);
        }
    }

    private void verifyProductCartPage () {
        ClickableActions.withMouse (HomePage.homePage ().getShoppingCart ()).click ();
        if (this.platformType == PlatformType.WEB) {
            NavigateActions.navigate ().verifyUrl ()
                .isEqualTo (format ("{0}/cart.html", URL));
        }
        ElementActions.onElement (CartPage.cartPage ().getCheckout ()).verifyIsDisplayed ()
            .isTrue ();
    }

    private void verifyProductDetailPage () {
        ClickableActions.withMouse (HomePage.homePage ().productItem ("Sauce Labs Backpack")).click ();
        if (this.platformType == PlatformType.WEB) {
            NavigateActions.navigate ().verifyUrl ()
                .startsWith (format ("{0}/inventory-item.html?id=", URL));
        }
        ElementActions.onElement (ProductDetailsPage.productDetailsPage ().getContainer ()).verifyIsDisplayed ()
            .isTrue ();
        if (this.platformType != PlatformType.WEB) {
            FingerActions.withFinger ().swipe (SwipeDirection.UP);
            FingerActions.withFinger ().swipe (SwipeDirection.DOWN);
            FingersActions.withFingers (ProductDetailsPage.productDetailsPage ().getImage ()).zoomIn ();
            FingersActions.withFingers (ProductDetailsPage.productDetailsPage ().getImage ()).zoomOut ();
        }
    }
}
