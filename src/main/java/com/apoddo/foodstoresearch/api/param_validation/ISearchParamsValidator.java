package com.apoddo.foodstoresearch.api.param_validation;

import com.apoddo.foodstoresearch.api.param_parser.model.SearchParams;


public interface ISearchParamsValidator {
     void validate(SearchParams params, boolean searchByPartialNameOrAddress);
     void validateLatitude(double latitude);

     void validateLongitude(double longitude);

     void validateDistance(int distance);

     void validateBatchSize(int batchSize);
}
