package com.vvss.FlavorFiesta.test_utils;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //  meant to be used together with and @BeforeAll @AfterAll
public class TestPersistenceLayer {
    /*
    Inherit this class to test the persistence layer of the Spring App
    This does not boot the complete Spring application, only the repositories.
    Makes sense to use when you want to test custom queries
     */
    @Autowired
    public TestEntityManager entityManager;
}
