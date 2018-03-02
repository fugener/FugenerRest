package com.fugener.rest.dynamo.repository.base;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.fugener.rest.dynamo.model.User;
import com.fugener.rest.dynamo.repository.rule.LocalDynamoCreateRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseRepositoryTest {

    @ClassRule
    public static LocalDynamoCreateRule dynamoCreateRule = new LocalDynamoCreateRule();

    protected DynamoDBMapper userDynamoDBMapper;

    private AmazonDynamoDB amazonDynamoDB;

    @Before
    public void setup() {
        amazonDynamoDB = amazonDynamoDB();
        userDynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = userDynamoDBMapper.generateCreateTableRequest(User.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
        amazonDynamoDB.createTable(tableRequest);

    }

    @After
    public void clean() {
        final List<String> tables = amazonDynamoDB.listTables().getTableNames();
        tables.stream().forEach(table -> amazonDynamoDB.deleteTable(table));
    }

    private AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(awsCredentials());
        amazonDynamoDB.setEndpoint("http://localhost:8000/");
        return amazonDynamoDB;
    }

    private AWSCredentials awsCredentials() {
        return new BasicAWSCredentials("123", "123");
    }

}
