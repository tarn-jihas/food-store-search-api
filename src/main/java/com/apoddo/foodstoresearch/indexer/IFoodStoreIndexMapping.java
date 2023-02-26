package com.apoddo.foodstoresearch.indexer;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public interface IFoodStoreIndexMapping {

    void addProperty(String name, String type);
    XContentBuilder build() throws IOException;
}
