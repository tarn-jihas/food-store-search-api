package com.apoddo.foodstoresearch.util.enums;

public enum FieldName {
    COUNTY("county"),
    LICENSE_NUMBER("licenseNumber"),
    ESTABLISHMENT_TYPE("establishmentType"),
    ENTITY_NAME("entityName"),
    DBA_NAME("dbaName"),
    STREET_NUMBER("streetNumber"),
    STREET_NAME("streetName"),
    CITY("city"),
    STATE_ABBREVIATION("stateAbbreviation"),
    ZIP_CODE("zipCode"),
    LOCATION("location"),
    TEXT("text"),
    KEYWORD("keyword"),
    GEO_POINT("geo_point"),
    SQUARE_FOOTAGE("squareFootage");
    private final String fieldName;


    FieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public static String getCounty() {
        return COUNTY.fieldName;
    }

    public static String getLicenseNumber() {
        return LICENSE_NUMBER.fieldName;
    }

    public static String getEstablishmentType() {
        return ESTABLISHMENT_TYPE.fieldName;
    }

    public static String getEntityName() {
        return ENTITY_NAME.fieldName;
    }

    public static String getDbaName() {
        return DBA_NAME.fieldName;
    }

    public static String getStreetNumber() {
        return STREET_NUMBER.fieldName;
    }

    public static String getStreetName() {
        return STREET_NAME.fieldName;
    }

    public static String getCity() {
        return CITY.fieldName;
    }

    public static String getStateAbbreviation() {
        return STATE_ABBREVIATION.fieldName;
    }

    public static String getZipCode() {
        return ZIP_CODE.fieldName;
    }

    public static String getLocation() {
        return LOCATION.fieldName;
    }

    public static String getText() {
        return TEXT.fieldName;
    }

    public static String getKeyword() {
        return KEYWORD.fieldName;
    }

    public static String getGeoPoint() {
        return GEO_POINT.fieldName;
    }

    public static String getSquareFootage() {
        return SQUARE_FOOTAGE.fieldName;
    }
}
