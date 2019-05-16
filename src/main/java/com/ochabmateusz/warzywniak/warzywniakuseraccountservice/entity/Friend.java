package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Friend {



    @Field(value = "idConnectedFriend")
    private String idConnectedFriend;


    @Field(value = "connected")
    private boolean connected;

    @Field(value = "startBeingFriendDate")
    private String startBeingFriendDate;

    @Field(value = "stopBeingFriendDate")
    private String stopBeingFriendDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return idConnectedFriend.equals(friend.idConnectedFriend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConnectedFriend);
    }
}