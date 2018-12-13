package com.project.demo.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class EnvironmentProperties {

    @Value("${test.url}")
    private String testUrl;
    @Value("${test.datasheet}")
    private String testDatasheet;
    @Value("${test.pageObjects}")
    private String testPageObjects;

    public String getTestUrl() {
        return testUrl;
    }

    public String getTestDatasheet() {
        return testDatasheet;
    }

    public String getTestPageObjects() {
        return testPageObjects;
    }

    @Override
    public String toString() {
        return "EnvironmentProperties [testUrl=" + testUrl + " [datasheet[ " + testDatasheet + " ]";
    }

}
