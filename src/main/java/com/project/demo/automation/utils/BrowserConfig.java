package com.project.demo.automation.utils;

import com.project.demo.automation.fileConstants.DriverConstants;
import com.project.demo.automation.fileConstants.LocationConstants;
import com.project.demo.automation.leadDBUtils.LoginDetail;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;


public class BrowserConfig {

    protected static RetrieveTestData retrieveTestData;
    static String browserInstance = getSearchCriteria();
    static Logger  log =Logger.getLogger(BrowserConfig.class);

    protected static WebDriver getDriver() {

        WebDriver driver = null;

        if(browserInstance.equalsIgnoreCase(DriverConstants.CHROME)){
            File file = new File(DriverConstants.CHROME_DRIVER);
            System.setProperty(DriverConstants.CHROME_PROPERTY, file.getAbsolutePath());
            System.out.println("Chrome location : "+file.getAbsolutePath());
            driver = new ChromeDriver();

        }else  if(browserInstance.equalsIgnoreCase(DriverConstants.IE)){
            File file = new File(DriverConstants.IE_DRIVER);
            System.setProperty(DriverConstants.IE_PROPERTY, file.getAbsolutePath());
            driver = new InternetExplorerDriver();
        }
        else  if(browserInstance.equalsIgnoreCase(DriverConstants.FIREFOX)){
            File file = new File(DriverConstants.FIREFOX_DRIVER);
            System.setProperty(DriverConstants.FIREFOX_PROPERTY, file.getAbsolutePath());
            driver = new FirefoxDriver();
        }
        return driver;
    }

    public enum Browser {
        Chrome(1), InternetExplorer(2), Firefox(3);

        private int value;
        private Browser(int value) {
            this.value = value;
        }
    }

    private static LoginDetail getLoginDetail(){

        retrieveTestData = new RetrieveTestData(LocationConstants.DATA_SHEET_FILE);
        LoginDetail loginDetail = retrieveTestData.getLoginDetail();
        if(loginDetail==null){
            loginDetail =retrieveTestData.getLoginDetail();
        }
        return  loginDetail;
    }
    public static String getSearchCriteria() {
        String browser =getLoginDetail().getBrowser();
        Browser searchCriteria = Browser.valueOf(browser);
        try {
            switch (searchCriteria) {
                case Chrome:
                    browser = DriverConstants.CHROME;
                    break;
                case InternetExplorer:
                    browser = DriverConstants.IE;
                    break;
                case Firefox:
                    browser = DriverConstants.FIREFOX;
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return browser;
    }

    private static DesiredCapabilities capabilities;

    public BrowserConfig(DriverService service, Capabilities capabilities, ChromeOptions options) {
        super();
    }

    public BrowserConfig(DriverService service, Capabilities capabilities) {
        super();
    }

    public DriverService getService(){
        DriverService service =null;
        try {

            if (browserInstance.equalsIgnoreCase(DriverConstants.CHROME)) {
                service = new ChromeDriverService.Builder().usingDriverExecutable(new File(LocationConstants.DRIVER_DIR + DriverConstants.CHROME_DRIVER)).usingAnyFreePort().build();
            } else if (browserInstance.equalsIgnoreCase(DriverConstants.IE)) {

                service = new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(LocationConstants.DRIVER_DIR + DriverConstants.IE_DRIVER)).usingAnyFreePort().build();
            } else {

                service = null;
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return service;
    }

    public DesiredCapabilities getCapabilities(){

        if(browserInstance.equalsIgnoreCase(DriverConstants.CHROME)) {
            if (capabilities == null) {
                capabilities = DesiredCapabilities.chrome();
                //the following Chrome options handle the stability and security on newer Chrome drivers
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                options.addArguments("test-type");
                options.addArguments("disable-popup-blocking");
                options.addArguments("disable-extensions");
                capabilities.setCapability("chrome.binary", "");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            }
        }else if(browserInstance.equalsIgnoreCase(DriverConstants.IE)){
            capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability("ignoreZoomSetting", true);
            capabilities.setCapability("enablePersistentHover", false);

        }else{
            capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability("marionette", true);
            capabilities = DesiredCapabilities.firefox();
        }
        return capabilities;
    }


}
