package com.apoddo.foodstoresearch.api.param_parser;

import com.apoddo.foodstoresearch.api.param_parser.model.SearchParams;

public interface ISearchParamsParser {
    SearchParams parse(String latitude, String longitude, String distance, String batchSize);
    SearchParams parse(String partialQuery, String batchSize);
    double parseLatitude(String latitude);

    double parseLongitude(String longitude);

    int parseDistance(String distance);

    int parseBatchSize(String batchSize);

}
