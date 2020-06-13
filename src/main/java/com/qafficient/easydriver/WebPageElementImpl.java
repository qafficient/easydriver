package com.qafficient.easydriver;

import com.qafficient.easydriver.utility.ThreadUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

class WebPageElementImpl implements WebPageElement{

    protected WebElement rootElement;
    protected WebDriver webDriver;
    private WebDriverWait webDriverWait;
    private JavascriptExecutor javascriptExecutor;
    private Actions actions;

    public void setRootElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait = new WebDriverWait(webDriver, 10);
        this.javascriptExecutor = (JavascriptExecutor) webDriver;
        this.actions = new Actions(webDriver);
    }

    private void waitForElementVisibility() {
        webDriverWait.until(ExpectedConditions.visibilityOf(this.rootElement));
    }

    private void waitForElementVisibility(int seconds) {
        this.webDriverWait = (new WebDriverWait(webDriver, seconds));
        waitForElementVisibility();
    }

    /***
     * Waits for the element to be visible
     * and after that clicks on it.
     */

    @Override
    public void click() {
        waitForElementVisibility();
        this.rootElement.click();
    }

    /***
     * Waits for the element to be visible
     * and calls selenium submit method after that.
     */

    @Override
    public void submit() {
        waitForElementVisibility();
        this.rootElement.submit();
    }

    /***
     * Waits for the element to be visible
     * and performs selenium send keys on the element.
     * @param keysToSend
     */

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        waitForElementVisibility();
        this.rootElement.sendKeys(keysToSend);
    }

    /***
     * Waits for the element to be visible
     * and calls selenium clear text field method.
     */

    @Override
    public void clear() {
        waitForElementVisibility();
        this.rootElement.clear();
    }

    @Override
    public String getTagName() {
        waitForElementVisibility();
        return this.rootElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        waitForElementVisibility();
        return this.rootElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        waitForElementVisibility();
        return this.rootElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        waitForElementVisibility();
        return this.rootElement.isEnabled();
    }

    @Override
    public String getText() {
        waitForElementVisibility();
        return this.rootElement.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return this.rootElement.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return this.rootElement.findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        try {
            return this.rootElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public Point getLocation() {
        return this.rootElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return this.rootElement.getSize();
    }

    @Override
    public Rectangle getRect() {
        return this.rootElement.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return this.rootElement.getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return this.rootElement.getScreenshotAs(target);
    }

    @Override
    public void click(boolean isClickableWait) {
        if (isClickableWait) {
            webDriverWait.until(ExpectedConditions.elementToBeClickable(this.rootElement));
        }
        this.click();
    }

    /***
     * Waits for the element visibility and then clears the text field and set texts in it.
     * @param text Text to be set in the Text Field
     */

    @Override
    public void clearAndType(String text) {
        waitForElementVisibility();
        this.rootElement.clear();
        this.rootElement.sendKeys(text);
    }

    /***
     * Performs click operation on the element using Java Script Executor.
     */

    @Override
    public void jsClick() {

        this.javascriptExecutor.executeScript(
                "arguments[0].click();", this.rootElement);
    }

    /***
     * Performs click operation on the element using Web Driver actions.
     */
    @Override
    public void actionClick() {
        actions.click();
    }

    @Override
    public void clickWithThreadWait(long miliSecs) {
        click();
        ThreadUtil.sleep(miliSecs);
    }

    @Override
    public String getBackGroundColor() {
        return getCssValue("background-color");
    }

    /***
     * Scroll to the element and performs click on the element.
     * If jsClickReqd is true then click operation performed using Java Script.
     * @param jsClickReqd boolean (True/False)
     */
    @Override
    public void scrollClick(boolean jsClickReqd) {
        scrollInToView();
        if(jsClickReqd){
            jsClick();
        }else{
            click();
        }
    }


    /***
     * Returns the parent element of the current element.
     * @return GuruPageElement
     */
    @Override
    public WebPageElement getParent() {
        WebPageElement pageElement = new WebPageElementImpl();
        pageElement.setRootElement(this.rootElement.findElement(By.xpath("..")));
        pageElement.setWebDriver(webDriver);
        return pageElement;
    }

    @Override
    public WebPageElement scrollInToView() {
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(false);", this.rootElement);
        return this;
    }

    @Override
    public WebPageElement moveToElement(WebPageElement element) {
        waitForElementVisibility();
        actions.moveToElement(element);
        return this;
    }

    @Override
    public WebElement getRawElement() {
        return this.rootElement;
    }

    @Override
    public Actions getActions() {
        waitForElementVisibility();
        return this.actions;
    }

    @Override
    public WebPageElement waitForElement(int seconds) {
        waitForElementVisibility(seconds);
        return this;
    }

    @Override
    public String getTitle() {
        return this.rootElement.getAttribute("title").trim();
    }

    @Override
    public String getHref() {
        return this.rootElement.getAttribute("href").trim();
    }

    @Override
    public List<WebPageElement> findPageElements(By by) {
        return convertToWebPageElements(findElements(by));
    }

    @Override
    public WebPageElement findPageElement(By by) {
        return convertToWebPageElements(findElements(by)).get(0);
    }

    @Override
    public void selectByText(String text) {
        getSelect().selectByVisibleText(text);
    }

    @Override
    public void selectByIndex(int index) {
        getSelect().selectByIndex(index);
    }

    @Override
    public void selectByValue(String value) {
        getSelect().selectByValue(value);
    }

    @Override
    public List<WebPageElement> getAllSelectedOptions() {
        return convertToWebPageElements(getSelect().getAllSelectedOptions());
    }

    @Override
    public List<WebPageElement> getAllOptions() {
        return convertToWebPageElements(getSelect().getOptions());
    }

    @Override
    public WebPageElement getFirstSelectedOption() {
        return convertToWebPageElement(
                getSelect().getFirstSelectedOption());

    }

    @Override
    public void deSelectByIndex(int index) {
        getSelect().deselectByIndex(index);
    }

    @Override
    public void deSelectByValue(String value) {
        getSelect().deselectByValue(value);

    }

    @Override
    public void deSelectByText(String text) {
        getSelect().deselectByVisibleText(text);
    }

    @Override
    public boolean isMultipleSelectionAllowed() {
        return getSelect().isMultiple();
    }


    @Override
    public void rightClick() {
        getActions().contextClick(this.rootElement);
    }

    @Override
    public Select getSelect() {
        waitForElementVisibility();
        return new Select(this.rootElement);
    }

    @Override
    public boolean isVisibleByJQuery() {
        try {
            return (boolean) javascriptExecutor.executeScript(
                    "return jQuery(arguments[0]).is(\":visible\");", this.rootElement);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getAttributeByJQuery(String attribute) {
        return (String) javascriptExecutor.executeScript(
                "return jQuery(arguments[0]).attr(\"" + attribute + "\");", this.rootElement);
    }

    public String getTextByJQuery() {
        return (String) javascriptExecutor.executeScript(
                "return jQuery(arguments[0]).text();", this.rootElement);
    }

    public void setTextByJs(String value) {
        javascriptExecutor.executeScript(
                "arguments[0].setAttribute('value', '" + value + "');", this.rootElement);
    }

    public String getAttributeByJs(String attributeName) {

        try {
            String value = (String) javascriptExecutor
                    .executeScript("return arguments[0].getAttribute(\"" + attributeName + "\")", this.rootElement);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public void setAttributeByJs(String value) {
        javascriptExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')",
                this.rootElement);
    }

    private List<WebPageElement> convertToWebPageElements(List<WebElement> elements) {
        List<WebPageElement> guruPageElements = new ArrayList<>();
        elements.stream().forEach(d ->
                {
                    guruPageElements.add(convertToWebPageElement(d));
                }

        );
        return guruPageElements;
    }

    private WebPageElement convertToWebPageElement(WebElement element){
        final WebPageElement pageElement = new WebPageElementImpl();
        pageElement.setWebDriver(webDriver);
        pageElement.setRootElement(element);
        return pageElement;
    }



}
