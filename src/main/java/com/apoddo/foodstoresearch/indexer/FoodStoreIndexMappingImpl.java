package com.apoddo.foodstoresearch.indexer;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents an implementation of the {@code FoodStoreIndexMapping} interface, which is used to build a
 * mapping for the Elasticsearch index that stores FoodStore objects. The mapping specifies the field names, types, and
 * settings for the index, which are used to define the structure and behavior of the index.
 */
public class FoodStoreIndexMappingImpl implements IFoodStoreIndexMapping {
    /**
     * A map of field names to a map of field properties. The field properties map contains a single key-value pair
     * representing the type of the field.
     */
    private final Map<String, Map<String, String>> properties;

    /**
     * Creates a new instance of the {@code FoodStoreIndexMappingImpl} class with an empty properties map.
     */
    public FoodStoreIndexMappingImpl() {
        properties = new HashMap<>();
    }

    /**
     * Adds a new field to the mapping with the given name and type.
     *
     * @param name The name of the field to add.
     * @param type The type of the field to add.
     */
    public void addProperty(String name, String type) {
        properties.put(name, Collections.singletonMap("type", type));
    }

    /**
     * Builds the Elasticsearch mapping for the FoodStore index using the properties that have been added to this
     * instance.
     *
     * @return An instance of {@code XContentBuilder} representing the Elasticsearch mapping for the FoodStore index.
     * @throws IOException If an I/O error occurs while building the mapping.
     */
    public XContentBuilder build() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("mappings");
            {
                builder.startObject("_doc");
                {
                    builder.startObject("properties");
                    {
                        for (Map.Entry<String, Map<String, String>> entry : properties.entrySet()) {
                            builder.startObject(entry.getKey());
                            {
                                for (Map.Entry<String, String> subEntry : entry.getValue().entrySet()) {
                                    builder.field(subEntry.getKey(), subEntry.getValue());
                                }
                            }
                            builder.endObject();
                        }
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();

        return builder;
    }
}
