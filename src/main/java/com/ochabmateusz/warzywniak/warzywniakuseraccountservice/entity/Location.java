package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Location {


    @NonNull
    @NotEmpty
    @Size(min = 2, message = "City should have atleast 2 characters")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Accept only uppercase lowercase characters")
    @Field(value = "city")
    private  String city;

    @NonNull
    @NotEmpty
    @Field(value = "coordinates")
    @Valid
    private Coordinates coordinates;



    @Field(value = "street")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Accept only uppercase lowercase characters")
    private String street=null;


    @Field(value = "numberOfHouse")
    private int numberOfHouse=0;

    @Field(value = "country")
    @Enumerated(EnumType.STRING)
    private Country country=Country.INITIAL;

    @Field(value = "voivodeship")
    @Enumerated(EnumType.STRING)
    private Voivodeship voivodeship=Voivodeship.INITIAL;


    @Field(value = "typeOfProduct")
    @Enumerated(EnumType.STRING)
    private List<TypeOfProduct> typeOfProduct;


}
