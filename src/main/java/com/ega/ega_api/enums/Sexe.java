package com.ega.ega_api.enums;

public enum Sexe {
    HOMME("M"),
    FEMME("F");

    private final String label;

    Sexe(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
