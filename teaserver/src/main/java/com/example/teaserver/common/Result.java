package com.example.teaserver.common;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {

    public static Result ok(Object data) {
        Result r = new Result();
        r.put("code", 0);
        r.put("data", data);
        return r;
    }

    public static Result ok() {
        return ok(null);
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.put("code", 1);
        r.put("msg", msg);
        return r;
    }
}