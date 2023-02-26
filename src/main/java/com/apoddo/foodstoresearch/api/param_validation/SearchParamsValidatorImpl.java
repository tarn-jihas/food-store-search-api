package com.apoddo.foodstoresearch.api.param_validation;

import com.apoddo.foodstoresearch.api.param_parser.model.SearchParams;
import org.springframework.stereotype.Service;
/**
 * Service for validating search parameters.
 */
@Service
public class SearchParamsValidatorImpl implements ISearchParamsValidator{
    /**
     * Validates the given search parameters, and throws an exception if any validation fails.
     *
     * @param params The search parameters to validate.
     * @param searchByPartialNameOrAddress Whether the search is being performed by partial name/address or not.
     * @throws IllegalArgumentException If any validation fails.
     */
    public void validate(SearchParams params, boolean searchByPartialNameOrAddress) {
        if(searchByPartialNameOrAddress) {
            validateBatchSize(params.getBatchSize());
            validateInputQuery(params.getPartialQuery());
        }else{
            validateLatitude(params.getLatitude());
            validateLongitude(params.getLongitude());
            validateDistance(params.getDistance());
            validateBatchSize(params.getBatchSize());
        }

    }
    /**
     * Validates the given latitude, and throws an exception if the validation fails.
     *
     * @param latitude The latitude to validate.
     * @throws IllegalArgumentException If the validation fails.
     */
    public void validateLatitude(double latitude) {
        String lat  = Double.toString(latitude);
        if (!lat.matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$")) {
            throw new IllegalArgumentException("Invalid input parameter: latitude and longitude must be valid numbers in decimal degrees format, with up to 6 decimal places.");
        }
    }
    /**
     * Validates the given longitude, and throws an exception if the validation fails.
     *
     * @param longitude The longitude to validate.
     * @throws IllegalArgumentException If the validation fails.
     */
    public void validateLongitude(double longitude) {
        String lon  = Double.toString(longitude);
        if (!lon.matches("^[-+]?([1-9]?\\d(\\.\\d+)?|1[0-7]\\d(\\.\\d+)?|180(\\.0+)?)$")) {
            throw new IllegalArgumentException("Invalid input parameter: latitude and longitude must be valid numbers in decimal degrees format, with up to 6 decimal places.");
        }

    }
    /**
     * Validates the given distance, and throws an exception if the validation fails.
     *
     * @param distance The distance to validate.
     * @throws IllegalArgumentException If the validation fails.
     */
    public void validateDistance(int distance) {
        String dist  = Integer.toString(distance);
        if(!dist.matches("^[1-9][0-9]*$")) {
            throw new IllegalArgumentException("Invalid optional query parameter: distance must be a positive number starting from 1. If the distance is not specified or added to the call, the default will be 1");
        }

    }
    /**
     * Validates the given batch size, and throws an exception if the validation fails.
     *
     * @param batchSize The batch size to validate.
     * @throws IllegalArgumentException If the validation fails.
     */
    public void validateBatchSize(int batchSize) {
        String dist  = Integer.toString(batchSize);
        if(!dist.matches("^[1-9][0-9]{0,3}(,[0-9]{3})*$|10000")){
            throw new IllegalArgumentException("Invalid optional query parameter: batchSize batchSize must be a positive number starting from 1 up to and including 10000. If the batchSize is not specified or added to the call, the default will be 10");
        }

    }
    /**
     * Validates the input query, and throws an exception if the validation fails.
     *
     * @param inputQuery The batch size to validate.
     * @throws IllegalArgumentException If the validation fails.
     */
    public void validateInputQuery(String inputQuery) {
        if (inputQuery == null || inputQuery.trim().isEmpty()) throw new IllegalArgumentException("Missing required query parameter: inputParam");

    }

}
