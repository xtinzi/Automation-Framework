package com.project.demo.automation.tests;

import com.project.demo.automation.Page.HomePage;
import com.project.demo.automation.leadDBUtils.CaseForm;

import com.project.demo.automation.utils.PageNames;
import com.project.demo.automation.utils.SeleniumService;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;

public class BaseTest extends SeleniumService {

    protected int sleepTime = 2000;
    protected final boolean YES = true;
    protected final boolean NO = false;

    List<? extends CaseForm> caseForms;
    /*
    Element identifiers
     */

    private String casesPage = PageNames.TEST_PAGE;


    private String addButtonElement = "Add Button";
    private String firstNameElement = "FirstName";
    private String lastNameElement = "LastName";
    private String userNameElement = "UserName";
    private String passwrdElement = "Password";
    private String customerRadioCompanyAAAElement = "Radio Company AAA";
    private String customerRadioCompanyBBBElement = "Radio Company BBB";
    private String roleElement = "Role Dropdown";
    private String emailElement = "Email";
    private String cellphoneElement = "CellPhone";
    private String saveButtonElement = "SAVE BUTTON";


    private static boolean isExternalGridTest = false;

    //To avoid returning nulls, access this field via a getter method
    private String browserAndEnvironment;
    boolean isInternetExplorer = false;

    public String getBrowserAndEnvironment(HomePage page) {
        browserAndEnvironment = page.getBrowser() + ", " + getEnvironment().toUpperCase() + ", ";
        setInternetExplorer(page.getBrowser().equalsIgnoreCase("InternetExplorer"));
        return browserAndEnvironment;
    }


    /*
    Getters
     */
    public int getSleepTime() {
        return sleepTime;
    }

    public WebDriver getDriver() {
        return this.driver;
    }


    public boolean isExternalGridTest() {
        return isExternalGridTest;
    }

    public void setExternalGridTest(boolean externalGridTest) {
        isExternalGridTest = externalGridTest;
    }


    public boolean isInternetExplorer() {
        return isInternetExplorer;
    }

    public void setInternetExplorer(boolean internetExplorer) {
        isInternetExplorer = internetExplorer;
    }


    public Boolean findPerson(HomePage page, CaseForm caseForm, ArrayList<Person> people, String name, String surname) {
        Boolean personFound = false;
        try {
            Iterator<Person> peopleIterator = people.iterator();
            int i = 0;
            while (peopleIterator.hasNext() && personFound != true && i < 7) {
                if (people.get(i).getName().equalsIgnoreCase(name) && people.get(i).getLastName().equalsIgnoreCase(surname)) {
                    personFound = true;

                } else {
                    personFound = false;

                }
                i++;
            }
        } catch (Exception e) {
            getExtentTest().log(getLogStatus().FAIL, "Find Person Test Failed" + getExtentTest().addScreenCapture(page.captureScreen()));
        }

        return personFound;
    }

    public String findDuplicateUsername(HomePage page, CaseForm caseForm, ArrayList<Person> people) {
        String duplicateUsername = "";
        try {

            for (int i = 0; i < people.size(); i++) {
                for (int j = i + 1; j < people.size(); j++) {
                    if (people.get(i).getUserName().equalsIgnoreCase(people.get(j).getUserName())) {

                        duplicateUsername = duplicateUsername + ",";
                    }
                }
            }
        } catch (Exception e) {
            getExtentTest().log(getLogStatus().FAIL, "Find Duplicate Usernames Test Failed" + getExtentTest().addScreenCapture(page.captureScreen()));
        }

        return duplicateUsername;

    }

    public void addNewPerson(HomePage page, CaseForm caseForm) throws Exception {
        try {
            page.switchFocusTo(casesPage, addButtonElement);
            page.enterDataFromExcel(casesPage, firstNameElement, caseForm.getFirstNameValue());
            page.enterDataFromExcel(casesPage, lastNameElement, caseForm.getLastNameValue());
            page.enterDataFromExcel(casesPage, userNameElement, caseForm.getUserNameValue());
            page.enterDataFromExcel(casesPage, passwrdElement, caseForm.getPasswordValue());

            if (caseForm.getCustomerValue().equalsIgnoreCase("Company AAA")) {
                //options radio 1
                page.switchFocusTo(casesPage, customerRadioCompanyAAAElement);
            } else if (caseForm.getCustomerValue().equalsIgnoreCase("Company BBB")) {//options radio 2
                page.switchFocusTo(casesPage, customerRadioCompanyBBBElement);
            }

            page.switchFocusTo(casesPage, roleElement);

            if (caseForm.getRoleValue().equalsIgnoreCase("Sales Team")) {
                //Sales Team
                page.selectOptionFromList(page.getDriver().findElement(By.name("RoleId")), "Sales Team");
            } else if (caseForm.getRoleValue().equalsIgnoreCase("Customer")) {
                //Customer
                page.selectOptionFromList(page.getDriver().findElement(By.name("RoleId")), "Customer");
            }
            //Admin
            else if (caseForm.getRoleValue().equalsIgnoreCase("Admin")) {
                page.selectOptionFromList(page.getDriver().findElement(By.name("RoleId")), "Admin");
            }
            page.enterDataFromExcel(casesPage, emailElement, caseForm.getEmailValue());

            page.enterDataFromExcel(casesPage, cellphoneElement, caseForm.getCellPhoneValue());
            getExtentTest().log(getLogStatus().PASS, "All fields completed successfully." + getExtentTest().addScreenCapture(page.captureScreen()));

            page.switchFocusTo(casesPage, saveButtonElement);
            getExtentTest().log(getLogStatus().PASS, caseForm.getFirstNameValue() + " " + caseForm.getLastNameValue() + "Added successfully." + getExtentTest().addScreenCapture(page.captureScreen()));

        } catch (AssertionError AE) {
            getExtentTest().log(getLogStatus().FAIL, "Field was not mandatory" + getExtentTest().addScreenCapture(page.captureScreen()));
            throw new Exception("Failed to Assert business logic: Campaign Outcome Field was not mandatory");
        }
    }

    public ArrayList<Person> createArrayList(HomePage page, CaseForm caseForm, ArrayList<Person> people, int numRows) {
        String row;
        try {


            for (int i = 1; i <= numRows; i++) {
                row = page.getDriver().findElement(By.cssSelector("body > table > tbody > tr:nth-child(" + i + ")")).getText();

                String[] splited = row.split(" ");
                Person newPerson = new Person();
                people.add(newPerson);
                newPerson.setName(splited[0]);
                newPerson.setLastName(splited[1]);
                newPerson.setUserName(splited[2]);
                newPerson.setCustomer(splited[3]);
                newPerson.setRole(splited[4]);
                newPerson.setEmail(splited[5]);
                newPerson.setCellPhone(splited[6]);
            }
        } catch (Exception e) {
            log.error("Adding people to arrayList Failed. " + e.getMessage());
            //   getExtentTest().log(getLogStatus().FAIL, "Adding people to arrayList Failed." + getExtentTest().addScreenCapture(page.captureScreen()));
        }

        return people;
    }

    public ArrayList<DogBreed> createDogBreedArrayList(ArrayList<DogBreed> dogBreeds, String outputString) {
        try {
            outputString = outputString.replace("{\"status\":\"success\",\"message\":{", ",");
            outputString = outputString.replace("],", "]:[],");
            String[] splited = outputString.split(":\\[\\],");

            for (int i = 0; i < splited.length; i++) {
                DogBreed newDogBreed = new DogBreed();
                dogBreeds.add(newDogBreed);
                String result = splited[i].substring(splited[i].indexOf("\"") + 1, splited[i].indexOf(":[") - 1);
                newDogBreed.setBreed(result);

            }


        } catch (Exception e) {
            log.error("Adding Breed to ArrayList Failed. " + e.getMessage());
        }

        return dogBreeds;
    }

    public ArrayList<SubBreed> createSubBreedArrayList(ArrayList<SubBreed> subBreeds, String outputString) {
        try {
            outputString = outputString.replace("{\"status\":\"success\",\"message\":", "");
            outputString = outputString.replace("[", "");
            outputString = outputString.replace("]}", "");
            outputString = outputString.replace("\"", "");


            String[] splited = outputString.split(",");

            for (int i = 0; i < splited.length; i++) {
                SubBreed newSubBreed = new SubBreed();
                subBreeds.add(newSubBreed);
                newSubBreed.setSubBreed(splited[i]);

            }


        } catch (Exception e) {
            log.error("Adding sub breed to arrayList Failed. " + e.getMessage());
        }

        return subBreeds;
    }

    public Boolean findBreed(ArrayList<DogBreed> people, String breed) {
        Boolean breedFound = false;
        try {
            Iterator<DogBreed> peopleIterator = people.iterator();
            int i = 0;
            while (peopleIterator.hasNext() && breedFound != true && i < people.size()) {
                if (people.get(i).getBreed().equalsIgnoreCase(breed)) {
                    breedFound = true;
                    break;
                } else {
                    breedFound = false;

                }
                i++;
            }
            getExtentTest().log(getLogStatus().PASS, "Breed search was successful.");
        } catch (Exception e) {
            System.out.println("Test failed" + e);
            getExtentTest().log(getLogStatus().FAIL, "Breed Search Test Failed");
        }

        return breedFound;
    }

    public void printBreeds(ArrayList<DogBreed> breeds) {
        try {
            for (int i = 0; i < breeds.size(); i++) {
                getExtentTest().log(getLogStatus().INFO, "Breed: " + (i + 1) + " " + breeds.get(i).getBreed());

            }
        } catch (Exception e) {
            //System.out.println("Test failed" + e);
            getExtentTest().log(getLogStatus().FAIL, "Print Breeds Test Failed");
        }

    }

    public void printSubBreeds(ArrayList<SubBreed> subBreeds) {
        try {
            for (int i = 0; i < subBreeds.size(); i++) {
                getExtentTest().log(getLogStatus().INFO, "Sub-breed: " + (i + 1) + " " + subBreeds.get(i).getSubBreed());

            }
        } catch (Exception e) {
            //System.out.println("Test failed" + e);
            getExtentTest().log(getLogStatus().FAIL, "Print sub-breeds Test Failed");
        }

    }


    public String getStringResponse(String initialUrl) throws IOException {
        String response = "";
        try {

            URL url = new URL(initialUrl);
            URLConnection connection = url.openConnection();
            String responseString = "";
            HttpURLConnection httpConn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            String xmlInput =
                    " <soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:v1='' xmlns:v2='' xmlns:v7=''>\n" +
                            " <soapenv:Header/>\n" +
                            " <soapenv:Body>\n" +
                            " <v1:SendMessageRequest>\n" +
                            " <v2:header> \n" +
                            " <v2:senderID></v2:senderID> \n" +
                            " <v2:messageID></v2:messageID> \n" +
                            " <v2:timestamp></v2:timestamp> \n" +
                            " </v2:header> \n" +
                            " <v1:customerKey> \n" +
                            " </v1:customerKey> \n" +
                            " <v1:type>TEXT_MSG</v1:type> \n" +
                            " <v1:message>/v1:message> \n" +
                            " </v1:SendMessageRequest> \n" +
                            " </soapenv:Body> \n" +
                            " </soapenv:Envelope>";

            byte[] buffer;
            buffer = xmlInput.getBytes();
            try {
                bout.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] b = bout.toByteArray();

            // Set the appropriate HTTP parameters.
            httpConn.setRequestProperty("Content-Length",
                    String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "application/json'");
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStream out = httpConn.getOutputStream();
            //Write the content of the request to the outputstream of the HTTP Connection.
            out.write(b);
            out.close();
            Logger log = Logger.getLogger(BaseTest.class);
            log.info("************ Request Message***********");

            //Read the response.
            InputStreamReader isr =
                    new InputStreamReader(httpConn.getInputStream());
            BufferedReader in = new BufferedReader(isr);

            //Write the SOAP message response to a String.
            while ((responseString = in.readLine()) != null) {
                response = response + responseString;
                System.out.println("Test passed");

            }
        } catch (Exception e) {
            getExtentTest().log(getLogStatus().FAIL, "Get String Response Test Failed");
        }

        return response;
    }

    public String getStringCallStatus(String initialUrl) throws IOException {
        String status = "";
        String response = "";

        try {
            response = getStringResponse(initialUrl);
            status = response.substring(response.indexOf("status") + 9, response.indexOf("message") - 3);
        } catch (Exception e) {
            getExtentTest().log(getLogStatus().FAIL, "Get Status Test Failed");
        }

        return status;
    }

    public String getStringCallMessage(String initialUrl) throws IOException {
        String message = "";
        String response = "";

        try {
            response = getStringResponse(initialUrl);
            message = response.substring(response.indexOf("message") + 10, response.indexOf("jpg\"") + 3);
        } catch (Exception e) {
            getExtentTest().log(getLogStatus().FAIL, "Get Message Test Failed");
        }

        return message;
    }
}