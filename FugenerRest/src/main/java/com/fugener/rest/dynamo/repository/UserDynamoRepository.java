package com.fugener.rest.dynamo.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fugener.rest.dynamo.model.User;
import com.fugener.rest.exception.DependencyException;
import com.fugener.rest.exception.InvalidArgumentException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserDynamoRepository extends BaseDynamoRepository implements UserRepository {

    public UserDynamoRepository(final DynamoDBMapper dynamoDBMapper, final String tableName) {
        super(dynamoDBMapper, tableName);
    }

    @Override
    public User saveUser(User user) throws DependencyException, InvalidArgumentException {
        try {
            getDynamoDBMapper().save(user);
        } catch (Exception ex) {
            throw new DependencyException(ex.getMessage());
        }
        return user;
    }

    @Override
    public List<User> getUsersByIds(List<String> userIdList) throws DependencyException, InvalidArgumentException {
        if (CollectionUtils.isEmpty(userIdList)) {
            throw new InvalidArgumentException("Empty userIdList");
        }

        final List<User> userList = new ArrayList<>();
        final List<User> query = new ArrayList<>();

        userIdList.stream()
                .filter(Objects::nonNull)
                .forEach(userId -> query.add(User.builder().id(userId).build()));

        final Map<String, List<Object>> dbResults;
        try {
            dbResults = getDynamoDBMapper().batchLoad(query);
        } catch (Exception ex) {
            throw new DependencyException(ex.getMessage());
        }

        if (dbResults != null) {
            dbResults.get(getTableName()).stream()
                    .forEach(user -> userList.add((User) user));
        }

        return userList;
    }
}
