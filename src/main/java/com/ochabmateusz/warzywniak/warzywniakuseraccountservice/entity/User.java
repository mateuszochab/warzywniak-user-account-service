package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.Map;


@Document(collection = "wuser")
public class User {
    //Fields


    @Id
    @Getter
    private String id;


    @Setter
    @Getter
    @Field(value = "userBase")
    private UserBase userBase;


    @Setter
    @Getter
    @Field(value = "confirmed")
    private boolean confirmed;

    @Setter
    @Getter
    @Field(value = "superUser")
    private boolean superUser;

    @Setter
    @Getter
    @Field(value = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Setter
    @Getter
    @Field(value = "premium")
    private Premium premium;


    @Getter
    @Field(value = "listOfConnectedFriends")
    private List<Friend> listOfConnectedFriends;

    @Getter
    @Field(value = "requestConnectionFriendList")
    private Map<Friend, String> requestConnectionFriendList;

    @Getter
    @Field(value = "sendInvitationToFriendList")
    private Map<Friend, String> sendInvitationToFriendList;

    @Getter
    @Setter
    @Field(value = "notFriendAnyLongerList")
    private List<Friend> notFriendAnyLongerList;

    @Getter
    @Field(value = "createdDate")
    private String createdDate;


    @Getter
    @Field(value = "modifiedDates")
    private List<ModifiedDate> modifiedDates;

    @Getter
    @Field(value = "rating")
    private List<Rating> rating;

    @Getter
    @Field(value = "conversations")
    private List<String> conversations;

    @Getter
    @Field(value = "abandonedConversations")
    private List<String> abandonedConversations;


}
