package com.samfieldhawb.imfiresultcalculator.models;

public class Faculty {
    private String name;
    private String short_code;

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
