package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
public class Coordinates {


    @NotEmpty
    @NotNull
    @Field(value = "latitude")
    @Pattern(regexp = "^-?([1-8]?[0-9]\\.{1}\\d{1,6}$|90\\.{1}0{1,6}$)", message = "value has to be in range 0-90")
    private String latitude;

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^-?((([1]?[0-7][0-9]|[1-9]?[0-9])\\.{1}\\d{1,6}$)|[1]?[1-8][0]\\.{1}0{1,6}$)", message = "value has to be in range 0-180")
    @Field(value = "longitude")
    private String longitude;



}
