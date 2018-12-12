package com.project.demo.automation.utils;


public enum BrowserConstants {

    CHROME("Chrome"),
    FIREFOX("Firefox"),
    INTERNET_EXPLORER("Internet Explorer"),
    SAFARI("Safari");

    private String browser;
    private String property;

    BrowserConstants(String browser) {
        this.browser = browser;
    }

    @Override
    public String toString() {
            return browser;
    }

}
