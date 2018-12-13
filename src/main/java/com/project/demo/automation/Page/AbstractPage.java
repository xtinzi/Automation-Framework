package com.project.demo.automation.Page;

import com.project.demo.automation.fileConstants.DriverConstants;
import com.project.demo.automation.fileConstants.LocationConstants;
import com.project.demo.automation.leadDBUtils.LoginDetail;
import com.project.demo.automation.utils.*;
import com.google.common.base.Function;
import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.maven.shared.utils.io.FileUtils.copyFile;


public abstract class AbstractPage<T> implements Page {

    public WebDriver driver;
    private static final int TIME_OUT = 20;
    public static final String DOUBLE_FORWARD_SLASH = "//";
    protected static String HOME_URL = "";


    public static GenerateReports reportingModule;
    RetrieveTestData retrieveTestData = new RetrieveTestData(getDataSheetLocation());

    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AbstractPage.class);
    List<String> columnData;
    private int stepCounter;
    private long sleeptime = 2000;
    public LoginDetail loginDetail = null;
    private long millisecond = 10000000;


    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverUtil getUtil() {
        return new WebDriverUtil(getDriver());
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void close() {
        getDriver().close();
    }

    private static String[] splitURL(String URL) {
        String protocol = "";
        String endpoint = "";
        String testEnvironment[] = new String[2];
        List<String> holder = Arrays.asList(URL.split("://"));
        protocol = holder.get(0);
        endpoint = holder.get(1);
        testEnvironment[0] = protocol;
        testEnvironment[1] = endpoint;

        return testEnvironment;
    }
    /*
     ** Retrieve url from properties and get username + password from the data sheet(Note: supports chrome only)
     * @param loadProperties
     * @param loginDetail
     * @return url containing credentials
     */

    public static String getURLs(LoadProperties loadProperties, LoginDetail loginDetail) {

        String[] array = splitURL(loadProperties.getValueFromPropFile(loginDetail.getTestEnvironment()));
        String url = array[0] + ":" + DOUBLE_FORWARD_SLASH +
                loginDetail.getUserName() + ":" + loginDetail.getPassword() + "@" + array[1];

        HOME_URL = url;
        return url;
    }

    public String getURL(String url, String userName, String password) {

        String[] array = splitURL(url);
        String formattedUrl = array[0] + ":" + DOUBLE_FORWARD_SLASH +
                userName + ":" + password + "@" + array[1];

        HOME_URL = formattedUrl;
        return formattedUrl;
    }

    /*
     ** Retrieve url from properties and get username & password from the data sheet
     * @param loadProperties
     * @param loginDetail
     * @return url
     */

    public static String getPlainURL(LoadProperties loadProperties, LoginDetail loginDetail) {

        String[] array = splitURL(loadProperties.getValueFromPropFile(loginDetail.getTestEnvironment()));
        String url = array[0] + ":" + DOUBLE_FORWARD_SLASH + array[1];
        HOME_URL = url;
        return url;
    }

    /*
     ** Retrieve url from properties and get username & password from the data sheet
     * @param loadProperties
     * @param loginDetail
     * @param teamMember
     * @return url
     */

    public static String getURL(LoadProperties loadProperties, LoginDetail loginDetail, String teamMember) {

        String[] array = splitURL(loadProperties.getValueFromPropFile(loginDetail.getTestEnvironment()));
        String url = array[0] + ":" + DOUBLE_FORWARD_SLASH +
                teamMember + ":" + loginDetail.getPassword() + "@" + array[1];
        log.info("URL: " + url);
        HOME_URL = url;
        return url;
    }

    /*
     ** Enter credentials on a modal popup (username & password)
     * @param loginDetail
     */

    public void enterCredentials(LoginDetail loginDetail) throws InterruptedException, AWTException {
        String username = loginDetail.getUserName();
        String password = loginDetail.getPassword();

        if (isAlertPresent() == true) {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(username);

            StringSelection stringSelection = new StringSelection(password);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            Robot robot = new Robot();
            try {
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL); //Added release for CTRL Key
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            alert.accept();
            Thread.sleep(sleeptime);
        } else {
            getDriver().switchTo().defaultContent();
        }
    }

    public boolean isAlertPresent() {
        boolean isPresent = false;
        try {
            driver.switchTo().alert();
            isPresent = true;

        } catch (NoAlertPresentException e) {
            log.info("Alert is not visible");
        }
        return isPresent;
    }

    public String getDataSheetLocation() {
        String location = SeleniumService.getEnvironmentProperties().getTestDatasheet();
        return location;
    }

    /*
     ** Wait for an element to load
     ** This is not an implicit wait therefore it will excecute once the element is available
     */
    protected void waitForElementVisible(final By by) {
        (new WebDriverWait(driver, TIME_OUT)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                try {
                    WebElement element = d.findElement(by);
                    return element != null && element.isDisplayed();
                } catch (Exception e) {
                    return false;
                }
            }
        });
    }

    /*
     ** Wait for a page to load
     ** This is not an implicit wait therefore it will return once the page is available
     */
    protected void waitForPageLoad() {
        Wait<WebDriver> wait = new WebDriverWait(driver, TIME_OUT);
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                log.info("Current Window State       : "
                        + String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
                return String
                        .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                        .equals("complete");
            }
        });

    }

    /*
     *Set zoom level to 100
     */

    public void setZoomToHundredPercent() {
        driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
    }

    /*
     ** Locates an element, timeout handled by waitForElementVisible <implicit> method
     */

    protected WebElement findElement(By by) {
        long timestamp = 0;
        try {
            if (retrieveTestData.getLoginDetail().getBrowser().equalsIgnoreCase(DriverConstants.IE)) {
                setZoomToHundredPercent();
            }
            waitForElementVisible(by);
            timestamp = System.currentTimeMillis();

            if (timestamp > 1000) {
                log.info("Element " + by + " took " + (timestamp % 1000) / 100 + " second/s to be visible");
            }

        } catch (TimeoutException ex) {
            throw new TimeoutException(ex.getMessage());
        }
        return getDriver().findElement(by);
    }

    /*
     ** Locates several elements, timeout handled by waitForElementVisible method
     */
    protected List<WebElement> findElements(By by) {
        try {
            waitForElementVisible(by);
        } catch (TimeoutException ex) {
            throw new TimeoutException(ex.getMessage());
        }
        return getDriver().findElements(by);
    }


    /*
     ** Set report headers
     */

    //Screenshot configuration
    public T takeScreenShoot(ScreenShotConfiguration screenShotConfiguration) {
        if (getDriver() == null) {
            throw new IllegalArgumentException("Driver is not configured");
        }
        WebDriver localDriver = new Augmenter().augment(getDriver());

        File screenShot = ((TakesScreenshot) localDriver).getScreenshotAs(OutputType.FILE);
        try {
            copyFile(screenShot, new File(screenShotConfiguration.getScreenShotFileName()));
        } catch (IOException e) {
            e.getMessage();
        }

        return (T) this;
    }

    public T takeScreenShoot(String screenShot) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");
        String formatedDate = format.format(date);

        String formated = formatedDate.replaceAll("/", "h");
        String dateAndHours = formated.substring(0, 16);
        String minutes = formated.substring(16, 17);
        String seconds = formated.substring(17, 19);

        log.info("Formated dateAndHours: " + dateAndHours);
        log.info("Formated minutes: " + minutes);
        log.info("Formated seconds: " + seconds);

        String completeFormatedDate = dateAndHours + "minutes" + seconds + "seconds";
        log.info("Formated timestamp: " + completeFormatedDate);
        ScreenShotConfiguration screenShotConfiguration = new ScreenShotConfiguration(LocationConstants.SCREEN_SHOTS_DIR + screenShot + "." + stepCounter + ".png");
        log.info("Screen shot: " + screenShot + "." + stepCounter + "(" + completeFormatedDate + ").png" + " captured at time stamp: " + formatedDate);
        stepCounter++;
        return takeScreenShoot(screenShotConfiguration);
    }

    public String captureScreen() {
        String screenShotPath;
        log.info("Capturing screenshot");
        TakesScreenshot oScn = (TakesScreenshot) driver;
        File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
        File oDest = new File(LocationConstants.SCREEN_SHOTS_DIR + oScnShot.getName());
        try {
            FileUtils.copyFile(oScnShot, oDest);
            FileUtils.forceDelete(oScnShot);
        } catch (IOException e) {
            log.error("Failed to capture screen");
        } catch (UnhandledAlertException ae) {
            Alert alert = driver.switchTo().alert();
            alert.accept();

        }
        screenShotPath = "../Screen Shots/" + oDest.getName();
        return screenShotPath;
    }

    /**
     * Checks if page is ready to interact with
     *
     * @return
     */
    public boolean isPageReady() {
        return String
                .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                .equals("complete");
    }

    /*
     ** Locates an element. This method assumes the element is there. No need to wait for visibility
     */
    protected WebElement findInvisibleElement(By by) {
        try {

            long timestamp = System.nanoTime() / millisecond;
            if (timestamp > millisecond) {
                log.info("Element " + by + " took " + timestamp / millisecond + " second/s to be visible");
                log.info("Element " + by + " took " + timestamp / millisecond + " second/s to be visible");
            }
        } catch (TimeoutException ex) {
            throw new TimeoutException(ex.getMessage());
        }
        return getDriver().findElement(by);
    }

    public WebElement waitUntilElementIsClickable(final WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return element;
    }
}

