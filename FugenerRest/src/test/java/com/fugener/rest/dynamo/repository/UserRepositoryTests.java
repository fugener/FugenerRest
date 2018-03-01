package com.fugener.rest.dynamo.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.fugener.rest.FugenerRestApplication;
import com.fugener.rest.dynamo.model.User;
import com.fugener.rest.dynamo.repository.rule.LocalDynamoCreateRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FugenerRestApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.aws.accesskey=test1",
        "amazon.aws.secretkey=test231" })
public class UserRepositoryTests {

    @ClassRule
    public static LocalDynamoCreateRule dynamoCreateRule = new LocalDynamoCreateRule();

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setup() throws Exception {
        try {
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
            CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(User.class);
            tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
            amazonDynamoDB.createTable(tableRequest);
        } catch (ResourceInUseException e) {

        }

        dynamoDBMapper.batchDelete((List<User>) userRepository.findAll());
    }

    @Test
    public void testCreateUser() {
        String id = "id";
        String name = "name";
        User user = User.builder().id(id).name(name).build();
        userRepository.save(user);

        List<User> userList = (List<User>) userRepository.findAll();
        assertEquals(1, userList.size());
        assertEquals(id, userList.get(0).getId());
    }
}
