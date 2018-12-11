# Automation-Framework
java-maven-spring-selenium-basic API testing
- The automation framework was developed to simulate user control of web pages, it is Data Driven. The framework also does basic API testing by retrieving json responces from API calls made to the DOG API.

# Testing Framework incoorporating open source technologies

# Why use these technologies:


### Testing approach
    Data Driven Testing

### Features Supported
    Cross-Browser Compatibility Testing
    Jar packaging 


###  Reporting
    ReportPortal Integration
    Screenshots 
    Logs 


### Pre-requisites:
Java Programming knowledge
Selenium Web Driver knowledge

Main classes

Selenium Service
Environment Properties 
Page Objects
Retrieve Test Data
Extent Reporting
Logger
BaseTest

###Overview of main modules/classes

1.      Selenium service
This module extends the Selenium Library and all of its functions which manipulate and control web pages within a browser. E.g. Launching a web-page, clicking an element, entering data on a form.

2.      Environment properties
This module controls each environment’s dependencies and is controlled by the IDE’s VM options. The selected VM option will then ensure that the correct files are used for the test:

-        Test Data File
-        Environment URL
-        Page Objects

3.      Page objects
This module firstly contains a list of mapped page elements in XML format, either by Name, XPath, ClassName, ID etc.

4.      Retrieve test data
This module contains the methods necessary to read the excel file’s worksheets, rows and cells in order to extract the relevant information from it.

5.      extent Reporting module
used for creating reports.

6.      Homepage
This module contains methods for the interaction with the Webpage. E.g. To switch focus to a specific element, or to take a screenshot.

7.      Logger
This module simply for loggging information.

8.      BaseTest
This Class contains methods used by the Tests.

### Running The tests

## Using Maven

Open a command window and:

# Clone the project   

# Change to directory with jar file
    cd New_Project\target
    
# Create jar file
    mvn clean package -DskipTests

# Change to directory with jar file
    cd New_Project\target

# Run the Jar File
    Java ....

# Open generated_folder and check report for results.

## On intelliJ

### Technology used 
    Java 1.8
    Selenium 3.6.0
    Maven 3.5.2
    Spring 4.3.7-RELEASE
    Extent report 2.41.2+
