package com.fugener.rest.dynamo.repository;

import com.fugener.rest.dynamo.model.User;
import com.fugener.rest.dynamo.repository.base.BaseRepositoryTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTests extends BaseRepositoryTest {

    private UserRepository userRepository;
    private String tableName = "User";

    @Override
    @Before
    public void setup() {
        super.setup();
        userRepository = new UserDynamoRepository(userDynamoDBMapper, tableName);
    }

    @Test
    public void testCreateAndUpdateUser() {
        String id = "id";
        String name = "name";
        User user = User.builder().id(id).name(name).build();
        List<User> userList = userRepository.getUsersByIds(Arrays.asList(id));
        assertEquals(0, userList.size());

        userRepository.saveUser(user);
        userList = userRepository.getUsersByIds(Arrays.asList(id));
        assertEquals(1, userList.size());
        assertEquals(id, userList.get(0).getId());
        assertEquals(name, userList.get(0).getName());

        String name2 = "name2";
        user = User.builder().id(id).name(name2).build();
        userRepository.saveUser(user);
        userList = userRepository.getUsersByIds(Arrays.asList(id));
        assertEquals(1, userList.size());
        assertEquals(id, userList.get(0).getId());
        assertEquals(name2, userList.get(0).getName());
    }

    @Test
    public void testGetUsersByIds() {
        List<String> userIdList = Arrays.asList("id1", "id2", "id3");
        List<User> userList = userRepository.getUsersByIds(userIdList);
        assertEquals(0, userList.size());

        User user0 = User.builder().id(userIdList.get(0)).name("name0").build();
        User user1 = User.builder().id(userIdList.get(1)).name("name1").build();
        User user2 = User.builder().id(userIdList.get(2)).name("name2").build();
        userRepository.saveUser(user0);
        userRepository.saveUser(user1);
        userRepository.saveUser(user2);

        userList = userRepository.getUsersByIds(userIdList);
        assertEquals(3, userList.size());
    }
}
