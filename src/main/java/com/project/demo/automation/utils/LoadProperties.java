package com.project.demo.automation.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class LoadProperties {

    Properties properties = new Properties();
    Logger log = Logger.getLogger(LoadProperties.class);

    public LoadProperties() {
        InputStream inputStream;

        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("properties.properties");
            properties.load(inputStream);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Retrieve key value from the properties file
     *
     * @param key
     * @return value
     */
    public String getValueFromPropFile(String key) {
        return properties.getProperty(key);
    }

}