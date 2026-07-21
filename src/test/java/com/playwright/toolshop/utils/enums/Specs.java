package com.playwright.toolshop.utils.enums;

public enum Specs {
    HANDLE_MATERIAL("Handle Material"),
    LENGTH("Length"),
    MATERIAL("Material"),
    WARRANTY("Warranty"),
    WEIGHT("Weight");

    private final String specName;

    Specs(String specName) {
        this.specName = specName;
    }

    public String getSpecName(){
        return specName;
    }
}
