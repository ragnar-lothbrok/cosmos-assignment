package com.assignment.cosmos.cosmosassignment;

import com.azure.data.cosmos.ConnectionMode;
import com.azure.data.cosmos.ConnectionPolicy;
import com.azure.data.cosmos.CosmosClient;
import com.azure.data.cosmos.CosmosContainer;
import com.azure.data.cosmos.CosmosDatabase;

public class ProductClient {

    public static CosmosContainer container = null;

    static {
        ConnectionPolicy connectionPolicy = new ConnectionPolicy();
        connectionPolicy.connectionMode(ConnectionMode.DIRECT);

        CosmosClient cosmosClient = CosmosClient.builder()
            .endpoint(System.getenv("COSMOS_END_POINT"))
            .key(System.getenv("COSMOS_PRIMARY_KEY"))
            .connectionPolicy(connectionPolicy)
            .build();

        CosmosDatabase cosmosDatabase = cosmosClient.getDatabase(System.getenv("COSMOS_DATABASE"));
        container = cosmosDatabase.getContainer(System.getenv("COSMOS_CONTAINER"));
    }

    public static CosmosContainer getInstance() {
        return container;
    }
}
