package com.tiendanube.contactsapi.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
public class ContactsControllerIntegrationTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:7.0.12"))
        .withExposedPorts(27017);

    @Autowired
    MockMvc mockMvc;

    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

    @BeforeAll
    static void beforeAll() {
        mongoDBContainer.start();
        System.setProperty("spring.data.mongodb.host", mongoDBContainer.getHost());
        System.setProperty("spring.data.mongodb.port", String.valueOf(mongoDBContainer.getMappedPort(27017)));
        System.setProperty("springjsondb.database", "test");
        System.setProperty("spring.data.mongodb.uri",
            "mongodb://" + mongoDBContainer.getHost() + ":" + mongoDBContainer.getMappedPort(27017) + "/test");
    }

    @AfterAll
    static void afterAll() {
        mongoDBContainer.stop();
    }

    @BeforeEach
    void setUp() {
        mongoClient = MongoClients.create(
            "mongodb://" + mongoDBContainer.getHost() + ":" + mongoDBContainer.getMappedPort(27017));
        mongoDatabase = mongoClient.getDatabase("test");
    }

    @AfterEach
    void tearDown() {
        mongoDatabase.drop();
    }

    @Test
    void test_createContact() throws Exception {
        String createContactRequest = """
            {
              "email": "testuser@testmail.com",
              "first_name": "test",
              "last_name": "user"
            }""";

        RequestBuilder req = MockMvcRequestBuilders.post("/contacts")
            .content(createContactRequest)
            .contentType("application/json")
            .accept("application/json");

        mockMvc.perform(req)
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(status().isCreated());
    }

    @Test
    void test_createContact_failsWhenEmailIsDuplicated() throws Exception {
        mongoDatabase.getCollection("contacts").insertOne(Document.parse("""
            {
                "email": "testuser@testmail.com",
                "firstName": "Test",
                "lastName": "User"
            }""")
        );

        String createContactRequest = """
            {
                "email": "testuser@testmail.com",
                "first_name": "Test",
                "last_name": "User"
            }""";

        RequestBuilder req = MockMvcRequestBuilders.post("/contacts")
            .content(createContactRequest)
            .contentType("application/json")
            .accept("application/json");

        mockMvc.perform(req)
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void test_createContact_failsWhenEmailIsInvalid() throws Exception {
        String createContactRequest = """
            {
              "email": "invalidemail",
              "first_name": "Test",
              "last_name": "User"
            }""";

        RequestBuilder req = MockMvcRequestBuilders.post("/contacts")
            .content(createContactRequest)
            .contentType("application/json")
            .accept("application/json");

        mockMvc.perform(req)
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void test_getContact() throws Exception {
        InsertOneResult res = mongoDatabase.getCollection("contacts").insertOne(Document.parse("""
            {
                "email": "testuser@testmail.com",
                "firstName": "Test",
                "lastName": "User"
            }""")
        );

        assert res.getInsertedId() != null;
        String id = res.getInsertedId().asObjectId().getValue().toString();

        RequestBuilder req = MockMvcRequestBuilders.get("/contacts/" + id)
            .accept("application/json");

        mockMvc.perform(req)
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(status().isOk())
            .andExpect(result -> {
                String content = result.getResponse().getContentAsString();
                Assertions.assertTrue(content.contains("\"email\":\"testuser@testmail.com\""));
                Assertions.assertTrue(content.contains("\"first_name\":\"Test\""));
                Assertions.assertTrue(content.contains("\"last_name\":\"User\""));
            });
    }

    @Test
    void test_deleteContact() throws Exception {
        InsertOneResult res = mongoDatabase.getCollection("contacts").insertOne(Document.parse("""
            {
                "email": "testuser@testmail.com",
                "firstName": "Test",
                "lastName": "User"
            }""")
        );

        assert res.getInsertedId() != null;
        String id = res.getInsertedId().asObjectId().getValue().toString();

        RequestBuilder req = MockMvcRequestBuilders.delete("/contacts/" + id)
            .accept("application/json");

        mockMvc.perform(req)
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(status().isNoContent());

        Assertions.assertEquals(0, mongoDatabase.getCollection("contacts").countDocuments());
    }
}
