package com.apoddo.foodstoresearch.indexer;

import com.apoddo.foodstoresearch.loader.model.FoodStore;

import java.io.IOException;
import java.util.List;

public interface IFoodStoreIndexer {
    void createIndex(String indexName, FoodStoreIndexMappingImpl mapping) throws IOException;
    void indexDocuments(String indexName, List<FoodStore> foodStores);
}
