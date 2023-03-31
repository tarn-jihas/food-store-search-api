package com.apoddo.foodstoresearch.storage;

import com.apoddo.foodstoresearch.loader.model.FoodStore;

import java.io.IOException;
import java.util.List;

public interface IFoodStoreRepository {
    void index(List<FoodStore> foodStores) throws IOException;
    List<FoodStore> searchNearestStore(double latitude, double longitude, int distance, int batchSize) throws IOException;
    List<FoodStore> searchByPartialNameOrAddress(String query, int batchSize);
}
