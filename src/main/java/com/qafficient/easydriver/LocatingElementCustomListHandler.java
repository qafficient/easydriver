package com.qafficient.easydriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LocatingElementCustomListHandler implements InvocationHandler {

    private final ElementLocator locator;
    private final WebDriver driver;

    public LocatingElementCustomListHandler(WebDriver driver, ElementLocator locator) {
        this.locator = locator;
        this.driver = driver;
    }

    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        List<WebElement> elements = locator.findElements();

        List<WebPageElement> pageElements = new ArrayList<>();
        for (WebElement elem : elements) {
            WebPageElement pageElement = new WebPageElementImpl();
            pageElement.setRootElement(elem);
            pageElement.setWebDriver(driver);
            pageElements.add(pageElement);
        }

        try {
            return method.invoke(pageElements, objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}

