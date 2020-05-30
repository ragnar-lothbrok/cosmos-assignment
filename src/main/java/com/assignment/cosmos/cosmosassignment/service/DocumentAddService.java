package com.assignment.cosmos.cosmosassignment.service;

import com.assignment.cosmos.cosmosassignment.ProductClient;
import com.assignment.cosmos.cosmosassignment.dtos.Product;
import com.assignment.cosmos.cosmosassignment.utils.CSVUtils;
import com.azure.data.cosmos.CosmosItemRequestOptions;
import com.azure.data.cosmos.PartitionKey;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class DocumentAddService {

    public static final Logger LOGGER = LoggerFactory.getLogger(DocumentAddService.class);

    public static void saveAll() {
        final long start = System.nanoTime();
        Flux
            .just(CSVUtils.readDataLineByLine("sample_kaggle_data.csv"))
            .flatMap(Flux::fromIterable)
            .map(product -> addDocument(Mono.just(product), Product.class, product.getCategorySubcategory()).block())
            .doFinally(endType -> LOGGER.info("Time taken : {} " , TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + " milliseconds."))
            .subscribe(product -> LOGGER.info("Inserted = {} ", product.getId()));
    }

    public static <T> Mono<T> addDocument(Mono<T> obj, Class<T> klass, Object partitionKey) {
        return obj.flatMap(data -> {
            CosmosItemRequestOptions cosmosItemRequestOptions = new CosmosItemRequestOptions();
            cosmosItemRequestOptions.partitionKey(new PartitionKey(partitionKey));
            return ProductClient.getInstance().createItem(data, cosmosItemRequestOptions)
                .map(response -> {
                    LOGGER.info("Request Charge to add record for Request Charge = {} ", response.requestCharge());
                    T savedData = null;
                    try {
                        savedData =  response.properties().getObject(klass);
                    } catch (IOException e) {
                        LOGGER.error("Exception occurred while parsing output to Pojo = {} ", e);
                    }
                    return savedData;
                });
        });
    }

}
