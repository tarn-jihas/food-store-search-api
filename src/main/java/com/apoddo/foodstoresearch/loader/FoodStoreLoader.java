package com.apoddo.foodstoresearch.loader;

import com.apoddo.foodstoresearch.io.parser.ICSVParser;
import com.apoddo.foodstoresearch.loader.model.FoodStore;
import com.apoddo.foodstoresearch.storage.IFoodStoreRepository;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * This class represents a loader that can load FoodStore data from a CSV file into a repository using a parser.
 */
@Component
public class FoodStoreLoader {
    /**
     * The repository to load FoodStore data into.
     */
    private final IFoodStoreRepository repository;
    /**
     * The parser to use to parse the CSV file.
     */
    private final ICSVParser parser;

    /**
     * Creates a new instance of the {@code FoodStoreLoader} class with the given repository and parser.
     *
     * @param repository The repository to load FoodStore data into.
     * @param parser     The parser to use to parse the CSV file.
     */
    public FoodStoreLoader(IFoodStoreRepository repository, ICSVParser parser) {
        this.repository = repository;
        this.parser = parser;
    }

    /**
     * Loads FoodStore data from the CSV file with the given file name into the repository using the parser.
     *
     */
    public void load() {
        try {
            List<FoodStore> foodStores = parser.parse();
            repository.index(foodStores);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }


}
