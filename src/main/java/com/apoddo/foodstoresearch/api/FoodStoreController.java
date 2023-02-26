package com.apoddo.foodstoresearch.api;

import com.apoddo.foodstoresearch.api.param_parser.ISearchParamsParser;
import com.apoddo.foodstoresearch.api.param_parser.model.SearchParams;
import com.apoddo.foodstoresearch.api.param_validation.ISearchParamsValidator;
import com.apoddo.foodstoresearch.loader.model.FoodStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * This class handles HTTP requests for food store search functionality.
 * The FoodStoreController can perform a search for the nearest food stores to a given latitude and longitude within a specified distance,
 * or search for food stores by a partial name or address criteria.
 */
@RestController
public class FoodStoreController {

    private final IFoodStoreService service;

    private final ISearchParamsParser parser;


    private final ISearchParamsValidator validator;

    /**
     * This constructor creates an instance of FoodStoreController.
     *
     * @param service an instance of FoodStoreService used to perform food store searches
     */
    @Autowired
    public FoodStoreController(IFoodStoreService service,
                               ISearchParamsParser parser,
                               ISearchParamsValidator validator) {
        this.service = service;
        this.parser = parser;
        this.validator = validator;
    }


    /**
     * This method handles GET requests for searching for the nearest food stores to a given latitude and longitude within a specified distance.
     *
     * @param latitude contains the latitude for the query
     * @param longitude contains the longitude for the query
     * @param distance contains the distance around which to look for stores. Default is 1.
     * @param batchSize contains the size limit of the returned query. Default is 10, max is 10 000.
     * @return a list of FoodStore objects that match the search criteria
     */
    @GetMapping("/searchNearestStores")
    public <T> ResponseEntity<T> searchNearestStores(@RequestParam("latitude") String latitude,
                                                     @RequestParam("longitude") String longitude,
                                                     @RequestParam(value = "distance", required = false, defaultValue = "1") String distance,
                                                     @RequestParam(value = "batchSize", required = false, defaultValue = "10") String batchSize) throws IOException {

        // parse the parameters
        SearchParams params = parser.parse(latitude, longitude, distance, batchSize);
        validator.validate(params, false);
        // validate the parameters
        List<FoodStore> results = service.searchNearestStores(params.getLatitude(), params.getLongitude(), params.getDistance(), params.getBatchSize());
        if (results.isEmpty()) return (ResponseEntity<T>) ResponseEntity.ok("Query returned 0 hits");
        return (ResponseEntity<T>) ResponseEntity.ok(results);

    }


    /**
     * This method handles GET requests for searching for food stores by a partial name or address criteria.
     *
     * @param inputParam the partial name or address criteria to search for
     * @param batchSize  the size of the query which will be returned. Default is 10, maximum is 10000.
     * @return a list of FoodStore objects that match the search criteria
     */
    @GetMapping("/searchByPartialNameOrAddress")
    public <T> ResponseEntity<T> searchByPartialNameOrAddress(@RequestParam(value = "inputParam") String inputParam,
                                                              @RequestParam(value = "batchSize", required = false, defaultValue = "10") String batchSize) {
        // parse the parameters
        SearchParams params = parser.parse(inputParam, batchSize);
        // validate the parameters
        validator.validate(params, true);

        List<FoodStore> results = service.searchByPartialNameOrAddress(params.getPartialQuery(), params.getBatchSize());
        if (results.isEmpty()) return (ResponseEntity<T>) ResponseEntity.ok("Query returned 0 hits");
        return (ResponseEntity<T>) ResponseEntity.ok(results);

    }
}
