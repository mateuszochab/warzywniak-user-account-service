package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;


@Setter
@Getter
@ToString
@AllArgsConstructor
public class Premium {


    @Field(value = "isActive")
    private boolean isActive;

    @Field(value = "startDatePremium")
    private String startDatePremium;

    @Field(value = "endDatePremium")
    private String endDatePremium;


}
