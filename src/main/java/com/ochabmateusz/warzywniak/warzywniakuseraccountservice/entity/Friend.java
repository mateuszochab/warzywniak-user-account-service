package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;
@Getter
@Setter
@AllArgsConstructor
public class Friend {



    @Field(value = "idConnectedFriend")
    private String idConnectedFriend;


    @Field(value = "connected")
    private boolean connected;
}