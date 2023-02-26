package com.apoddo.foodstoresearch.api;

import com.apoddo.foodstoresearch.loader.model.FoodStore;
import com.apoddo.foodstoresearch.storage.IFoodStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


/**
 * This class represents a service for performing search operations on FoodStore objects.
 * It delegates the search operations to an instance of the IFoodStoreRepository interface.
 */

@Service
public class FoodStoreServiceImpl implements IFoodStoreService{
    /**
     * Constructs a new FoodStoreService object.
     *
     * @param repository an instance of the IFoodStoreRepository interface
     */

    private final IFoodStoreRepository repository;

    @Autowired
    public FoodStoreServiceImpl(IFoodStoreRepository repository) {
        this.repository = repository;
    }

    /**
     * Searches for the nearest food stores to the given latitude and longitude within a specified distance.
     *
     * @param latitude  the latitude to search around
     * @param longitude the longitude to search around
     * @param distance  the distance in kilometers to search within
     * @param batchSize the size of the query which will be returned
     * @return a list of FoodStore objects that match the search criteria
     */
    public List<FoodStore> searchNearestStores(double latitude, double longitude, int distance, int batchSize) throws IOException {
        return repository.searchNearestStore(latitude, longitude, distance, batchSize);
    }

    /**
     * Searches for food stores whose name or address partially matches the given search query.
     *
     * @param partialNameOrAddress the search query
     * @param batchSize            the size of the query which will be returned
     * @return a list of FoodStore objects that match the search criteria
     */
    public List<FoodStore> searchByPartialNameOrAddress(String partialNameOrAddress, int batchSize) {
        return repository.searchByPartialNameOrAddress(partialNameOrAddress, batchSize);
    }
}