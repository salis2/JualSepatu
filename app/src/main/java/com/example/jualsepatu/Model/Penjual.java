package com.example.jualsepatu.Model;

public class Penjual {
    private String id;
    private String name;
    private String email;
    private String noTelepon;

    public Penjual() {
    }

    public Penjual(String id, String name, String email, String noTelepon) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.noTelepon = noTelepon;
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

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String value) {
        this.noTelepon = value;
    }

}
