package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
public class ModifiedDate{


    @Field(value = "modifiedDate")
    private String modifiedDate;


    @Field(value = "modificationType")
    @Enumerated(EnumType.STRING)
    private ModificationType modificationType;


}
