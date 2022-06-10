package com.example.jualsepatu.Model;

public class Pembeli {
    private String id;
    private String name;
    private String email;

    public Pembeli() {
    }

    public Pembeli(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getID() {
        return id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }
}
