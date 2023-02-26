package com.apoddo.foodstoresearch.indexer;

import com.apoddo.foodstoresearch.loader.model.FoodStore;
import com.apoddo.foodstoresearch.storage.ElasticSearchClient;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of the IFoodStoreIndexer interface for indexing food stores in Elasticsearch.
 */
@Service
public class FoodStoreIndexerImpl implements IFoodStoreIndexer {
    private ElasticSearchClient client;

    @Autowired
    public FoodStoreIndexerImpl(ElasticSearchClient client) {
        this.client = client;
    }


    /**
     * Creates an Elasticsearch index with the specified name and mapping.
     *
     * @param indexName the name of the index to be created
     * @param mapping   the mapping for the index
     * @throws IOException if there is an error creating the index
     */
    public void createIndex(String indexName, FoodStoreIndexMappingImpl mapping) throws IOException {
        XContentBuilder builder = mapping.build();
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.source(builder);
        client.getClient().indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * Indexes the specified list of food stores in the Elasticsearch index with the specified name.
     *
     * @param indexName  the name of the Elasticsearch index to be used
     * @param foodStores the list of food stores to be indexed
     */
    public void indexDocuments(String indexName, List<FoodStore> foodStores) {
        BulkRequest bulkRequest = new BulkRequest();
        for (FoodStore foodStore : foodStores) {
            IndexRequest indexRequest = new IndexRequest(indexName).id(foodStore.getLicense_Number())
                    .source(foodStore.toMap(), XContentType.JSON).opType(DocWriteRequest.OpType.INDEX);
            bulkRequest.add(indexRequest);
        }
        try {
            client.getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("Error indexing food stores in bulk", e);
        }
    }
}