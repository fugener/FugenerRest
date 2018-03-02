package com.fugener.rest.dynamo.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public abstract class BaseDynamoRepository {

    @NonNull
    @Setter
    @Getter
    private DynamoDBMapper dynamoDBMapper;

    @NonNull
    @Getter
    private String tableName;
}
