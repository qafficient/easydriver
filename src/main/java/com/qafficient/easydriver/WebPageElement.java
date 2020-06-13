package com.qafficient.easydriver;

import com.google.inject.ImplementedBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

@ImplementedBy(WebPageElementImpl.class)
public interface WebPageElement extends WebElement {

    void setRootElement(WebElement element);

    void setWebDriver(WebDriver driver);

    // Do not Use above methods

    void click(boolean isClickableWait);
    void clearAndType(String text);
    void jsClick();
    void actionClick();
    void clickWithThreadWait(long milisecs);
    String getBackGroundColor();
    void scrollClick(boolean jsClickReqd);
    WebPageElement getParent();
    WebPageElement scrollInToView();
    WebPageElement moveToElement(WebPageElement element);
    WebElement getRawElement();
    Actions getActions();
    WebPageElement waitForElement(int seconds);
    String getTitle();
    String getHref();
    List<WebPageElement> findPageElements(By by);
    WebPageElement findPageElement(By by);
    void selectByText(String text);
    void selectByIndex(int index);
    void rightClick();
    boolean isVisibleByJQuery();
    String getAttributeByJQuery(String attribute);
    String getTextByJQuery();
    void setTextByJs(String value);
    String getAttributeByJs(String attributeName);
    Select getSelect();
    void setAttributeByJs(String value);
    void selectByValue(String value);
    List<WebPageElement> getAllSelectedOptions();
    List<WebPageElement> getAllOptions();
    WebPageElement getFirstSelectedOption();
    void deSelectByIndex(int index);
    void deSelectByValue(String value);
    void deSelectByText(String text);
    boolean isMultipleSelectionAllowed();

}
