package com.apoddo.foodstoresearch.loader.model;

import com.apoddo.foodstoresearch.util.enums.FieldName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.elasticsearch.common.geo.GeoPoint;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodStore {

    @JsonProperty("county")
    private String County;
    @JsonProperty("licenseNumber")
    private String License_Number;
    @JsonProperty("establishmentType")
    private String Establishment_Type;
    @JsonProperty("entityName")
    private String Entity_Name;
    @JsonProperty("dbaName")
    private String DBA_Name;
    @JsonProperty("streetNumber")
    private String Street_Number;
    @JsonProperty("streetName")
    private String Street_Name;
    @JsonProperty("city")
    private String City;
    @JsonProperty("stateAbbreviation")
    private String State_Abbreviation;
    @JsonProperty("zipCode")
    private String Zip_Code;
    @JsonProperty("squareFootage")
    private int Square_Footage;
    @JsonProperty("latitude")
    private Double Latitude;
    @JsonProperty("longitude")
    private Double Longitude;

    @JsonProperty("location")
    GeoPoint geoPoint;

    public FoodStore() {
    }

    public FoodStore(String County, String License_Number, String Establishment_Type, String entityName, String dbaName, String streetNumber, String streetName, String city, String stateAbbreviation, String zipCode, int squareFootage, Double latitude, Double longitude) {
        this.County = County;
        this.License_Number = License_Number;
        this.Establishment_Type = Establishment_Type;
        this.Entity_Name = entityName;
        this.DBA_Name = dbaName;
        this.Street_Number = streetNumber;
        this.Street_Name = streetName;
        this.City = city;
        this.State_Abbreviation = stateAbbreviation;
        this.Zip_Code = zipCode;
        this.Square_Footage = squareFootage;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(FieldName.getCounty(), County != null ? County : "");
        map.put(FieldName.getLicenseNumber(), License_Number != null ? License_Number : "");
        map.put(FieldName.getEstablishmentType(), Establishment_Type != null ? Establishment_Type : "");
        map.put(FieldName.getEntityName(), Entity_Name != null ? Entity_Name : "");
        map.put(FieldName.getDbaName(), DBA_Name != null ? DBA_Name : "");
        map.put(FieldName.getStreetNumber(), Street_Number != null ? Street_Number : "");
        map.put(FieldName.getStreetName(), Street_Name != null ? Street_Name : "");
        map.put(FieldName.getCity(), City != null ? City : "");
        map.put(FieldName.getStateAbbreviation(), State_Abbreviation != null ? State_Abbreviation : "");
        map.put(FieldName.getZipCode(), Zip_Code != null ? Zip_Code : "");
        map.put(FieldName.getSquareFootage(), Square_Footage);

        geoPoint = new GeoPoint();
        if (Longitude != null && Latitude != null) {
            geoPoint.reset(Longitude, Latitude);
        }

        map.put("location", geoPoint);

        return map;
    }

    public String getLicense_Number() {
        return License_Number;
    }

    public static class FoodStoreBuilder {
        private String county;
        private String license_Number;
        private String establishment_Type;
        private String entity_Name;
        private String dba_Name;
        private String street_Number;
        private String street_Name;
        private String city;
        private String state_Abbreviation;
        private String zip_Code;
        private int square_Footage;
        private Double latitude;
        private Double longitude;

        public FoodStoreBuilder() {
        }

        public FoodStoreBuilder setCounty(String county) {
            this.county = county;
            return this;
        }

        public FoodStoreBuilder setLicense_Number(String license_Number) {
            this.license_Number = license_Number;
            return this;
        }

        public FoodStoreBuilder setEstablishment_Type(String establishment_Type) {
            this.establishment_Type = establishment_Type;
            return this;
        }

        public FoodStoreBuilder setEntity_Name(String entity_Name) {
            this.entity_Name = entity_Name;
            return this;
        }

        public FoodStoreBuilder setDBA_Name(String dba_Name) {
            this.dba_Name = dba_Name;
            return this;
        }

        public FoodStoreBuilder setStreet_Number(String street_Number) {
            this.street_Number = street_Number;
            return this;
        }

        public FoodStoreBuilder setStreet_Name(String street_Name) {
            this.street_Name = street_Name;
            return this;
        }

        public FoodStoreBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public FoodStoreBuilder setState_Abbreviation(String state_Abbreviation) {
            this.state_Abbreviation = state_Abbreviation;
            return this;
        }

        public FoodStoreBuilder setZip_Code(String zip_Code) {
            this.zip_Code = zip_Code;
            return this;
        }

        public FoodStoreBuilder setSquare_Footage(int square_Footage) {
            this.square_Footage = square_Footage;
            return this;
        }

        public FoodStoreBuilder setLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public FoodStoreBuilder setLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public FoodStore build() {
            FoodStore foodStore = new FoodStore();
            foodStore.County = this.county;
            foodStore.License_Number = this.license_Number;
            foodStore.Establishment_Type = this.establishment_Type;
            foodStore.Entity_Name = this.entity_Name;
            foodStore.DBA_Name = this.dba_Name;
            foodStore.Street_Number = this.street_Number;
            foodStore.Street_Name = this.street_Name;
            foodStore.City = this.city;
            foodStore.State_Abbreviation = this.state_Abbreviation;
            foodStore.Zip_Code = this.zip_Code;
            foodStore.Square_Footage = this.square_Footage;
            foodStore.Latitude = this.latitude;
            foodStore.Longitude = this.longitude;
            return foodStore;
        }
    }

}
