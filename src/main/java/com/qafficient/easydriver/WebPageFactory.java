package com.qafficient.easydriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class WebPageFactory {

    private static WebDriver driver;

    public static WebDriver getDriver(){
        return driver;
    }

    public static WebDriver setDriver(WebDriver driver){
        return driver = driver;
    }

    public static void initElements(WebDriver driver, Object currentObj) {
        PageFactory.initElements(new PageFieldDecorator(driver,
                driver), currentObj);
    }

    public static void initElements(ElementLocatorFactory factory, Object currentObj) {
        PageFactory.initElements(factory, currentObj);
    }

}
