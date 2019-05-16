package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class test {

    public static void main(String[] args) {
        Map<String, String> mapa = new HashMap<>();


        mapa.put("id", "abc");
        mapa.put("code", "okon");



        List<String> lista = mapa.entrySet().stream()
                .filter(h -> h.getKey().equals("i"))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        lista.forEach(System.out::println);
    }
}
