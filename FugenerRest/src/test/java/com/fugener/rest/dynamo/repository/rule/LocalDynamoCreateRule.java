package com.fugener.rest.dynamo.repository.rule;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.rules.ExternalResource;

public class LocalDynamoCreateRule extends ExternalResource {

    protected DynamoDBProxyServer server;

    public LocalDynamoCreateRule() {
        System.setProperty("sqlite4java.library.path", "native-libs");
    }

    @Override
    protected void before() throws Exception {
        String port = "8000";
        this.server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory", "-port", port});
        server.start();
    }

    @Override
    protected void after() {
        this.stopUnchecked(server);
    }

    protected void stopUnchecked(DynamoDBProxyServer dynamoDBProxyServer) {
        try {
            dynamoDBProxyServer.stop();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
