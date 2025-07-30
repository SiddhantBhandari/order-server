package com.sbmicroservices.utils;

import com.google.gson.Gson;


public class Mapper {

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }
}
