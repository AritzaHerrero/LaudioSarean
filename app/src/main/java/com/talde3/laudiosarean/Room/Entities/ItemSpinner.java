package com.talde3.laudiosarean.Room.Entities;

public class ItemSpinner {
    private int id;
    private String gunea;

    // Constructor
    public ItemSpinner(int id, String gunea) {
        this.id = id;
        this.gunea = gunea;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getGunea() {
        return gunea;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setGunea(String gunea) {
        this.gunea = gunea;
    }

    @Override
    public String toString() {
        return gunea;
    }
}
