package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
public class ModifiedDate{


    @Getter
    @Field(value = "modifiedDate")
    private String modifiedDate;

    @Getter
    @Field(value = "modificationType")
    @Enumerated(EnumType.STRING)
    private ModificationType modificationType;


}
