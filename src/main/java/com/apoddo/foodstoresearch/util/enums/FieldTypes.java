package com.apoddo.foodstoresearch.util.enums;

public enum FieldTypes {
    TEXT("text"),
    KEYWORD("keyword"),
    FLOAT("float"),
    GEOPOINT("geo_point");


    private final String fieldType;
    FieldTypes(String fieldType){
        this.fieldType = fieldType;

    }


    public static String getText() {
        return TEXT.fieldType;
    }

    public static String getKeyword() {
        return KEYWORD.fieldType;
    }

    public static String getFloat() {
        return FLOAT.fieldType;
    }

    public static String getGeoPoint() {
        return GEOPOINT.fieldType;
    }
}
