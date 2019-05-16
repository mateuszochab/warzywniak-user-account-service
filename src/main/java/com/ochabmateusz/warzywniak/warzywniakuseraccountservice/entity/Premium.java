package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;


@Setter
@Getter
public class Premium {


    @Field(value = "isActive")
    private boolean isActive;

    @Field(value = "startDatePremium")
    private String startDatePremium;

    @Field(value = "endDatePremium")
    private String endDatePremium;


}
