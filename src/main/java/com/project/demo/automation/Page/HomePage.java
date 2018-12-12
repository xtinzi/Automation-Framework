package com.project.demo.automation.Page;

import com.project.demo.automation.fileConstants.DriverConstants;
import com.project.demo.automation.fileConstants.LocationConstants;
import com.project.demo.automation.leadDBUtils.LoginDetail;
import com.project.demo.automation.utils.*;
import com.thoughtworks.selenium.SeleniumException;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.openqa.selenium.Keys.TAB;


public class HomePage extends AbstractPage<HomePage> {

    private String property;

    LoadProperties loadProperties = new LoadProperties();
    PageObjects pageObjects;
    public static File report_file;

    //Added Report_file_Smoke
    public static GenerateReports reportingModule_smoke;
    public static final String REPORT_DIRECTORY=LocationConstants.MAIN_FOLDER+"/Report/";

    private String mainWindowHandle = null;
    private String caseWindow = null;


    /*Constructor
    *@param WebDriver
    */
    public HomePage(WebDriver driver) throws AWTException, InterruptedException {
        super(driver);
    }
    /*
     * Retrieve the location of the datasheet
     * @return
     */
    public String dataSheetLocation()
    {
        String location = getDataSheetLocation();
        log.info("Datasheet Location: " + location);
        return location;
    }
    /*
    **Load method implements interface Page
     */
       public HomePage load() throws AWTException, InterruptedException {

        retrieveTestData = new RetrieveTestData(dataSheetLocation());

           if(getBrowser().equalsIgnoreCase(DriverConstants.IE)){
               Thread.sleep(5000);
               waitForPageToLoad();
               getDriver().get(getPlainURL(loadProperties, getLoginDetail()));

               //If alert is present, it means a log in is required. Else an automatic login occurred.
               if(isAlertPresent() == true) {
                   enterCredentials(loginDetail);
                   Thread.sleep(500);
               }
           }else {
              getDriver().get(getURLs(loadProperties, getLoginDetail()));

               log.info("Login Successful on " + getEnviroment());
               log.info("Automation test started on this environment : " + "\nOS : " + System.getProperty("os.name") +
                       "\nUser : " + System.getProperty("user.name") +
                       "\nBrowser  : " + getBrowser() +
                       "\nEnvironment  : " + getEnviroment() +
                       "\nURL : " + loadProperties.getValueFromPropFile(getEnviroment()));
           }
        return this;
    }

    public void explicitWait(int duration){
        try{
            log.info("Waiting for " + duration/1000 + " seconds.");
            Thread.sleep(duration);
            log.info("Done waiting.");
        }
        catch(InterruptedException ie){
            log.error("WaitForThread was interrupted.");
            log.error(ie.getMessage());
        }
    }

    public HomePage load(String url, String username, String password){


        retrieveTestData =new RetrieveTestData(SeleniumService.getEnvironmentProperties().getTestDatasheet());
        // Chrome v59 Deprecated credential injection.
        // First use the Injection URL - this will cache the credentials
        // then reload the url without credenials - this will load the page.
        getDriver().get(getURL(url,username,password));
        getDriver().get(url);

        log.info("Login Successful on " + url);
        log.info("Automation test started on this environment : " + "\nOS : " + System.getProperty("os.name") +
                "\nUser : " + System.getProperty("user.name"));
        return this;

    }

    /**
     *
     * @return loginDetail
     * this method loads login detail only when loginDetail is null
     */
    public  LoginDetail getLoginDetail(){
        if(loginDetail==null){
            loginDetail =retrieveTestData.getLoginDetail();
        }
        return  loginDetail;
    }

    public String getMainWindowHandle() {
        return mainWindowHandle;
    }

    public void setMainWindowHandle(String mainWindowHandle) {
        this.mainWindowHandle = mainWindowHandle;
    }

    public String getBrowser()
    {
        return getLoginDetail().getBrowser();
    }



    public String getEnviroment()
    {
        return getLoginDetail().getTestEnvironment();
    }

    /*
    ** Wait for a page to load
     */
    public void waitForPageToLoad(){
        waitForPageLoad();
    }

    /*
    **  Reporting changes
    **@param sheetNumber
    **@param columnNumber
    **@param rowUpTo
     */
    public List<String> retrieveColumn(Integer sheetNumber, Integer columnNumber, Integer rowUpTo){
        RetrieveTestData retrieveTestData = new RetrieveTestData(getDataSheetLocation());
        columnData = retrieveTestData.getColumns(sheetNumber, columnNumber, rowUpTo);
        return columnData;
    }
    /*
    ** Load properties
    * @param key
    * @return
     */
    public String property(String key) {
        property = loadProperties.getValueFromPropFile(key);
        return property;
    }

    /*
    *** Maximise browser window
     */
    public void maximiseWindow() {
        getDriver().manage().window().maximize();
    }

    /*
    * Select the first value from an interactive menu item on a default frame
    * @param identifier main menu
    * @param identifier sub menu
    */

    public void selectNonInteractiveMenuItems(String pageName, String elementName) {

        try {
            getDriver().switchTo().defaultContent();
            findElement(pageObjects.getByElement(pageName, elementName)).click();
            log.info("Selected non interactive menu item: <" + elementName + ">");
        }
        catch (UnhandledAlertException ae){
            log.error("Pop up displayed during an attempt to select an element: <" + elementName + ">");
            acceptAlert();
        }
        catch (Exception e){
            log.error("Unable to select non interactive menu item: <" + elementName + ">");
        }
    }


    /*
     * Select the first value from an interactive menu item
     * @param pageName
     * @param elementName
     */
    public void selectNonInteractiveMenuItem(String pageName, String elementName) {
        try{
            WebElement element = findElement(pageObjects.getByElement(pageName, elementName));
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", element);

        log.info("Selected non interactive menu item: <" + elementName + ">");
        }
        catch (UnhandledAlertException ae){
            log.error("Pop up displayed during an attempt to select an element: <" + elementName + ">");
            acceptAlert();
        }
        catch (Exception e){
            log.error("Unable to select non interactive menu item: <" + elementName + ">");
        }
    }

    public void selectNonInteractiveItem(String pageName, String elementName) {
        try{
            findElement(pageObjects.getByElement(pageName, elementName)).click();
            log.info("Selected non interactive  item: <" + elementName + ">");
        }
        catch (UnhandledAlertException ae){
            log.error("Pop up displayed during an attempt to select an element: <" + elementName + ">");
            acceptAlert();
        }
        catch (Exception e){
            log.error("Unable to select non interactive menu item: <" + elementName + ">");
        }
    }
    public void selectNonInteractiveMenuItem(String pageName, String elementName,boolean isInternetExplorer) {
        WebElement element;
        try{
            if(isInternetExplorer) {
                element = findInvisibleElement(pageObjects.getByElement(pageName, elementName));

            }
            else{
                element = findElement(pageObjects.getByElement(pageName, elementName));

            }
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
            log.info("Selected non interactive menu item: <" + elementName + ">");
        }
        catch (UnhandledAlertException ae){
            log.error("Pop up displayed during an attempt to select an element: <" + elementName + ">");
            acceptAlert();
        }
        catch (Exception e){
            log.error("Unable to select non interactive menu item: <" + elementName + ">");
        }
    }
    /*
       * Scroll down from a specific elements location
       * @param pageName
       * @param elementName
       */
    public void scrollDown(String pageName, String elementName) throws InterruptedException {
        WebElement element = findElement(pageObjects.getByElement(pageName, elementName));
        Thread.sleep(1500);
        Actions action = new Actions(driver);
        action.click(element).perform();
        action.sendKeys(Keys.PAGE_DOWN).perform();
    }

    /*
     * select second value from an interactive menu item
     * @param pageName
     * @param elementName
     * @param subElementName
     */
    public void selectInteractiveMenuItems(String elementName, String pageName, String subElementName) throws InterruptedException {
        getDriver().switchTo().defaultContent();
        findElement(pageObjects.getByElement(elementName, pageName)).click();
        log.info("Selected interactive menu item <" + pageName + "> on page <" + elementName + ">");
        log.info("Selecting interactive sub menu item <" + subElementName + "> on menu item <" + pageName + ">");
        pageName = subElementName;
        Thread.sleep(2000);
        waitForPageToLoad();
        findElement(pageObjects.getByElement(elementName, pageName)).click();

    }

    /* Select an option from a dropdown list
    * @param pageName
    * @param elementName
    * @param locationOffirstElementOnDropDown
    * @param optionFromDropDown
     */
    public void selectOptionFromList(String pageName, String elementName, String locationOfFirstElementOnDropDown, String optionFromDropDown)
    {
        // Use PageName, LabelName, SelectList, OptionFromDropDown
        try {
            findElement(pageObjects.getByElement(pageName, elementName)).click();
            Select selectOption = new Select(findInvisibleElement(pageObjects.getByElement(pageName, locationOfFirstElementOnDropDown)));
            selectOption.selectByVisibleText(optionFromDropDown);
        }catch (Exception e) {
            log.error("Could not select a value on <" + locationOfFirstElementOnDropDown + "> dropdown. Please verify if the supplied data is correct");
            throw new SeleniumException(e.getMessage());
        }

    }

    /* Select an option from a dropdown list
        * @param pageName
        * @param elementName
        * @param optionFromDropDown
     */
    public void selectOptionFromList(String pageName, String elementName, String optionFromDropDown) {

        WebElement element = findElement(pageObjects.getByElement(pageName, elementName));
        element.click();
        try {
            findElementOnPage(optionFromDropDown);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
    public void scrollToBottomOfPage(){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("scroll(0, 400);");
    }
    public boolean isOptionOnTheList(String pageName, String elementName, String optionFromDropDown) {

        boolean isOnTheList = false;
        WebElement element = findElement(pageObjects.getByElement(pageName, elementName));
        element.click();
        try {
            isElementPresentOnDropdownList(optionFromDropDown);
            isOnTheList = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return isOnTheList;
    }


    /*
    * Switch back to a default frame
     */
    public void switchToDefaultFrame() {
        getDriver().switchTo().defaultContent();
    }


    public boolean isElementPresent(String id){
        try{
            getDriver().findElement(By.id(id));
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }

    public boolean isElementPresentOnDropdownList(String value){
        try{
            findElementOnPage(value);
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }


    //Return the attribute value of an element
    public String getStringValue(String pageName,String element){
        String stringValue = null;
        try {
             stringValue = findElement(pageObjects.getByElement(pageName, element)).getText();
        }
        catch (UnhandledAlertException ue){
            log.info("Alert was present during: Get String Value");
            acceptAlert();
            log.info("Accepted the alert.");
        }
        catch (Exception e){
            log.info("Something went wrong when trying to get the string value: ");
            log.info(e.getMessage());
        }
        return stringValue;
    }

    //Return the attribute value of an element, this method doesn't wait for an element to be visible
    public String getTextValue(String pageName,String element){
        String textValue = null;
        try{
            textValue = findInvisibleElement(pageObjects.getByElement(pageName, element)).getText();
        }
        catch (UnhandledAlertException ue){
            log.info("Alert was present during: Get Text Value");
            acceptAlert();
            log.info("Accepted the alert.");
        }
        catch (Exception e){
            log.info("Something went wrong when trying to get the string value: ");
            log.info(e.getMessage());
        }
        return textValue;
    }

      /**
     * This method returns an element's attribute such as title,value,innerHTML
     * @param pageName
     * @param element
     * @param attribute
     * @return
     */
    public String getAttribute(String pageName,String element,String attribute){
        return findInvisibleElement(pageObjects.getByElement(pageName, element)).getAttribute(attribute);
    }

    public void clearField(String pageName, String elementName){

        WebElement element = null;
        try {
            element = findInvisibleElement(pageObjects.getByElement(pageName, elementName));
            element.clear();
            log.info("Clearing Filed: <" + elementName + ">");

        } catch (Exception e) {
            log.error("Failed to Clear Element: <" + elementName + ">");
        }
    }


    /*
    * Enter data on a textfield
    * @param pageName
    * @param elementName
    * @param dataFromSheet
    *
     */
    public void enterDataFromExcel(String pageName, String elementName, String dataFromSheet) {

        WebElement element = null;
        try {
            clearField(pageName,elementName);
            element = findElement(pageObjects.getByElement(pageName, elementName));
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", element);
            driver.switchTo().activeElement();
            element.sendKeys(dataFromSheet);
            log.info("Entering data from a datasheet on Element: <" + elementName + ">");

        } catch (Exception e) {
            log.error("Failed entering data from a datasheet on Element: <" + elementName + ">");
        }
    }

    public void clearInputBox(String pageName, String elementName){
        WebElement element;
        try {

            element = findElement(pageObjects.getByElement(pageName, elementName));
            element.sendKeys(Keys.BACK_SPACE);
        }catch (Exception ex){
            log.error("Failed to clear field: <"+elementName+ ">");

        }
    }

    public String getTextOnLockedFields(String pageName, String elementName)
    {
        String textOnField = null;
        try {
            textOnField = findElement(pageObjects.getByElement(pageName, elementName)).getText();
        } catch (Exception e) {
            log.error("Failed to rerieve text from : <" + elementName + ">");
        }
        return textOnField;
    }



    public void sendKeyEnter()
    {
        switch (TAB) {
    }
    }

    public void sendKeysEnter(String pageName,String elementName)
    {
        WebElement element= null;
        try {
            element = findElement(pageObjects.getByElement(pageName, elementName));
            element.sendKeys(Keys.ENTER);
        }
            catch (Exception e) {
                log.error("Failed to send key ENTER");
            }
    }

    public void sendKeyTAB(String pageName,String elementName)
    {
        WebElement element= null;
        try {
            element = findElement(pageObjects.getByElement(pageName, elementName));
            element.sendKeys(Keys.TAB);
        }
    catch (Exception e) {
        log.error("Failed to send key TAB");
    }


    }
    /*
        * Populate data and search
        * This is for search menus
     */
    public void searchAndEnterData(String pageName, String elementName,String iconName, String dataFromSheet) throws InterruptedException {

        WebElement element= null;
        try {
            element = findElement(pageObjects.getByElement(pageName, elementName));
            Thread.sleep(2000);
            element.sendKeys(dataFromSheet);
            log.info("Entering data from a datasheet on Element: <" + elementName + ">");

        } catch (Exception e) {
            log.error("Failed entering data from a datasheet on Element: <" + elementName + ">");
        }

        sendKeyTAB(pageName, elementName);
        Thread.sleep(2000);
        sendKeysEnter(pageName, elementName);
    }




        public boolean isElementPresent(String pageName, String linkText){
        boolean isVisible = false;
        try {
            getDriver().findElement(pageObjects.getByElement(pageName, linkText));

            isVisible = true;
        }
        catch (NoSuchElementException e){
            isVisible = false;
        }
        return isVisible;
    }

    public boolean isVisible(String pageName, String linkText)
    {
        boolean isVisible = false;
        try {

            if (isElementPresent(pageName, linkText)) {
                isVisible = getDriver().findElement(pageObjects.getByElement(pageName, linkText)).isDisplayed();
            }

        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return isVisible;
    }



    public void mouseClick(WebElement element ){
            Actions action = new Actions(driver);
            action.moveToElement(element).doubleClick().perform();
    }

    public void switchFocusTo(String pageName, String element) throws Exception{
        WebElement webElement = null;
        try {
            webElement = findElement(pageObjects.getByElement(pageName, element));
            new Actions(driver).moveToElement(webElement).click().perform();
        }
        catch (Exception e)
        {
            log.error("switchFocusTo feature: Unable to select element : " + element);
            throw new Exception("switchFocusTo feature: Unable to select element : " + element);
        }
    }

    public void switchFocusTo(WebElement element){
        try {
            new Actions(driver).moveToElement(element).click().perform();
        }catch (Exception e)
        {
            log.error("switchFocusTo feature: Unable to select element : " +element.getText());
        }
    }
    /*
    * Find element on a web page
    * @param linkText
 */
    public void findElementOnPage(String label) {
        List<WebElement> anchors = findElements(By.tagName("option"));
        Iterator<WebElement> i = anchors.iterator();
            while (i.hasNext()) {
                WebElement anchor = i.next();
                log.error(anchor.getText() + "    "+ anchor.getTagName());
                    if (anchor.getText().contains(label)) {
                    anchor.click();

                    log.info("Selected name from dropdown" + label);
                    break;
                }
            }
    }

    public void findElementOnPage(String tagname, String label){
        List<WebElement> anchors = findElements(By.tagName(tagname));
        Iterator<WebElement> i = anchors.iterator();
        while (i.hasNext()) {
            WebElement anchor = i.next();
            log.info(anchor.getText() + "    "+ label);
            if (anchor.getText().contains(label)) {

                JavascriptExecutor executor = (JavascriptExecutor)driver;
                executor.executeScript("arguments[0].click();", anchor);

                log.info("Selected name from dropdown" + label);
                break;
            }
        }
    }


    /*
      * Find element on a web page
      * @param linkText
   */
    public void getElementOnPage(String  linkText)
    {
        List<WebElement> anchors = findElements(By.tagName("a"));
        Iterator<WebElement> i = anchors.iterator();

        while (i.hasNext()) {
            WebElement anchor = i.next();

            if (anchor.getText().contains(linkText)) {
                log.info("Parsed text: " + anchor.getText());
                log.info("Retrieved text from 'title' tag: " + anchor.getAttribute("title"));

                if (linkText.contains(anchor.getAttribute("title"))) {

                    List<WebElement> innerlinks = findElements(By.linkText(anchor.getAttribute("title")));
                    Iterator<WebElement> in = innerlinks.iterator();

                    while (in.hasNext()) {
                        WebElement innerlink = i.next();
                        if (innerlink.getText().contains(linkText)) {
                            innerlink.click();
                            log.info("Selected name from dropdown" + linkText);
                        }
                        break;
                    }
                }else {
                    findElement(By.linkText(anchor.getText())).click();
                }
                break;
            }
        }
    }





    public void findAndSelectElementUsingPartialText(String searchString, String elementType){
        // Method uses XPath wildcard search.
        // Provide Element Type. E.g: span,a,td,tr,div etc.
        // Provide partial containing text of element.
        try {
            log.info("Attempting to find element with partial text: " + searchString + ".");
            WebElement searchResult = getDriver().findElement(By.xpath("//" + elementType + "[contains(text(), '" + searchString + "')]"));
            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            log.info("Clicked element with partial text: " + searchString + ".");
            executor.executeScript("arguments[0].click()", searchResult);
            Thread.sleep(500);
            // Hyperlink elements only require one click.
            if (searchResult.isDisplayed()) {
                if (!elementType.equalsIgnoreCase("a")) {
                    log.info("Attempting to open element with partial text: " + searchString + ".");
                    searchResult.sendKeys(Keys.ENTER);
                    log.info("Sent ENTER KEY to element with partial text: " + searchString + ".");
                }
            }
        } catch (Exception e) {
            log.error("Unable to find/select the requested search string: " + searchString + ".");
        }
    }

    public void findElementOnSearch(String pageName,String searchResult,String search) {
        //First make sure that the result table is visible
        if (!findElements(pageObjects.getByElement(pageName, searchResult)).isEmpty()) {
            WebElement baseTable = findElement(pageObjects.getByElement(pageName, searchResult));
            List<WebElement> listRows = baseTable.findElements(By.tagName("a"));
            for (WebElement element : listRows) {
                try {
                    String lines[] = element.getText().split("\\r?\\n");

                    if (lines.length > 0) {
                        for(String myValue:lines) {
                            if(myValue.trim().startsWith(search)){
                                if(myValue.trim().equalsIgnoreCase(search)) {
                                    //Now here, clicking the link hyperlink
                                    mouseClick(element);
                                    return;
                                }
                                else{
                                    throw new SeleniumException("Could not find "+ search + " on the search result");
                                }
                            }
                        }
                    }

                } catch (UnhandledAlertException ux) {
                    log.error(ux.getMessage());
                }
            }
        }

    }



    public boolean isInArray(String value,String [] stringArray){
        boolean inArray=false;
        for(int i=0;i<stringArray.length;i++){
            if(value.equals(stringArray[i])){
                inArray =true;
            }
        }
        return inArray;
    }




    public void acceptAlert() {
        Alert alert = driver.switchTo().alert();
        log.error("An alert was present" );
        log.error("Alert Text: " + alert.getText());
        alert.accept();
    }

    @Override
    public boolean isAlertPresent()
    {
        boolean isPresent = false;
        try {
            driver.switchTo().alert();
            isPresent = true;

        }
        catch (NoAlertPresentException e)
        {
            log.info("Alert is not visible");
        }
        return isPresent;
    }




        public void selectOptionFromList(WebElement element,String optionFromDropDown) {
        element.click();
        try {
            WebElement elementsOnElement = findElementsOnElement(element,"option",optionFromDropDown);
            elementsOnElement.click();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public WebElement findElementsOnElement(WebElement webElement,String tagName, String label){
        List<WebElement> anchors = webElement.findElements(By.tagName(tagName));
        Iterator<WebElement> i = anchors.iterator();
        while (i.hasNext()) {
            WebElement anchor = i.next();
            log.info(anchor.getText() + "    "+ anchor.getTagName());
            if (anchor.getText().equalsIgnoreCase(label)) {
                return anchor;
            }
        }
        return null;
    }


    public WebElement getWebElement(String pageName, String  elementName) {
        WebElement webElement = null;
        try {
            webElement = findElement(getPageObjects().getByElement(pageName, elementName));
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return webElement;
    }


    public PageObjects getPageObjects() {
        return pageObjects;
    }

    public void setPageObjects(PageObjects pageObjects) {
        this.pageObjects = pageObjects;
    }

}