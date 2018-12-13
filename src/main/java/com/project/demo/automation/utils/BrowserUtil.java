package com.project.demo.automation.utils;

import com.project.demo.automation.fileConstants.DriverConstants;
import com.project.demo.automation.fileConstants.LocationConstants;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;


public class BrowserUtil {

    private static BrowserUtil instance;

    public static BrowserUtil getInstance() {
        if (instance == null) {
            instance = new BrowserUtil();
        }
        return instance;
    }

    public BrowserConfig getChromeService() {

        DriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File(LocationConstants.DRIVER_DIR + DriverConstants.CHROME_DRIVER)).usingAnyFreePort().build();
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--test-type");
        options.addArguments("--start-maximized");
        capabilities.setVersion("44");
        capabilities.setCapability("chrome.binary", LocationConstants.DRIVER_DIR + DriverConstants.CHROME_DRIVER);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        return new BrowserConfig(service, capabilities, options);
    }

    public BrowserConfig getInternetExplorerService() {
        DriverService service = new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(LocationConstants.DRIVER_DIR + DriverConstants.IE_DRIVER)).usingAnyFreePort().build();
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        ieCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        ieCapabilities.setVersion("11.0");
        ieCapabilities.setCapability("IE.binary", LocationConstants.DRIVER_DIR + DriverConstants.IE_DRIVER);

        return new BrowserConfig(service, ieCapabilities);
    }

    public BrowserConfig getFireFoxService() {
        DriverService service = new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(LocationConstants.DRIVER_DIR + DriverConstants.FIREFOX_DRIVER)).usingAnyFreePort().build();
        File file = new File(LocationConstants.DRIVER_DIR + DriverConstants.FIREFOX_DRIVER);
        FirefoxProfile firefoxProfile = new FirefoxProfile();

        try {
            firefoxProfile.addExtension(file);
        } catch (IOException e) {
            e.getMessage();
        }
        DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
        firefoxCapabilities.setCapability("firefox.binary", LocationConstants.DRIVER_DIR + DriverConstants.FIREFOX_DRIVER);

        return new BrowserConfig(service, firefoxCapabilities);
    }

}
