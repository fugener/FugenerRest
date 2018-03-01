package com.fugener.rest.dynamo.repository;

import com.fugener.rest.dynamo.model.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface UserRepository extends CrudRepository<User, String> {
    //List<User> findById(String id);
}
