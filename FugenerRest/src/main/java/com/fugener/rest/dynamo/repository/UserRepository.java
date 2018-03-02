package com.fugener.rest.dynamo.repository;

import com.fugener.rest.dynamo.model.User;
import com.fugener.rest.exception.DependencyException;
import com.fugener.rest.exception.InvalidArgumentException;

import java.util.List;

public interface UserRepository {

    public User saveUser(final User user) throws DependencyException, InvalidArgumentException;

    public List<User> getUsersByIds(final List<String> userIdList) throws DependencyException, InvalidArgumentException;
}
