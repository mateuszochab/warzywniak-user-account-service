package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class Rating {


    @Field(value = "starOwner")
    @NotNull
    @NotEmpty
    private String starOwner;

    // @Size(min = 1, max = 5, message = "Rating can be one of following numbers: 1,2,3,4,5")
    @Field(value = "star")
    @NotNull
    @NotEmpty
    private int star;


    @Field(value = "dateOfRating")
    private String dateOfRating;


    //@Size(min = 1, max = 500, message = "Comments cannot be longer than 500 characters")
    @Field(value = "comment")
    private String comment;


}
