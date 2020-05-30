package com.assignment.cosmos.cosmosassignment.service;

import com.assignment.cosmos.cosmosassignment.ProductClient;
import com.assignment.cosmos.cosmosassignment.dtos.Product;
import com.azure.data.cosmos.CosmosItemProperties;
import com.azure.data.cosmos.FeedOptions;
import com.azure.data.cosmos.FeedResponse;
import com.azure.data.cosmos.PartitionKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DocumentRetrievalService {

    public static final Logger LOGGER = LoggerFactory.getLogger(DocumentRetrievalService.class);

//    public static void fetch() {
//        List<Product> products = getCosmosDocuments("select * from products", Product.class, null)
//            .collectList().block();
//        LOGGER.info("Total Products = {} ", products.size());
//    }

//    public static void fetch() {
//        List<Product> products = getCosmosDocumentsMono("select * from products", Product.class, null, 100).block();
//        LOGGER.info("Total Products = {} ", products.size());
//    }

//    public static void fetch() {
//        List<Product> products = getCosmosDocuments("select * from products offset 0 limit 750", Product.class, null)
//            .collectList().block();
//        LOGGER.info("Total Products = {} ", products.size());
//    }

    public static void fetch() {
        List<Product> products = getCosmosDocuments("select * from products", Product.class, "Arts & Crafts > Paper & Stickers")
            .collectList().block();
        LOGGER.info("Total Products = {} ", products.size());
    }

    public static <T> Flux<T> getCosmosDocuments(String cosmosSQL, Class<T> klass, String partitionKey) {
        try {
            FeedOptions options = new FeedOptions();
            if(Objects.nonNull(partitionKey) && StringUtils.isNotEmpty(partitionKey)) {
                options.partitionKey(new PartitionKey(partitionKey));
                options.enableCrossPartitionQuery(false);
            } else {
                options.enableCrossPartitionQuery(true);
            }
            options.maxItemCount(500);
            return ProductClient.getInstance().queryItems(cosmosSQL, options)
                .map(response -> {
                    List<T> definitions = new ArrayList<>();
                    LOGGER.info("Request Charge to fetch record for query = {} Request Charge = {} ", cosmosSQL, response.requestCharge());
                    response.results().forEach(props -> {
                        T savedData = null;
                        try {
                            savedData =  props.getObject(klass);
                        } catch (IOException e) {
                            LOGGER.error("Exception occurred while parsing output to Pojo = {} ", e);
                        }
                        if (Objects.nonNull(savedData)) {
                            definitions.add(savedData);
                        }
                    });
                    LOGGER.info("Data fetched = {} Request Charge ={} Continuation Token = {} ", definitions.size(),response.requestCharge(), response.continuationToken());
                    return definitions;
                }).flatMap(Flux::fromIterable);
        } catch (Exception exception) {
            LOGGER.error("Exception occurred while fetching documents from Cosmos = {} ", exception);
        }
        return Flux.empty();
    }

    public static <T> Mono<List<T>> getCosmosDocumentsMono(String cosmosSQL, Class<T> klass, String partitionKey, Integer size) {
        try {
            FeedOptions options = new FeedOptions();
            if(Objects.nonNull(partitionKey) && StringUtils.isNotEmpty(partitionKey)) {
                options.partitionKey(new PartitionKey(partitionKey));
                options.enableCrossPartitionQuery(false);
            } else {
                options.enableCrossPartitionQuery(true);
            }
            options.maxItemCount(size);
//            options.requestContinuation("<set token retrieved from response>");
            Flux<FeedResponse<CosmosItemProperties>> cosmosResponse = ProductClient.getInstance().queryItems(cosmosSQL, options);
            return cosmosResponse.next().map(res -> {
                List<T> definitions = new ArrayList<>();
                res.results().forEach(props -> {
                    T savedData = null;
                    try {
                        savedData =  props.getObject(klass);
                    } catch (IOException e) {
                        LOGGER.error("Exception occurred while parsing output to Pojo = {} ", e);
                    }
                    if (Objects.nonNull(savedData)) {
                        definitions.add(savedData);
                    }
                });
                LOGGER.info("Data fetched = {} Request Charge ={} Continuation Token = {} ", definitions.size(),res. requestCharge(), res.continuationToken());
                return definitions;
            });
        } catch (Exception exception) {
            LOGGER.error("Exception occurred while fetching documents from Cosmos = {} ", exception.getMessage());
        }
        return Mono.empty();
    }
}
