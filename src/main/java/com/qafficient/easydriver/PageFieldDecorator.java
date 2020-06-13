package com.qafficient.easydriver;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

class PageFieldDecorator implements FieldDecorator {

    private final DefaultFieldDecorator defaultFieldDecorator;

    private final SearchContext searchContext;
    private final WebDriver webDriver;


    public PageFieldDecorator(SearchContext searchContext, WebDriver webDriver) {
        this.searchContext = searchContext;
        this.webDriver = webDriver;
        defaultFieldDecorator = new DefaultFieldDecorator(new DefaultElementLocatorFactory(searchContext));
    }

    public Object decorate(ClassLoader loader, Field field) {
        if (WebPageElement.class.isAssignableFrom(field.getType())
                && isFieldFindable(field)) {
            return proxyForLocator(loader, getLocator(field));
        } else if (List.class.isAssignableFrom(field.getType()) &&
                field.getGenericType().getTypeName().contains("WebPageElement")
                && isFieldFindable(field)) {
            ElementLocator locator = new DefaultElementLocatorFactory(searchContext).createLocator(field);
            if (locator == null) {
                return null;
            }
            return proxyForListLocator(loader, locator);
        } else {
            return defaultFieldDecorator.decorate(loader, field);
        }
    }

    private ElementLocator getLocator(Field field) {
        return new DefaultElementLocatorFactory(searchContext).createLocator(field);
    }

    protected List<WebPageElement> proxyForListLocator(ClassLoader loader, ElementLocator locator) {
        InvocationHandler handler = new LocatingElementCustomListHandler(webDriver, locator);

        List<WebPageElement> proxy;
        proxy = (List<WebPageElement>) Proxy.newProxyInstance(
                loader, new Class[]{List.class}, handler);
        return proxy;
    }

    protected WebPageElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        InvocationHandler handler = new LocatingElementCustomHandler(webDriver, locator);

        WebPageElement proxy;
        proxy = (WebPageElement) Proxy.newProxyInstance(
                loader, new Class[]{WebPageElement.class}, handler);
        return proxy;
    }

    private boolean isFieldFindable(Field field) {
        if (field.getAnnotation(FindBy.class) == null &&
                field.getAnnotation(FindBys.class) == null &&
                field.getAnnotation(FindAll.class) == null) {
            return false;
        }
        return true;
    }

}
