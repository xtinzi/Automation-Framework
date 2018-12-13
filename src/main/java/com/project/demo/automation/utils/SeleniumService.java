package com.project.demo.automation.utils;

import com.project.demo.automation.Page.AbstractPage;
import com.project.demo.automation.Page.HomePage;
import com.project.demo.automation.Page.Page;
import com.project.demo.automation.fileConstants.DriverConstants;
import com.project.demo.automation.fileConstants.LocationConstants;
import com.project.demo.automation.leadDBUtils.LoginDetail;
import com.project.demo.environments.test.IntEnv;
import com.project.demo.generic.GenericEnv;
import com.project.demo.properties.EnvironmentProperties;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;
import junit.framework.TestCase;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:WEB-INF/spring/mini-xml-config-context.xml")
public class SeleniumService extends TestCase {

    protected static ExtentReports extentReports;
    private static ExtentTest extentTest;
    private static LogStatus logStatus;

    protected static BrowserConfig config;
    protected static DriverService service;
    protected WebDriver driver;
    protected static WebDriverUtil webUtil;
    protected RetrieveTestData retrieveTestData;
    // RecordVideo recordVideo;
    protected HomePage page;
    private static EnvironmentProperties environmentPropertiesCopy;
    static ApplicationContext context;
    private static String environment;
    protected PageObjects pageObjects;
    @Autowired
    protected EnvironmentProperties environmentProperties;
    @Autowired
    private GenericEnv env;
    protected static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(new Object() {
    }.getClass().getEnclosingClass());
    public LoginDetail loginDetail;
    static int numberOfDriverInstances = 0;

    //Before executing a class, this method creates a chrome service

    protected List<Throwable> exceptions = null;

    @BeforeClass
    public static void createService() throws IOException {

        context = new ClassPathXmlApplicationContext("WEB-INF/spring/mini-xml-config-context.xml");
        environmentPropertiesCopy = context.getBean(EnvironmentProperties.class);

        createTestFiles(environmentPropertiesCopy);
        if (config == null) {
            LocationConstants.DATA_SHEET_FILE = environmentPropertiesCopy.getTestDatasheet();
            config = BrowserUtil.getInstance().getChromeService();
        }

        if (service == null) {
            service = config.getService();
            service.start();
        } else if (!service.isRunning()) {
            service.start();
        }
    }

    public static ExtentTest getExtentTest() {
        return extentTest;
    }

    public void setExtentTest(ExtentTest extentTest) {
        this.extentTest = extentTest;
    }

    public static LogStatus getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(LogStatus logStatus) {
        this.logStatus = logStatus;
    }
    /*
     ** setUp a selenium service
     */

    @Override
    @Before
    public void setUp() throws Exception {
        environmentPropertiesCopy = environmentProperties;
        if (env instanceof IntEnv) {
            setEnvironment(((IntEnv) env).getEnvName());
        } else {
            throw new Exception("Spring Boot Environment Properties not set.");
        }

        if (this.driver == null) {
            this.driver = new RemoteWebDriver(service.getUrl(), config.getCapabilities());
            numberOfDriverInstances++;
        }
        if (retrieveTestData == null) {
            retrieveTestData = new RetrieveTestData(environmentProperties.getTestDatasheet());
        }
        loginDetail = new RetrieveTestData(environmentProperties.getTestDatasheet()).getLoginDetail();
        log.info("Environment " + environmentProperties.getTestUrl());
        page = new HomePage(driver);
        pageObjects = new PageObjects(environmentProperties.getTestPageObjects());
        page.setPageObjects(pageObjects);
        page.load(environmentProperties.getTestUrl(), loginDetail.getUserName(), loginDetail.getPassword());

        exceptions = new ArrayList<>();

        // Initialize and Instantiate Extent Report
        extentReports = new ExtentReports(page.REPORT_DIRECTORY + "TestAutomationReport.html", false, NetworkMode.ONLINE);
        extentReports.addSystemInfo("Environment", getEnvironment().toUpperCase());
        page.setMainWindowHandle(page.getDriver().getWindowHandle());

    }

    /*
     ** Create Web utilities
     */
    protected synchronized WebDriverUtil createWebUtil() {

        if (driver == null) {
            driver = new ChromeDriver();
            webUtil = new WebDriverUtil(driver);
        } else if (webUtil == null) {
            webUtil = new WebDriverUtil(driver);
        }
        return webUtil;
    }

    public static WebDriverUtil getWebUtil() {
        return webUtil;
    }

    protected static void setWebUtil(WebDriverUtil webUtil) {
        if (SeleniumService.webUtil == null) {
            SeleniumService.webUtil = webUtil;

        }
    }

    public static DriverService getService() {
        return service;
    }

    protected static void setService(DriverService service) {
        if (SeleniumService.service == null) {
            SeleniumService.service = service;
        }
    }

    public WebDriver restartService(String teamMember) throws AWTException, InterruptedException {
        if (driver != null) {
            driver = new RemoteWebDriver(service.getUrl(), config.getCapabilities());

            LoadProperties loadProperties = new LoadProperties();
            AbstractPage abstractPage = new AbstractPage(driver) {
                public Page load() {
                    return null;
                }
            };
            retrieveTestData = new RetrieveTestData(String.valueOf(LocationConstants.DATA_SHEET_FILE));
            loginDetail = retrieveTestData.getLoginDetail();
            if (loginDetail.getBrowser().equalsIgnoreCase(String.valueOf(BrowserConstants.CHROME))) {
                driver.get(AbstractPage.getURL(loadProperties, loginDetail, teamMember));
                driver.get(AbstractPage.getPlainURL(loadProperties, loginDetail));
            } else {
                driver.get(AbstractPage.getPlainURL(loadProperties, loginDetail));
                abstractPage.enterCredentials(loginDetail);
            }
        }
        createWebUtil();
        return driver;
    }

    public WebDriver launchNewService(LoginDetail loginDetail) throws AWTException, InterruptedException {
        driver = new RemoteWebDriver(service.getUrl(), config.getCapabilities());
        LoadProperties loadProperties = new LoadProperties();
        AbstractPage abstractPage = new AbstractPage(driver) {
            public Page load() {
                return null;
            }
        };
        if (loginDetail.getBrowser().equalsIgnoreCase(String.valueOf(BrowserConstants.CHROME))) {
            driver.get(AbstractPage.getURLs(loadProperties, loginDetail));
            driver.get(AbstractPage.getPlainURL(loadProperties, loginDetail));
        } else {
            driver.get(AbstractPage.getPlainURL(loadProperties, loginDetail));
            abstractPage.enterCredentials(loginDetail);
        }
        return driver;
    }

    public static EnvironmentProperties getEnvironmentProperties() {
        return environmentPropertiesCopy;
    }

    public String getEnvironment() {
        return environment;
    }

    public static void setEnvironment(String environment) {
        SeleniumService.environment = environment;
    }

    @Override
    @After
    public void tearDown() {
        try {
            if (this.driver != null) {
                this.driver.quit();
                numberOfDriverInstances--;

            }
            if (retrieveTestData != null) {
                retrieveTestData = null;
            }
           /* if (recordVideo != null) {
                recordVideo.stopRecording();
            }*/
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        if (exceptions != null) {
            if (!exceptions.isEmpty()) {
                log.error("Number of Exceptions = " + exceptions.size());
            }
            Assert.assertEquals(0, exceptions.size());
        }
        //this is so a failure is picked up in an event of exceptions

    }

    /*
     **Stops the selenium service after test class executes
     * Even when test fails
     */
    @AfterClass
    public static void stopService() {

        log.info("Stopping Selenium Service");
        if (service != null && numberOfDriverInstances == 0) {
            service.stop();
        }
    }

    /**
     * this method creates the test files; main folder,data directory and report directory
     */
    public static void createTestFiles(EnvironmentProperties environmentProperties) {
        File parentDirectory = new File(LocationConstants.MAIN_FOLDER);

        log.info("\nTest Files Location : " + parentDirectory.getAbsolutePath());
        File dataDirectory = new File(LocationConstants.DATA_FOLDER);
        File reportDir = new File(LocationConstants.REPORT_DIRECTORY);
        File driverDir = new File(LocationConstants.DRIVER_DIR);
        File pageObjectDir = new File(LocationConstants.PAGE_OBJECT_DIR);
        if (!parentDirectory.exists()) {
            parentDirectory.mkdir();
        }

        if (!dataDirectory.exists()) {
            dataDirectory.mkdir();
        }
        if (!reportDir.exists()) {
            reportDir.mkdir();
        }
        if (!driverDir.exists()) {
            driverDir.mkdir();
        }
        if (!pageObjectDir.exists()) {
            pageObjectDir.mkdir();
        }


        try {
            //Move files from the resources directory to the test info directory
            String extractedDataSheetLocation = new File(environmentProperties.getTestDatasheet()).getName();
            String extractedPageObjectsFileName = new File(environmentProperties.getTestPageObjects()).getName();

            if (!new File(LocationConstants.DATA_FOLDER + extractedDataSheetLocation).exists()) {
                moveFile("/" + extractedDataSheetLocation, extractedDataSheetLocation, LocationConstants.DATA_FOLDER);
            }
            if (!new File(LocationConstants.DRIVER_DIR + DriverConstants.CHROME_DRIVER).exists()) {
                moveFile("/" + DriverConstants.CHROME_DRIVER, DriverConstants.CHROME_DRIVER, LocationConstants.DRIVER_DIR);
            }
            if (!new File(LocationConstants.DRIVER_DIR + DriverConstants.IE_DRIVER).exists()) {
                moveFile("/" + DriverConstants.IE_DRIVER, DriverConstants.IE_DRIVER, LocationConstants.DRIVER_DIR);
            }

            if (!new File(LocationConstants.PAGE_OBJECT_DIR + extractedPageObjectsFileName).exists()) {
                moveFile("/" + extractedPageObjectsFileName, extractedPageObjectsFileName, LocationConstants.PAGE_OBJECT_DIR);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    public static void moveFile(String inputPath, String inputFile, String outputPath) throws IOException {
        URL url = AbstractPage.class.getResource(inputPath);
        InputStream in = url.openStream();
        File dir = new File(outputPath);
        OutputStream out = new FileOutputStream(outputPath + inputFile);
        try {
            //create output directory if it doesn't exist
            if (!dir.exists()) {
                dir.mkdirs();
            }

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            // write the output file
            out.flush();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            try {
                out.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}

