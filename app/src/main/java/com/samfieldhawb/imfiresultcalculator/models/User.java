package com.samfieldhawb.imfiresultcalculator.models;

public class User {

    private  String firstName;

    private String lastName;

    private String middleName;

    private String gender;

    private String phoneNumber;

    private String email;

    private String registrationNumber;

//    private String faculty;
//
//    private String department;

    private String mode;

    private String role;

    private Faculty faculty;

    private Department department;

    public User() {
    }

    public User(String firstName, String lastName, String middleName, String gender, String phoneNumber, String email, String registrationNumber, Faculty faculty, Department department, String mode, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.registrationNumber = registrationNumber;
        this.faculty = faculty;
        this.department = department;
        this.mode = mode;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
