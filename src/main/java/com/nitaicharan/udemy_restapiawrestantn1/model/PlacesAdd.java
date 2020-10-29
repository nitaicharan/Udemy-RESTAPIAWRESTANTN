package com.nitaicharan.udemy_restapiawrestantn1.model;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlacesAdd {
    private Map<String, Double> location;
    private int accuracy;
    private String name;
    private String phoneNumber;
    private String address;
    private List<String> types;
    private String website;
    private String language;
}