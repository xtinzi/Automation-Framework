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
            try {

                Class[] cls = {CombinedTests.class,};

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
