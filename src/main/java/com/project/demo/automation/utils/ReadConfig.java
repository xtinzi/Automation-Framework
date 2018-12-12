package com.project.demo.automation.utils;

import java.util.ArrayList;


public class ReadConfig {
    private int lastRow=-1;
    private int fromRow=-1;
    private int upToRow=-1;


    public ReadConfig(ArrayList<String> configFields){
        int LAST_ROW=0;
        int FROM_ROW=1;
        int UP_TO_ROW=2;
        setLastRow(Integer.valueOf(configFields.get(LAST_ROW)));
        setFromRow(Integer.valueOf(configFields.get(FROM_ROW)));
        setUpToRow(Integer.valueOf(configFields.get(UP_TO_ROW)));
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getFromRow() {
        return fromRow;
    }

    public void setFromRow(int fromRow) {
        this.fromRow = fromRow;
    }

    public int getUpToRow() {
        return upToRow;
    }

    public void setUpToRow(int upToRow) {
        this.upToRow = upToRow;
    }

}
