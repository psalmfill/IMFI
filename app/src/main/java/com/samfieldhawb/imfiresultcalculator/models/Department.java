package com.samfieldhawb.imfiresultcalculator.models;

public class Department {
    private String name;
    private String short_code;

    public Department() {
    }


    public String getName() {
        return name;
    }

    public String getShort_code() {
        return short_code;
    }


    @Override
    public String toString() {
        return name;
    }
}
