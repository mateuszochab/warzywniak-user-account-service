package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository;

import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public interface ValidationRepository {



    boolean twoValidItemsInRequest(String  item1, String item2, Map<String, Object> map);


    boolean oneValidItemsInRequest(String id, Map<String, Object> requestBody);
}
