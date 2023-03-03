package com.epf.rentmanager.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Client {

    private int id;
    private String name;
    private String last_name;
    private String email;
    private LocalDate birthday;

    public Client(int id, String lastName, String name, String email, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.last_name = lastName;
        this.id = id;
    }

    public Client(String lastName, String name, String email, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.last_name = lastName;
    }

    public Client(){

    }




    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "The client's name is " + name;
    }
}
