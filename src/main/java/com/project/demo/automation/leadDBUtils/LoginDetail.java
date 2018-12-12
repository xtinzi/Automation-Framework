package com.project.demo.automation.leadDBUtils;

import java.util.ArrayList;


public class LoginDetail {
    private String testEnvironment;
    private String userName;
    private String password;
    private String businessUnit;
    private String profileType;
    private String roles;
    private String verifyBusinessUnitAndRole;
    private String browser;
    private String version;
    private String privateBankerTeam;
    private String privateBankerTeamMembers;
    private boolean isRecord;

    public LoginDetail(){

    }
    public LoginDetail(ArrayList<String> loginFields){
        int TEST_ENVIRONMENT =0;
        int USER_NAME =1;
        int PASSWORD =2;
        int BUSINESS_UNIT = 3;
        int PROFILE_TYPE =4;
        int ROLES = 5;
        int VERIFY_BUSINESS_UNIT_AND_ROLE = 6;
        int PRIVATE_BANKER_TEAM = 7;
        int PRIVATE_BANKER_TEAM_MEMBERS = 8;
        int BROWSER = 9;
        int RECORD =10;

        setTestEnvironment(loginFields.get(TEST_ENVIRONMENT));
        setUserName(loginFields.get(USER_NAME));
        setPassword(loginFields.get(PASSWORD));
        setProfileType((loginFields.get(PROFILE_TYPE)));
        setBusinessUnit((loginFields.get(BUSINESS_UNIT)));
        if(getTestEnvironment().contains("REWARDS")){
            setBusinessUnit("Rewards");
        }
        setRoles((loginFields.get(ROLES)));
        setVerifyBusinessUnitAndRole((loginFields.get(VERIFY_BUSINESS_UNIT_AND_ROLE)));
        setBrowser(loginFields.get(BROWSER));
        setPrivateBankerTeam(loginFields.get(PRIVATE_BANKER_TEAM));
        setPrivateBankerTeamMembers(loginFields.get(PRIVATE_BANKER_TEAM_MEMBERS));
        setRecord(loginFields.get(RECORD).equalsIgnoreCase("Yes"));



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

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getVerifyBusinessUnitAndRole() {
        return verifyBusinessUnitAndRole;
    }

    public void setVerifyBusinessUnitAndRole(String verifyBusinessUnitAndRole) {
        this.verifyBusinessUnitAndRole = verifyBusinessUnitAndRole;
    }
    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getBrowser() {
        return browser;
    }

    public String getVersion() {
        return version;
    }

    public String getPrivateBankerTeamMembers() {
        return privateBankerTeamMembers;
    }

    public void setPrivateBankerTeamMembers(String privateBankerTeamMembers) {
        this.privateBankerTeamMembers = privateBankerTeamMembers;
    }

    public String getPrivateBankerTeam() {
        return privateBankerTeam;
    }

    public void setPrivateBankerTeam(String privateBankerTeam) {
        this.privateBankerTeam = privateBankerTeam;
    }
    public boolean isRecord() {
        return isRecord;
    }

    public void setRecord(boolean record) {
        isRecord = record;
    }
}
