package com.apoddo.foodstoresearch.api;

import com.apoddo.foodstoresearch.api.param_parser.ISearchParamsParser;
import com.apoddo.foodstoresearch.api.param_parser.model.SearchParams;
import com.apoddo.foodstoresearch.api.param_validation.ISearchParamsValidator;
import com.apoddo.foodstoresearch.loader.model.FoodStore;
import com.apoddo.foodstoresearch.loader.FoodStoreLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(FoodStoreController.class)
public class FoodStoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodStoreServiceImpl foodStoreService;

    @MockBean
    private FoodStoreLoader foodStoreLoader;
    @MockBean
    @Qualifier("SearchParamsParserImpl")
    private ISearchParamsParser parser;

    @MockBean
    @Qualifier("SearchParamsValidatorImpl")
    private ISearchParamsValidator validator;
    @Test
    void testSearchNearestStores() throws Exception {
        String latitude = "37.7749";
        String longitude = "-122.4194";
        String distance = "10";
        String batchSize = "10";

        List<FoodStore> stores = Arrays.asList(
                 FoodStore.builder().Entity_Name("Store A").Street_Name("123 Main St").Latitude(37.7749).Longitude(-122.4194).build(),
                 FoodStore.builder().Entity_Name("Store B").Street_Name("456 Elm St").Latitude(37.7750).Longitude(-122.4195).build()
        );

        SearchParams params;
        given(parser.parse(latitude, longitude, distance, batchSize)).willReturn(params = new SearchParams.Builder()
                .latitude(Double.parseDouble(latitude))
                .longitude(Double.parseDouble(longitude))
                .distance(Integer.parseInt(distance))
                .batchSize(Integer.parseInt(batchSize)).build());


        given(foodStoreService.searchNearestStores(params.getLatitude(), params.getLongitude(), params.getDistance(), params.getBatchSize())).willReturn(stores);

        mockMvc.perform(MockMvcRequestBuilders.get("/searchNearestStores")
                        .param("latitude", latitude)
                        .param("longitude", longitude)
                        .param("distance", distance))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].latitude", is(37.7749)))
                .andExpect(jsonPath("$[0].longitude", is(-122.4194)))
                .andExpect(jsonPath("$[1].latitude", is(37.7750)))
                .andExpect(jsonPath("$[1].longitude", is(-122.4195)));
    }

    @Test
    void testSearchByPartialNameOrAddress() throws Exception {
        String inputParam = "main";
        String batchSize = "10";

        List<FoodStore> stores = Arrays.asList(
                FoodStore.builder().Entity_Name("Store A").Street_Name("123 Main St").build(),
                FoodStore.builder().Entity_Name("Store B").Street_Name("789 Main St").build()
        );

        SearchParams params = new SearchParams.Builder().batchSize(Integer.parseInt(batchSize)).partialQuery(inputParam).build();

        given(parser.parse(inputParam, batchSize)).willReturn(params);
        given(foodStoreService.searchByPartialNameOrAddress(params.getPartialQuery(), params.getBatchSize())).willReturn(stores);

        mockMvc.perform(MockMvcRequestBuilders.get("/searchByPartialNameOrAddress")
                        .param("inputParam", inputParam)
                        .param("batchSize", batchSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].entityName", is("Store A")))
                .andExpect(jsonPath("$[0].streetName", is("123 Main St")))
                .andExpect(jsonPath("$[1].entityName", is("Store B")))
                .andExpect(jsonPath("$[1].streetName", is("789 Main St")));
    }


}
