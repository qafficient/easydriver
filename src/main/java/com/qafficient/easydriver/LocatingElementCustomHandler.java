package com.qafficient.easydriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class LocatingElementCustomHandler implements InvocationHandler {

    private final ElementLocator locator;
    private final WebDriver driver;

    public LocatingElementCustomHandler(WebDriver driver, ElementLocator locator) {
        this.locator = locator;
        this.driver = driver;
    }

    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        WebElement element = locator.findElement();
        WebPageElement pageElement = new WebPageElementImpl();
        pageElement.setWebDriver(driver);
        pageElement.setRootElement(element);
        try {
            return method.invoke(pageElement, objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}

