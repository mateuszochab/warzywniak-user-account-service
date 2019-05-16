package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.service;

import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository.ValidationRepository;

import java.util.Map;

public class ValidationService implements ValidationRepository {
    @Override
    public boolean twoValidItemsInRequest(String item1, String item2, Map<String, Object> map) throws NoSuchFieldException {


        //check out if data from request are valid
        if (map.containsKey(item1) && map.containsKey(item2)) {

            return true;

            //            id = (String) requestBody.get("id");
//            name = (String) requestBody.get("name");
        } else {
            throw new NoSuchFieldException("request does not contains required fields");
        }

    }

    @Override
    public boolean oneValidItemsInRequest(String id, Map<String, Object> map) throws NoSuchFieldException {
        if (map.containsKey(id)) {
            return true;
        }else{
            throw new NoSuchFieldException("request does not contains required fields");
        }
    }
}
