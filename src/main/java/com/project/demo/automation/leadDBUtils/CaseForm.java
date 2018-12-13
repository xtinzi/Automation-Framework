package com.project.demo.automation.leadDBUtils;

import java.util.List;


public class CaseForm {
    //Test details
    private String testType;
    private String testScenario = "";
    //Case details
    private String firstNameValue;
    private String lastNameValue;
    private String userNameValue;
    private String passwordValue;
    private String customerValue;
    private String roleValue;
    private String emailValue;
    private String cellPhoneValue;


    private String client;
    private String caseTopic;
    private String productType;
    private String enquiryType;
    private String enquiryDetailsNotes;
    private String location;
    private String hotelTourExperience;
    private String supplier;
    private String supplierReference;
    private String booked;

    public CaseForm(List<String> caseFormIndices) {
        //test indices
        int TEST_TYPE = 1;
        int TEST_SCENARIO = 2;
        //tests indices
        int FIRST_NAME_VALUE = 3;
        int LAST_NAME_VALUE = 4;
        int USERNAME_VALUE = 5;
        int PASSWORD_VALUE = 6;
        int CUSTOMER_VALUE = 7;
        int ROLE_VALUE = 8;
        int EMAIL_VALUE = 9;
        int CELLPHONE_VALUE = 10;


        testType = caseFormIndices.get(TEST_TYPE);
        testScenario = caseFormIndices.get(TEST_SCENARIO);
        firstNameValue = caseFormIndices.get(FIRST_NAME_VALUE);
        lastNameValue = caseFormIndices.get(LAST_NAME_VALUE);
        userNameValue = caseFormIndices.get(USERNAME_VALUE);
        passwordValue = caseFormIndices.get(PASSWORD_VALUE);
        customerValue = caseFormIndices.get(CUSTOMER_VALUE);
        roleValue = caseFormIndices.get(ROLE_VALUE);
        emailValue = caseFormIndices.get(EMAIL_VALUE);
        cellPhoneValue = caseFormIndices.get(CELLPHONE_VALUE);
        lastNameValue = caseFormIndices.get(LAST_NAME_VALUE);


    }

    public CaseForm(List<String> caseFormIndices, boolean isChild) {


    }

    public String getLastNameValue() {
        return lastNameValue;
    }

    public void setLastNameValue(String lastNameValue) {
        this.lastNameValue = lastNameValue;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public void setTestScenario(String testScenario) {
        this.testScenario = testScenario;
    }

    public String getPasswordValue() {
        return passwordValue;
    }

    public void setPasswordValue(String passwordValue) {
        this.passwordValue = passwordValue;
    }

    public String getCustomerValue() {
        return customerValue;
    }

    public void setCustomerValue(String customerValue) {
        this.customerValue = customerValue;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }

    public String getEmailValue() {
        return emailValue;
    }

    public void setEmailValue(String emailValue) {
        this.emailValue = emailValue;
    }

    public String getCellPhoneValue() {
        return cellPhoneValue;
    }

    public void setCellPhoneValue(String cellPhoneValue) {
        this.cellPhoneValue = cellPhoneValue;
    }


    public String getUserNameValue() {
        return userNameValue;
    }

    public String getFirstNameValue() {
        return firstNameValue;
    }

    public void setFirstNameValue(String firstNameValue) {
        this.firstNameValue = firstNameValue;
    }


}


