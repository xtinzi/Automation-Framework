package com.project.demo.automation.leadDBUtils;

import java.util.ArrayList;


public class LoginDetail {
    private String testEnvironment;
    private String userName;
    private String password;
    private String browser;

    public LoginDetail(){

    }
    public LoginDetail(ArrayList<String> loginFields){
        int TEST_ENVIRONMENT =0;
        int USER_NAME =1;
        int PASSWORD =2;
        int BROWSER =3;

        setTestEnvironment(loginFields.get(TEST_ENVIRONMENT));
        setUserName(loginFields.get(USER_NAME));
        setPassword(loginFields.get(PASSWORD));
        setBrowser(loginFields.get(BROWSER));




    }

    public String getTestEnvironment() {
        return testEnvironment;
    }

    public void setTestEnvironment(String testEnvironment) {
        this.testEnvironment = testEnvironment;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setBrowser(String browser) {
        this.browser = browser;
    }


    public String getBrowser() {
        return browser;
    }


}
