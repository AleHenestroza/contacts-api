package com.tiendanube.contactsapi.integration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.*;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        System.setProperty("spring.data.mongodb.uri", "mongodb://" + mongoDBContainer.getHost() + ":" + mongoDBContainer.getMappedPort(27017) + "/test");
    }

    @AfterAll
    static void afterAll() {
        mongoDBContainer.stop();
    }

    @BeforeEach
    void setUp() {
        mongoClient = MongoClients.create("mongodb://" + mongoDBContainer.getHost() + ":" + mongoDBContainer.getMappedPort(27017));
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
                  "email": "alehenestroza@gmail.com",
                  "firstName": "Alejandro",
                  "lastName": "Henestroza"
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
        String createContactRequest = """
                {
                  "email": "alehenestroza@gmail.com",
                  "firstName": "Alejandro",
                  "lastName": "Henestroza"
                }""";

        RequestBuilder req = MockMvcRequestBuilders.post("/contacts")
                .content(createContactRequest)
                .contentType("application/json")
                .accept("application/json");


        mockMvc.perform(req)
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isCreated());


        mockMvc.perform(req)
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void test_createContact_failsWhenEmailIsInvalid() throws Exception {
        String createContactRequest = """
                {
                  "email": "invalidemail",
                  "firstName": "Alejandro",
                  "lastName": "Henestroza"
                }""";

        RequestBuilder req = MockMvcRequestBuilders.post("/contacts")
                .content(createContactRequest)
                .contentType("application/json")
                .accept("application/json");

        mockMvc.perform(req)
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }
}
