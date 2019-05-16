package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository;

import java.util.Map;

public interface ValidationRepository {



    boolean twoValidItemsInRequest(String  item1, String item2, Map<String, Object> map) throws NoSuchFieldException;


    boolean oneValidItemsInRequest(String id, Map<String, Object> requestBody) throws NoSuchFieldException;
}
