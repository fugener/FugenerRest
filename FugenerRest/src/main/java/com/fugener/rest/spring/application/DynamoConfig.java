package com.fugener.rest.spring.application;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.fugener.rest.dynamo.repository.UserDynamoRepository;
import com.fugener.rest.dynamo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoConfig {

    //table name has to be the same with cloudformation template
    //make sure you change cloudformation if you need to change table name
    private static final String userTableName = "User";

    @Bean
    DynamoDBMapper userDynamoDBMapper() {
        return new DynamoDBMapper(dynamoDB(), userDynamoDBMapperConfig());
    }

    @Bean
    UserRepository userRepository() {
        return new UserDynamoRepository(userDynamoDBMapper(), userTableName);
    }

    private AmazonDynamoDB dynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new InstanceProfileCredentialsProvider(false))
                .withRegion(Regions.US_WEST_2)
                .build();
    }

    private DynamoDBMapperConfig userDynamoDBMapperConfig() {
        return new DynamoDBMapperConfig.Builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(userTableName))
                .build();
    }
}
