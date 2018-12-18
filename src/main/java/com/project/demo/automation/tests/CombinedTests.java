package com.project.demo.automation.tests;

import com.project.demo.automation.leadDBUtils.CaseForm;
import com.project.demo.automation.utils.RetrieveTestData;
import com.project.demo.wrappers.APIStringResponse;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import scalaj.http.HttpResponse;

import java.util.ArrayList;

public class CombinedTests extends BaseTest {
    String allBreedsURL = "/breeds/list/all";
    String retriverSubBreedsURL = "/breed/retriever/list";
    String randomGoldenURL = "/breed/retriever/golden/images/random";
    String outputStringAllBreeds = "";

    static {
        log = Logger.getLogger(CombinedTests.class);
    }

    @Test
    public void CombinedTest() {
        try {
            // First Retrieve TestData file
            retrieveTestData = new RetrieveTestData(page.getDataSheetLocation());
            getStringCallStatus(allBreedsURL);
            outputStringAllBreeds = getStringResponse(allBreedsURL);
            ArrayList<DogBreed> allDogBreeds = new ArrayList<DogBreed>();
            allDogBreeds = createDogBreedArrayList(allDogBreeds, outputStringAllBreeds);

            for (CaseForm caseForm : retrieveTestData.readCaseData()) {

                if (caseForm.getFirstNameValue().contains("FName") && caseForm.getLastNameValue().contains("LName")) {

                    // Initialize Extent Test
                    try {
                        setExtentTest(extentReports.startTest("Add new person to table"));
                        getExtentTest().log(getLogStatus().PASS, "list loaded successfully." + getExtentTest().addScreenCapture(page.captureScreen()));
                        addNewPerson(page, caseForm);


                    } catch (UnhandledAlertException ue) {
                        getExtentTest().log(getLogStatus().FAIL, "Add new person  Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                    } catch (Throwable ex) {
                        getExtentTest().log(getLogStatus().FAIL, "Add new person  Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        log.error(ex.getMessage());
                    } finally {
                        extentReports.endTest(getExtentTest());
                    }
                    try {
                        int numRows = 1;

                        setExtentTest(extentReports.startTest("Check if  " + caseForm.getFirstNameValue() + "  " + caseForm.getLastNameValue() + "  is on the list"));
                        getExtentTest().log(getLogStatus().PASS, "list loaded successfully." + getExtentTest().addScreenCapture(page.captureScreen()));
                        numRows = page.getDriver().findElements(By.cssSelector("body > table > tbody > tr")).size();
                        ArrayList<Person> people = new ArrayList<Person>();
                        people = createArrayList(page, caseForm, people, numRows);
                        findPerson(page, caseForm, people, caseForm.getFirstNameValue(), caseForm.getLastNameValue());


                        if (findPerson(page, caseForm, people, caseForm.getFirstNameValue(), caseForm.getLastNameValue())) {
                            getExtentTest().log(getLogStatus().PASS, caseForm.getFirstNameValue() + " " + caseForm.getLastNameValue() + " is in the list" + getExtentTest().addScreenCapture(page.captureScreen()));

                        } else {
                            getExtentTest().log(getLogStatus().PASS, caseForm.getFirstNameValue() + " " + caseForm.getLastNameValue() + " is NOT in the list" + getExtentTest().addScreenCapture(page.captureScreen()));

                        }

                    } catch (UnhandledAlertException ue) {
                        getExtentTest().log(getLogStatus().FAIL, "Find person  Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        page.acceptAlert();
                    } catch (Throwable ex) {
                        getExtentTest().log(getLogStatus().FAIL, "Find person  Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        log.error(ex.getMessage());
                    } finally {
                        extentReports.endTest(getExtentTest());
                    }

                    if (caseForm.getFirstNameValue().contains("FName2") && caseForm.getLastNameValue().contains("LName2")) {


                        try {
                            int numRows = 1;
                            setExtentTest(extentReports.startTest("Check for Duplicate usernames on the list"));
                            getExtentTest().log(getLogStatus().PASS, "list loaded successfully." + getExtentTest().addScreenCapture(page.captureScreen()));
                            numRows = page.getDriver().findElements(By.cssSelector("body > table > tbody > tr")).size();
                            ArrayList<Person> people = new ArrayList<Person>();

                            people = createArrayList(page, caseForm, people, numRows);
                            findPerson(page, caseForm, people, caseForm.getFirstNameValue(), caseForm.getLastNameValue());
                            if (findDuplicateUsername(page, caseForm, people).length() > 1) {
                                getExtentTest().log(getLogStatus().FAIL, "Duplicate Usernames Found: " + findDuplicateUsername(page, caseForm, people) + getExtentTest().addScreenCapture(page.captureScreen()));

                            } else {
                                getExtentTest().log(getLogStatus().PASS, "No Duplicate Usernames Found" + getExtentTest().addScreenCapture(page.captureScreen()));

                            }


                        } catch (UnhandledAlertException ue) {
                            getExtentTest().log(getLogStatus().FAIL, "Find person  Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                            page.acceptAlert();
                        } catch (Throwable ex) {
                            getExtentTest().log(getLogStatus().FAIL, "Find person  Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                            log.error(ex.getMessage());
                        } finally {
                            extentReports.endTest(getExtentTest());
                        }

                    }

                } else if (caseForm.getFirstNameValue().equalsIgnoreCase("Xolani") && caseForm.getLastNameValue().equalsIgnoreCase("Tinzi")) {

                    try {
                        setExtentTest(extentReports.startTest("Get All Breed list"));
                        printBreeds(allDogBreeds);
                    } catch (UnhandledAlertException ue) {
                        getExtentTest().log(getLogStatus().FAIL, "Get All Breed list Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        page.acceptAlert();
                    } catch (Throwable ex) {
                        getExtentTest().log(getLogStatus().FAIL, "Get All Breed listTest failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        log.error(ex.getMessage());
                    } finally {
                        extentReports.endTest(getExtentTest());
                    }
                    try {
                        setExtentTest(extentReports.startTest("Check retriever from list"));
                        if (findBreed(allDogBreeds, "retriever")) {
                            getExtentTest().log(getLogStatus().PASS, "retriever was found on the list of dogs");

                        } else {
                            getExtentTest().log(getLogStatus().FAIL, "retriever was not found on the list of dogs");

                        }

                    } catch (UnhandledAlertException ue) {
                        getExtentTest().log(getLogStatus().FAIL, "Check retriever from list Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        page.acceptAlert();
                    } catch (Throwable ex) {
                        getExtentTest().log(getLogStatus().FAIL, "Check retriever from list Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        log.error(ex.getMessage());
                    } finally {
                        extentReports.endTest(getExtentTest());
                    }
                    try {
                        setExtentTest(extentReports.startTest("API request to produce a list of sub-breeds for retriever."));
                        if (getStringCallStatus(retriverSubBreedsURL).equalsIgnoreCase("success")) {
                            getExtentTest().log(getLogStatus().PASS, "sub-breeds  for retriever were found on the list of dogs");
                            String outputStringSubBreed = getStringResponse(retriverSubBreedsURL);

                            ArrayList<SubBreed> subBreeds = new ArrayList<SubBreed>();
                            subBreeds = createSubBreedArrayList(subBreeds, outputStringSubBreed);
                            printSubBreeds(subBreeds);
                        } else {
                            getExtentTest().log(getLogStatus().FAIL, "sub-breeds  for retriever were found not found on the list of dogs");

                        }

                    } catch (UnhandledAlertException ue) {
                        getExtentTest().log(getLogStatus().FAIL, "sub-breeds for retriever Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        page.acceptAlert();
                    } catch (Throwable ex) {
                        getExtentTest().log(getLogStatus().FAIL, "sub-breeds for retriever Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        log.error(ex.getMessage());
                    } finally {
                        extentReports.endTest(getExtentTest());
                    }


                    try {
                        setExtentTest(extentReports.startTest("API request to produce a random image / link for the sub-breed 'golden'"));
                            String breed = "retriever";
                            String subBreed = "golden";

                            // https://dog.ceo/api/breed/retriever/golden/images
                            String url = String.format("%s/breed/%s/%s/images/random", baseUrl, breed,subBreed);
                            HttpResponse<String> response = httpGet(url);
                            assert (response.code() == 200);

                            APIStringResponse apiStringResponse = apiStringToString(response.body());
                            assert (apiStringResponse.status.equals("success"));
                            assert (apiStringResponse.message.length() > 0);

                            // https://images.dog.ceo/breeds/retriever-golden/n02099601_10.jpg
                            String imageUrl = apiStringResponse.message;

                            // confirm text starts as a URL
                            assert(imageUrl.matches("^(https|http).*$"));
                            // confirm text ends in an image extension
                            assert(imageUrl.matches(".*(jpg|png)$"));
                            getExtentTest().log(getLogStatus().PASS, " random image / link for the sub-breed 'golden' call was Successful");
                            getExtentTest().log(getLogStatus().PASS, "random image/link for the sub-breed 'golden' call returned: " + "<a href=" + getStringCallMessage(randomGoldenURL).replace("\\", "") + ">Click Link</a>");




                    } catch (UnhandledAlertException ue) {
                        getExtentTest().log(getLogStatus().FAIL, "random image / link for the sub-breed 'golden' Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        page.acceptAlert();
                    } catch (Throwable ex) {
                        getExtentTest().log(getLogStatus().FAIL, "random image / link for the sub-breed 'golden' Test failedTest failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        log.error(ex.getMessage());
                    } finally {
                        extentReports.endTest(getExtentTest());
                    }


                    try {
                        int numRows = 1;

                        setExtentTest(extentReports.startTest("Check if  " + caseForm.getFirstNameValue() + "  " + caseForm.getLastNameValue() + "  is on the list"));
                        getExtentTest().log(getLogStatus().PASS, "list loaded successfully." + getExtentTest().addScreenCapture(page.captureScreen()));
                        numRows = page.getDriver().findElements(By.cssSelector("body > table > tbody > tr")).size();
                        ArrayList<Person> people = new ArrayList<Person>();
                        people = createArrayList(page, caseForm, people, numRows);
                        findPerson(page, caseForm, people, caseForm.getFirstNameValue(), caseForm.getLastNameValue());


                        if (findPerson(page, caseForm, people, caseForm.getFirstNameValue(), caseForm.getLastNameValue())) {
                            getExtentTest().log(getLogStatus().PASS, caseForm.getFirstNameValue() + " " + caseForm.getLastNameValue() + " is in the list" + getExtentTest().addScreenCapture(page.captureScreen()));

                        } else {
                            getExtentTest().log(getLogStatus().PASS, caseForm.getFirstNameValue() + " " + caseForm.getLastNameValue() + " is NOT in the list" + getExtentTest().addScreenCapture(page.captureScreen()));

                        }

                    } catch (UnhandledAlertException ue) {
                        getExtentTest().log(getLogStatus().FAIL, "Find person  Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        page.acceptAlert();
                    } catch (Throwable ex) {
                        getExtentTest().log(getLogStatus().FAIL, "Find person  Test failed:  Unexpected Alert was Present." + getExtentTest().addScreenCapture(page.captureScreen()));
                        log.error(ex.getMessage());
                    } finally {
                        extentReports.endTest(getExtentTest());
                    }

                }

            }
        } catch (Exception ex) {
            getExtentTest().log(getLogStatus().FAIL, "Tests failed: " + getExtentTest().addScreenCapture(page.captureScreen()));
            log.error(ex.getMessage());
        } finally {
            extentReports.flush();
        }

    }


}
