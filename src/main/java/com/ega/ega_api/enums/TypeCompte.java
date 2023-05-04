package com.ega.ega_api.enums;



public enum TypeCompte {
    EPARGNE("Epargne"),
    COURANT("Courant");

    private final String label;

    TypeCompte(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}