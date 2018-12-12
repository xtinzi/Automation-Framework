# Automation-Framework
java-maven-spring-selenium-basic API testing
- The automation framework was developed to simulate user control of web pages, it is Data Driven. The framework also does basic API testing by retrieving json responses from API calls made to the DOG API.

# Testing Framework incorporating open source technologies

# Why use these technologies:
The Assessment tests basic automation knowledge and basic API testing so it can be done using existing frameworks. 

The front end website used suggests that protractor should be used to test it, which then introduces the idea of using node. That would be the most simple way to do the assignment and it would be quick to setup. I've worked with protractor and other frameworks that use node and my biggest issue with these frameworks was continuous integration with other tools. 

So when node modules were removed or updated, it affected the project and was therefore unreliable, especially for a framework that is integrated to automated builds. So I try to avoid using node and the best way to do this is to build a framework that uses selenium and relies purely on maven to run, giving you control over what affects your project. 

This is the longer way to do this simple project but time lost in the initial phase of the framework is saved later on when you avoid problems from dependencies that are beyond your control. Also with this approach, debugging through code becomes easier and you don't have to pause screens to debug selenium or rely on the log file.


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

## Running The tests

### Using Maven

Open a command window and:

#### Clone the project   
    
#### Create the executable jar file
    cd Automation-Framework
    cd New_Project
    delete the target directory if its there
    mvn clean package -DskipTests 
    
#### Copy the jar file(automation-1.0-SNAPSHOT.jar) from New_Project\target into your a folder e.g MyFolder
    
#### Change to directory with jar file
    cd MyFolder

#### Run the Jar File
    java -Dspring.profiles.active=test -jar automation-1.0-SNAPSHOT.jar
    


#### Open generated_folder and check Report folder for report html file where you can view the results(use chrome browser to view the report).

### Using an IDE(intelliJ IDEA) to run the tests and debug through the code

#### import the the project as a maven project

#### set up VM options as shown in the picture below
![image1](https://user-images.githubusercontent.com/7296111/49867347-de76e380-fe12-11e8-9f7b-aa53cb4ba897.jpg)


#### The spreadsheet controls which tests you are running, from shows which test to start with and to is the last test to run
![image2](https://user-images.githubusercontent.com/7296111/49867348-df0f7a00-fe12-11e8-854c-820cfcc28280.jpg)




### Technology used 
    Java 1.8
    Selenium 3.6.0
    Maven 3.5.2
    Spring 4.3.7-RELEASE
    Extent report 2.41.2+
