package com.project.demo.automation.Page;


public class ScreenShotConfiguration {

    private String screenShotFileName;

    public ScreenShotConfiguration(String screenShotFileName)
    {
        this.screenShotFileName = screenShotFileName;
    }

    public String getScreenShotFileName()
    {
        return screenShotFileName;
    }

    public void setScreenShotFileName(String screenShotFileName) {
        this.screenShotFileName = screenShotFileName;
    }
}
