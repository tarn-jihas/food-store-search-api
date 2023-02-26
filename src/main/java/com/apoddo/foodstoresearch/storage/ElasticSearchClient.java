package com.apoddo.foodstoresearch.storage;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * This class represents a client for the Elasticsearch search engine and provides an instance of the
 * RestHighLevelClient class.
 */
@Component
@PropertySource("classpath:application.properties")
public class ElasticSearchClient {

    // The instance of the RestHighLevelClient class.
    private final RestHighLevelClient client;

    private final String esHost;

    /**
     * Creates a new instance of the ElasticSearchClient class with a default configuration, and initializes the
     * RestHighLevelClient instance.
     *
     * @param host the hostname for the Elasticsearch instance, as specified in the "es.hostname" property in the
     *             application.properties file.
     */
    @Autowired
    public ElasticSearchClient(@Value("${es.hostname}") String host) {
        this.esHost = host;

        // Set up the RestClient and the RestHighLevelClient with the specified Elasticsearch host and port.
        RestClientBuilder builder = RestClient.builder(new HttpHost(esHost, 9200, "http"));
        client = new RestHighLevelClient(builder);
    }

    /**
     * Gets the instance of the RestHighLevelClient class that is used to communicate with the Elasticsearch search engine.
     *
     * @return The instance of the RestHighLevelClient class.
     */
    public RestHighLevelClient getClient() {
        return client;
    }
}
