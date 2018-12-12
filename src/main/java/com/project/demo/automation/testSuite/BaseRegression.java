package com.project.demo.automation.testSuite;

import com.project.demo.automation.utils.*;
import com.project.demo.properties.EnvironmentProperties;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseRegression extends SeleniumService{
    protected static LoadProperties regressionProperties;
    protected String sheetPath="";
    ApplicationContext applicationContext;
    static {
        log = Logger.getLogger(BaseRegression.class);
    }
    //Added Report_file_Smoke
    public static GenerateReports reportingModule_smoke;

    public BaseRegression(){
        regressionProperties = new LoadProperties();
        applicationContext = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/mini-xml-config-context.xml");
        EnvironmentProperties environmentProperties =applicationContext.getBean(EnvironmentProperties.class);
        createTestFiles(environmentProperties);
        retrieveTestData =new RetrieveTestData(environmentProperties.getTestDatasheet());
        sheetPath =environmentProperties.getTestDatasheet();
    }








    public void setRetrieveTestData(RetrieveTestData retrieveTestData) {
        this.retrieveTestData = retrieveTestData;
    }








    public RetrieveTestData getRetrieveTestData() {
        return retrieveTestData;
    }



}
