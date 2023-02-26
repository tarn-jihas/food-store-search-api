package com.apoddo.foodstoresearch.io.parser;

import com.apoddo.foodstoresearch.loader.model.FoodStore;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;


public interface ICSVParser {

     List<FoodStore> parse() throws IOException, CsvValidationException;
}
