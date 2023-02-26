package com.apoddo.foodstoresearch.io.parser;

import com.apoddo.foodstoresearch.io.ICSVReader;
import com.apoddo.foodstoresearch.loader.model.FoodStore;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CSVParserImpl implements ICSVParser {
    private ICSVReader csvReader;

    @Autowired
    public CSVParserImpl(ICSVReader csvReader) {
        this.csvReader = csvReader;
    }


    /**
     * Parses a CSV file containing food store data and returns a list of FoodStore objects.
     *
     * @return a list of FoodStore objects
     */

    @Override
    public List<FoodStore> parse() {

       // Define the mapping strategy to map CSV columns to the FoodStore class properties
        HeaderColumnNameMappingStrategy<FoodStore> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(FoodStore.class);
        CsvToBean<FoodStore> csvToBean = new CsvToBean<>();
        csvToBean.setMappingStrategy(mappingStrategy);
        csvToBean.setCsvReader(csvReader.getCsvReader());

        // Parse the CSV data into a list of FoodStore objects
        List<FoodStore> foodStores = csvToBean.parse();

        // Return the list of FoodStore objects
        return foodStores;
    }

}
