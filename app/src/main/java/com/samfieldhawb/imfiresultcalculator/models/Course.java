package com.samfieldhawb.imfiresultcalculator.models;

public class Course {
    private String code;
    private String title;
    private int credit_unit;
    private int score = 0;

    public Course() {
    }

    public Course(String code, String title, int credit_unit) {
        this.code = code;
        this.title = title;
        this.credit_unit = credit_unit;
    }

    public Course(String code, String title, int creditUnit, int score) {
        this.code = code;
        this.title = title;
        this.credit_unit = creditUnit;
        this.score = score;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredit_unit() {
        return credit_unit;
    }

    public void setCredit_unit(int credit_unit) {
        this.credit_unit = credit_unit;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public String getGrade(){
        int score = this.score;
       if(score > 75 ){
           return "A";
       }else if(score >70){
           return "AB";
       }else if(score > 65){
           return "B";
       }else if(score > 60){
           return "BC";
       }
       else if(score > 55){
           return "C";
       }
       else if(score > 50){
           return "CD";
       }else if(score > 45){
           return "D";
       }else if(score > 40){
           return "E";
       }
       else{
           return "F";
       }
    }

}
