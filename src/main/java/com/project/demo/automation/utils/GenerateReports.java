package com.project.demo.automation.utils;


public class GenerateReports {

   StringBuilder html = new StringBuilder();

    public GenerateReports(String title) {
        html.append("<!doctype html>\n");
        html.append("<html lang='en'>\n");
        html.append("<head>\n");
        html.append("<meta charset='utf-8'>\n");
        html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"'>\n");
        html.append("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\">\n");
        html.append("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css\">\n");
        html.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js\">\n</script>");
        html.append("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\">\n</script>");
        html.append("<style type=\"text/css\">\n.style-table{margin:20px;}\"</style>\"");
        html.append("<title>" + title + "</title>\n");
        html.append("</head>\n\n");
        html.append("<body>\n");
        html.append("<img src=\"First_National_Bank_Logo.png\" alt=\\\"Smiley face\\\" height=\\\"143\\\" width=\\\"352\\>");
        html.append("<h1>" + title + "</h1>\n");
        html.append("<div class=\"style-table\" >\n");
        html.append("<table border=\"4px solid black\" style=\"width:50%\"  cellpadding=\"3\" cellspacing=\"0\">\n");
        html.append("<table class=\"table table-striped\">\n");

    }

    public void appendTable(String csvString) {
        String[] rowValues = csvString.split(",");
        html.append("<tr>\n");
        for (int i = 0; i < rowValues.length; i++) {
            html.append("<td><font size=\"2\">" + rowValues[i] + "</td>\n");
        }
        html.append("</tr>\n");
    }

    public String appendAdditionalData(String data){
        StringBuilder statTable = new StringBuilder();

        statTable.append("<table border=\"4px solid black\" style=\"width:50%\"  cellpadding=\"3\" cellspacing=\"0\">\n");
        statTable.append("<tr>\n");
        statTable.append("<td><font size=\"2\">" + "Overall Test Status" + "</td>\n");
        statTable.append("<td><font size=\"2\">" + data + "</td>\n");
        statTable.append("</tr>\n");
        statTable.append("</table>\n");
        return statTable.toString();
    }

    public String appendStats(String overallStatus, String numberOfTests, String failedTests, String passedTests, String passPercentage) {
        StringBuilder statTable = new StringBuilder();

        statTable.append("<table border=\"4px solid black\" style=\"width:50%\"  cellpadding=\"3\" cellspacing=\"0\">\n");
        statTable.append("<tr>\n");
        statTable.append("<td><font size=\"2\">" + "Overall Test Status" + "</td>\n");
        statTable.append("<td><font size=\"2\">" + overallStatus + "</td>\n");
        statTable.append("</tr>\n");
        statTable.append("<tr>\n");
        statTable.append("<td><font size=\"2\">" + "Total Number of Tests" + "</td>\n");
        statTable.append("<td><font size=\"2\">" + numberOfTests + "</td>\n");
        statTable.append("</tr>\n");
        statTable.append("<tr>\n");
        statTable.append("<td><font size=\"2\">" + "Failed Tests" + "</td>\n");
        statTable.append("<td bgcolo><font size=\"2\">" + failedTests + "</td>\n");
        statTable.append("</tr>\n");
        statTable.append("<tr>\n");
        statTable.append("<td><font size=\"2\">" + "Passed Tests" + "</td>\n");
        statTable.append("<td><font size=\"2\">" + passedTests + "</td>\n");
        statTable.append("</tr>\n");
        statTable.append("<tr>\n");
        statTable.append("<td><font size=\"2\">" + "Pass Percentage" + "</td>\n");
        statTable.append("<td><font size=\"2\">" + passPercentage + "</td>\n");
        statTable.append("</tr>\n");
        statTable.append("</table>\n");
        statTable.append("<p>" + "Test Results" + "</p>\n");
        statTable.append("<table border=\"4px solid black\" style=\"width:50%\"  cellpadding=\"3\" cellspacing=\"0\">\n");

        return statTable.toString();
    }

    public void appendHeadings(String csvString) {
        String[] rowValues = csvString.split(",");
        html.append("<tr>\n");
        for (int i = 0; i < rowValues.length; i++) {
            html.append("<th><font size=\"4\">" + rowValues[i] + "</th>\n");
        }
        html.append("</tr>\n");
    }

}
