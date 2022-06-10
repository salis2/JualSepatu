package com.example.jualsepatu.Model;


import java.util.ArrayList;

public class Sepatu {
    private String key;
    private String id;
    private String name;
    private String brand;
    private String type;
    private ArrayList<String> size;
    private String idPenjual;
    private String namaPenjual;
    private String emailPenjual;
    private String noTeleponPenjual;

    public Sepatu() {
    }

    public Sepatu(String id, String name, String brand, String type, ArrayList<String> size, String idPenjual, String namaPenjual, String emailPenjual, String noTeleponPenjual) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.size = size;
        this.idPenjual = idPenjual;
        this.namaPenjual = namaPenjual;
        this.emailPenjual = emailPenjual;
        this.noTeleponPenjual = noTeleponPenjual;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String value) {
        this.key = value;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String value) {
        this.brand = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public ArrayList<String> getSize() {
        return size;
    }

    public void setSize(ArrayList<String> value) {
        this.size = value;
    }

    public String getIDPenjual() {
        return idPenjual;
    }

    public void setIDPenjual(String value) {
        this.idPenjual = value;
    }

    public String getNamaPenjual() {
        return namaPenjual;
    }

    public void setNamaPenjual(String value) {
        this.namaPenjual = value;
    }

    public String getEmailPenjual() {
        return emailPenjual;
    }

    public void setEmailPenjual(String value) {
        this.emailPenjual = value;
    }

    public String getNoTeleponPenjual() {
        return noTeleponPenjual;
    }

    public void setNoTeleponPenjual(String value) {
        this.noTeleponPenjual = value;
    }
}
