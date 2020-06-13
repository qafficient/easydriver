package com.qafficient.easydriver.js;

import com.qafficient.easydriver.WebPageElement;
import com.qafficient.easydriver.WebPageFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class JavaScriptHelper {

    private static WebDriver driver =  WebPageFactory.getDriver();

    private static JavascriptExecutor jsDriver = (JavascriptExecutor) driver;

    public static Object exec(String code, Object... arguments) {
        return jsDriver.executeScript(code, arguments);
    }

    public static void click(WebPageElement element) throws WebDriverException {
        jsDriver.executeScript(
                "arguments[0].click();", element.getRawElement());
    }

    public static void windowFocus() {
        jsDriver.executeScript("window.focus();");
    }

    public static void waitForPageLoad(int waitTime) throws TimeoutException {
        new WebDriverWait(driver, waitTime).until(
                ExpectedConditions.jsReturnsValue("return document.readyState==complete;"));
    }

    public static void scrollDown(int height) {
        jsDriver.executeScript("window.scrollBy(0, " + height + ")");
    }

    public static void scrollTo(int height) {
        jsDriver.executeScript("window.scrollTo(0, " + height + ")");
    }

}
