package com.example.main.services;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseMapper {

    public Map<String, String> messageToResponse(String msg){
        Map<String, String> ret = new HashMap<>();
        ret.put("message", msg);
        return ret;
    }
}
