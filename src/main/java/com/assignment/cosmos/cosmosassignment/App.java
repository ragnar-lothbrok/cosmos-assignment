package com.assignment.cosmos.cosmosassignment;

import com.assignment.cosmos.cosmosassignment.service.DocumentParallelAddService;
import com.assignment.cosmos.cosmosassignment.service.DocumentRetrievalService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    public static final Logger LOGGER = LoggerFactory.getLogger(DocumentParallelAddService.class);

    public static void main(String[] args) {

//        DocumentAddService.saveAll();

        long startTime = System.currentTimeMillis();
        DocumentRetrievalService.fetch();
        LOGGER.info("Time taken : {} " , TimeUnit.MILLISECONDS.toMillis(System.currentTimeMillis() - startTime) + " milliseconds.");
    }
}
