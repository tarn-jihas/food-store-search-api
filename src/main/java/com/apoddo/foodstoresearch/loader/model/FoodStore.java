package com.apoddo.foodstoresearch.loader.model;

import com.apoddo.foodstoresearch.util.enums.FieldName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.common.geo.GeoPoint;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

}
