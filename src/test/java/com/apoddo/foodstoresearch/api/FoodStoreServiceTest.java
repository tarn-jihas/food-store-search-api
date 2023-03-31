package com.apoddo.foodstoresearch.api;

import com.apoddo.foodstoresearch.loader.model.FoodStore;
import com.apoddo.foodstoresearch.storage.IFoodStoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class FoodStoreServiceTest {

    @Mock
    private IFoodStoreRepository repository;

    private FoodStoreServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new FoodStoreServiceImpl(repository);
    }

    @Test
    public void testSearchNearestStores() throws IOException {
        // Arrange
        double latitude = 37.7749;
        double longitude = -122.4194;
        int distance = 10;
        int batchSize = 10;

        List<FoodStore> stores = Arrays.asList(
                FoodStore.builder().Entity_Name("Store A").Street_Name("123 Main St").Latitude(37.7749).Longitude(-122.4194).build(),
                FoodStore.builder().Entity_Name("Store B").Street_Name("456 Elm St").Latitude(37.7750).Longitude(-122.4195).build()
        );

        when(repository.searchNearestStore(latitude, longitude, distance, batchSize)).thenReturn(stores);

        // Act
        List<FoodStore> result = service.searchNearestStores(latitude, longitude, distance, batchSize);

        // Assert
        assertThat(result).isEqualTo(stores);
    }

    @Test
    public void testSearchByPartialNameOrAddress() throws IOException {
        // Arrange
        String criteria = "main";
        int batchSize = 10;
        List<FoodStore> stores = Arrays.asList(
                FoodStore.builder().Entity_Name("Store A").Street_Name("123 Main St").build(),
                FoodStore.builder().Entity_Name("Store B").Street_Name("789 Main St").build()
        );

        when(repository.searchByPartialNameOrAddress(criteria, batchSize)).thenReturn(stores);

        // Act
        List<FoodStore> result = service.searchByPartialNameOrAddress(criteria, batchSize);

        // Assert
        assertThat(result).isEqualTo(stores);
    }
}
