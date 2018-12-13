package com.project.demo.automation.tests;

public class Person {
    private String name;
    private String lastName;
    private String userName;
    private String customer;
    private String role;
    private String email;
    private String cellPhone;
    private Boolean Locked;

    public Person(String name, String lastName, String userName, String customer, String role, String email, String cellPhone, Boolean locked) {
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.customer = customer;
        this.role = role;
        this.email = email;
        this.cellPhone = cellPhone;
        Locked = locked;
    }

    public Person() {


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Boolean getLocked() {
        return Locked;
    }

    public void setLocked(Boolean locked) {
        Locked = locked;
    }
}
