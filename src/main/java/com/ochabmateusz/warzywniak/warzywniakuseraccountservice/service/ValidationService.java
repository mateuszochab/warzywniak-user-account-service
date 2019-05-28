package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.service;

import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository.ValidationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class ValidationService implements ValidationRepository {



    @Override
    public boolean twoValidItemsInRequest(String item1, String item2, Map<String, Object> map) {
        if(map.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JSON malformed- request is empty");
        }
        if (!(map.size() == 2)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JSON malformed- request does not contains 2 required fields");
        }




        //check out if data from request are valid
        if(map.containsKey(item1) && map.containsKey(item2)) {

            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JSON malformed- atleast one of fields in request is wrong");
        }

    }

    @Override
    public boolean oneValidItemsInRequest(String id, Map<String, Object> map) {
        if(map.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JSON malformed- request is empty");
        }

        if (map.containsKey(id)) {
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JSON malformed- request does not contains required fields");
        }
    }
}
