# Automation-Framework
java-maven-spring-selenium-basic API testing

# Testing Framework incoorporating all open source technologies

### Testing approach
    Data Driven Testing

### Features Supported
    Cross-Browser Compatibility Testing
    Jar packaging 


###  Reporting
    ReportPortal Integration
    Screenshots 
    Logs 


## Use Maven

Open a command window and run:

    mvn clean install





## Overriding options

The Cucumber runtime parses command line options to know what features to run, where the glue code lives, what plugins to use etc.
When you use the JUnit runner, these options are generated from the `@CucumberOptions` annotation on your test.

Sometimes it can be useful to override these options without changing or recompiling the JUnit class. This can be done with the
`cucumber.options` system property. The general form is:

Using Maven:

    mvn -...


### Run...

Specify a particular scenario by *line* (and use the pretty plugin, which prints the scenario back)




### Technology used 
    Java 1.8
    Selenium 3.6.0
    Maven 3.3+
    Spring 4.3.7-RELEASE
