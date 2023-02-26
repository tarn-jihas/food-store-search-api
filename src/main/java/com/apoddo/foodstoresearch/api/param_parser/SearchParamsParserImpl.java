package com.apoddo.foodstoresearch.api.param_parser;

import com.apoddo.foodstoresearch.api.param_parser.model.SearchParams;
import org.springframework.stereotype.Service;
/**
 * Implementation of the {@link ISearchParamsParser} interface that can parse search parameters
 * and return a {@link SearchParams} object. This class provides methods for parsing latitude,
 * longitude, distance, and batch size from string inputs.
 */
@Service
public class SearchParamsParserImpl implements ISearchParamsParser{

    /**
     * Parses the latitude, longitude, distance, and batch size from the given strings and
     * returns a new {@link SearchParams} object.
     *
     * @param latitude the latitude string to parse
     * @param longitude the longitude string to parse
     * @param distance the distance string to parse
     * @param batchSize the batch size string to parse
     * @return a new {@link SearchParams} object with the parsed parameters
     */
    @Override
    public SearchParams parse(String latitude, String longitude, String distance, String batchSize) {
        double lat = parseLatitude(latitude);
        double lon = parseLongitude(longitude);
        int dist = parseDistance(distance);
        int size = parseBatchSize(batchSize);
        return new SearchParams.Builder().latitude(lat).longitude(lon).distance(dist).batchSize(size).build();
    }
    /**
     * Parses the partial query and batch size from the given strings and returns a new
     * {@link SearchParams} object.
     *
     * @param partialQuery the partial query string to parse
     * @param batchSize the batch size string to parse
     * @return a new {@link SearchParams} object with the parsed parameters
     */
    @Override
    public SearchParams parse(String partialQuery, String batchSize) {
        int size = parseBatchSize(batchSize);
        String query = partialQuery;
        return new SearchParams.Builder().batchSize(size).partialQuery(query).build();
    }
    /**
     * Parses the latitude from the given string and returns a double value.
     *
     * @param latitude the latitude string to parse
     * @return the parsed latitude as a double value
     */
    @Override
    public double parseLatitude(String latitude) {
        return Double.parseDouble(latitude);
    }
    /**
     * Parses the longitude from the given string and returns a double value.
     *
     * @param longitude the longitude string to parse
     * @return the parsed longitude as a double value
     */
    @Override
    public double parseLongitude(String longitude) {
        return Double.parseDouble(longitude);
    }
    /**
     * Parses the distance from the given string and returns an integer value.
     *
     * @param distance the distance string to parse
     * @return the parsed distance as an integer value
     */
    @Override
    public int parseDistance(String distance) {
        return Integer.parseInt(distance);
    }
    /**
     * Parses the batch size from the given string and returns an integer value.
     *
     * @param batchSize the batch size string to parse
     * @return the parsed batch size as an integer value
     */
    @Override
    public int parseBatchSize(String batchSize) {
        return Integer.parseInt(batchSize);
    }
}
