package com.project.demo.automation.testSuite;


import com.project.demo.automation.tests.CombinedTests;
import org.junit.runner.JUnitCore;

import java.awt.*;

public class RunTests extends BaseRegression {

    public RunTests() throws InterruptedException, AWTException {
        // Initialize Constructor
        super();
    }

    public static void main(String args[]) {
        try {
            RunTests myTest = new RunTests();
            String from, to;

            /*from = regressionProperties.getValueFromPropFile("tests_from");
            to = regressionProperties.getValueFromPropFile("tests_to");*/
            try {

                Class[] cls = {CombinedTests.class,};
               // myTest.updateDatasheet("3", "3");

                JUnitCore.runClasses(cls);
            } catch (Exception e) {
                log.error("Tests Failed");
                log.error(e.getMessage());
            }

        } catch (Exception ex) {
            log.error("Tests Failed");
            log.error(ex.getMessage());
        }


    }
}
