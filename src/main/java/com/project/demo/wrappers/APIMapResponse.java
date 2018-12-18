package com.project.demo.wrappers;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;


public class APIMapResponse extends APIResponse {
    public LinkedTreeMap<String, List<String>> message;
}
