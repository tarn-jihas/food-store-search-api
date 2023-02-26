package com.apoddo.foodstoresearch;

import com.apoddo.foodstoresearch.loader.FoodStoreLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class FoodStoreSearchApplication {
    private final FoodStoreLoader loader;

    public FoodStoreSearchApplication(FoodStoreLoader loader) {
        this.loader = loader;
    }

    public static void main(String[] args) {
        SpringApplication.run(FoodStoreSearchApplication.class, args);
    }

    @PostConstruct
    public void init() {
        loader.load();
    }

}
