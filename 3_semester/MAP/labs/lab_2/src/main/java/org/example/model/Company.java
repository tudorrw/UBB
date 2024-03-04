package org.example.model;

public class Company {
    private int id;
    private String name;
    private int capitalization;

    public Company(int id, String name, int capitalization) {
        this.id = id;
        this.name = name;
        this.capitalization = capitalization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapitalization() {
        return capitalization;
    }

    public void setCapitalization(int capitalization) {
        this.capitalization = capitalization;
    }
}
