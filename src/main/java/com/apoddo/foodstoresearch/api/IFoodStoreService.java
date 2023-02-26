package com.apoddo.foodstoresearch.api;

import com.apoddo.foodstoresearch.loader.model.FoodStore;

import java.io.IOException;
import java.util.List;

public interface IFoodStoreService {
    List<FoodStore> searchNearestStores(double latitude, double longitude, int distance, int batchSize) throws IOException;
    List<FoodStore> searchByPartialNameOrAddress(String partialNameOrAddress, int batchSize);
}
