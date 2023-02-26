package com.apoddo.foodstoresearch.storage;

import com.apoddo.foodstoresearch.indexer.FoodStoreIndexMappingImpl;
import com.apoddo.foodstoresearch.indexer.IFoodStoreIndexMapping;
import com.apoddo.foodstoresearch.indexer.IFoodStoreIndexer;
import com.apoddo.foodstoresearch.loader.model.FoodStore;
import com.apoddo.foodstoresearch.util.enums.FieldName;
import com.apoddo.foodstoresearch.util.enums.FieldTypes;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FoodStoreRepositoryImpl implements IFoodStoreRepository {
    @Value("${index.name}")
    private String INDEX_NAME;

    @Value("${indexing.enabled}")
    private boolean indexingEnabled;

    private ElasticSearchClient client;

    private IFoodStoreIndexer foodStoreIndexer;

    @Autowired
    public FoodStoreRepositoryImpl(ElasticSearchClient client, IFoodStoreIndexer foodStoreIndexer) {
        this.client = client;
        this.foodStoreIndexer = foodStoreIndexer;
    }

      /**
     * Implements the index method from the IFoodStoreRepository interface.
     * Indexes a list of FoodStore objects in Elasticsearch.
     *
     * @param foodStores the list of FoodStore objects to be indexed
     * @throws IOException if an error occurs while communicating with Elasticsearch
     */
    @Override
    public void index(List<FoodStore> foodStores) throws IOException {
        // create a new IndicesExistsRequest with the index name

        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(INDEX_NAME);

        // execute the request
           try {
            boolean exists =  client.getClient().indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            if (!exists) foodStoreIndexer.createIndex(INDEX_NAME, addPropertiesToIndexMap());
            if(indexingEnabled)foodStoreIndexer.indexDocuments(INDEX_NAME, foodStores);


        } catch (IOException e) {
            // handle the Elasticsearch client IO exception
            System.err.println("Failed to check if index exists: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // handle any other exception that may be thrown
            System.err.println("Failed to index documents: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Creates a new {@link FoodStoreIndexMappingImpl} and adds properties to it for indexing {@link FoodStore} objects.
     *
     * @return the {@link FoodStoreIndexMappingImpl} with properties added
     */
    private FoodStoreIndexMappingImpl addPropertiesToIndexMap() {
        IFoodStoreIndexMapping fsIm = new FoodStoreIndexMappingImpl();
        fsIm.addProperty(FieldName.getCounty(), FieldTypes.getText());
        fsIm.addProperty(FieldName.getLicenseNumber(), FieldTypes.getKeyword());
        fsIm.addProperty(FieldName.getEstablishmentType(), FieldTypes.getText());
        fsIm.addProperty(FieldName.getEntityName(), FieldTypes.getText());
        fsIm.addProperty(FieldName.getDbaName(), FieldTypes.getText());
        fsIm.addProperty(FieldName.getStreetNumber(), FieldTypes.getText());
        fsIm.addProperty(FieldName.getStreetName(), FieldTypes.getText());
        fsIm.addProperty(FieldName.getCity(), FieldTypes.getText());
        fsIm.addProperty(FieldName.getStateAbbreviation(), FieldTypes.getText());
        fsIm.addProperty(FieldName.getZipCode(), FieldTypes.getText());
        fsIm.addProperty(FieldName.getSquareFootage(), FieldTypes.getFloat());
        fsIm.addProperty(FieldName.getLocation(), FieldTypes.getGeoPoint());
        return (FoodStoreIndexMappingImpl) fsIm;
    }


    /**
     * Implements the searchNearestStore method from the IFoodStoreRepository interface.
     * Searches for the nearest food stores to the given latitude and longitude within a specified distance.
     *
     * @param latitude the latitude to search around
     * @param longitude the longitude to search around
     * @param distance the distance in kilometers to search within. Default is 1
     * @param batchSize the query size which will be returned. Default is 10.
     * @return a list of FoodStore objects that match the search criteria
     * @throws IOException if an error occurs while communicating with Elasticsearch
     */
    @Override
    public List<FoodStore> searchNearestStore(double latitude, double longitude, int distance, int batchSize) throws IOException {
        GeoDistanceQueryBuilder queryBuilder = QueryBuilders.geoDistanceQuery("location")
                .point(latitude, longitude).distance(distance, DistanceUnit.KILOMETERS);
        // Create a SearchRequest to execute the search
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // Set the query and sort criteria for the search
        searchSourceBuilder.size(batchSize);
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.sort(SortBuilders.geoDistanceSort("location", latitude, longitude).order(SortOrder.ASC));
        searchRequest.source(searchSourceBuilder);

        SearchHits searchHits;
        try {
            SearchResponse searchResponse = client.getClient().search(searchRequest, RequestOptions.DEFAULT);
            searchHits = searchResponse.getHits();
        } catch (IOException e) {
            // Throw a RuntimeException if an error occurs while executing the search
            throw new RuntimeException("Error executing search query: " + e.getMessage(), e);
        }

        List<FoodStore>  asd = mapSearchHitsToFoodStores(searchHits);
        // Map the search hits to FoodStore objects and return them as a list
        return mapSearchHitsToFoodStores(searchHits);
    }


    /**
     * Implements the searchByPartialNameOrAddress method from the IFoodStoreRepository interface.
     * Searches for food stores to the given partial name or address.
     *
     * @param query the partial name
     * @return a list of FoodStore objects that match the search criteria
     * @throws IOException if an error occurs while communicating with Elasticsearch
     */
    @Override
    public List<FoodStore> searchByPartialNameOrAddress(String query, int batchSize) {
        // Define the fields to search in
        String[] fields = {FieldName.getEntityName(), FieldName.getStreetName(), FieldName.getDbaName()};

        // Build the search query using a MultiMatchQueryBuilder
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(query.toUpperCase(), fields)
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS);

        // Create a SearchRequest to execute the search
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(batchSize);
        searchSourceBuilder.query(multiMatchQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchHits searchHits;
        try {

            // Get a client instance and execute the search
            org.elasticsearch.action.search.SearchResponse searchResponse = client.getClient().search(searchRequest, RequestOptions.DEFAULT);
            searchHits = searchResponse.getHits();
        } catch (IOException e) {
            // Throw a RuntimeException if an error occurs while executing the search
            throw new RuntimeException("Error executing search query: " + e.getMessage(), e);
        }
        // Map the search hits to FoodStore objects and return them as a list
        return mapSearchHitsToFoodStores(searchHits);
}
    /**
     Maps the search hits from Elasticsearch to a list of FoodStore objects.
     @param searchHits the SearchHits object to be mapped to a list of FoodStore objects
     @return a list of FoodStore objects mapped from the SearchHits object
     */
    private List<FoodStore> mapSearchHitsToFoodStores(SearchHits searchHits) {
                return Arrays.stream(searchHits.getHits())
                .map(hit -> hit.getSourceAsMap())
                .map(sourceMap -> new FoodStore.FoodStoreBuilder()
                        .setCounty((String) sourceMap.getOrDefault(FieldName.getCounty(), ""))
                        .setLicense_Number((String) sourceMap.getOrDefault(FieldName.getLicenseNumber(), ""))
                        .setEstablishment_Type((String) sourceMap.getOrDefault(FieldName.getEstablishmentType(), ""))
                        .setEntity_Name((String) sourceMap.getOrDefault(FieldName.getEntityName(), ""))
                        .setDBA_Name((String) sourceMap.getOrDefault(FieldName.getDbaName(), ""))
                        .setStreet_Number((String) sourceMap.getOrDefault(FieldName.getStreetNumber(), ""))
                        .setStreet_Name((String) sourceMap.getOrDefault(FieldName.getStreetName(), ""))
                        .setCity((String) sourceMap.getOrDefault(FieldName.getCity(), ""))
                        .setState_Abbreviation((String) sourceMap.getOrDefault(FieldName.getStateAbbreviation(), ""))
                        .setZip_Code((String) sourceMap.getOrDefault(FieldName.getZipCode(), ""))
                        .setSquare_Footage(sourceMap.get(FieldName.getSquareFootage()) != null ? Integer.parseInt(sourceMap.get(FieldName.getSquareFootage()).toString()) : 0)
                        .setLatitude(sourceMap.get("location") != null ? ((Map<String, Object>) sourceMap.get("location")).get("lat") != null ? Double.parseDouble(((Map<String, Object>) sourceMap.get("location")).get("lat").toString()) : 0.0 : 0.0)
                        .setLongitude(sourceMap.get("location") != null ? ((Map<String, Object>) sourceMap.get("location")).get("lon") != null ? Double.parseDouble(((Map<String, Object>) sourceMap.get("location")).get("lon").toString()) : 0.0 : 0.0)
                        .build())
                .collect(Collectors.toList());
    }

}
