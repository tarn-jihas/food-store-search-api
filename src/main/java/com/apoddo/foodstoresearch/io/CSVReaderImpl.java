package com.apoddo.foodstoresearch.io;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

@Component
public class CSVReaderImpl implements ICSVReader {

    private final CSVReader csvReader;

    public CSVReaderImpl(@Value("${input.fileName}") String fileName) throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader(fileName));
        this.csvReader = new CSVReader(reader);
    }

    public CSVReader getCsvReader() {
        return csvReader;
    }
}