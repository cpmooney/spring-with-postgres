package com.moondog.labs.spring_docker_postgres;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.StreamSupport;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringDockerPostgresApplication.class})
@ContextConfiguration(initializers = {ThingRepositoryTest.Initializer.class})
class ThingRepositoryTest {

    @Autowired
    public ThingRepository thingRepository;

    public static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:11.1").withUsername("ben").withPassword("asdfasdfasdf");

    @BeforeAll
    public static void setup() {
        postgres.start();
    }

    @AfterAll
    private static void teardown() {
        postgres.stop();
    }

    @Test
    @Transactional
    public void test() {
        thingRepository.save(
                Thing.builder().shape("triangle").color("turquoise").build());

        List<Thing> things = StreamSupport.stream(thingRepository.findAll().spliterator(), false)
                .toList();

        assertThat(things.size()).isEqualTo(1);
        assertThat(things.getFirst().getShape()).isEqualTo("triangle");
        assertThat(things.getFirst().getColor()).isEqualTo("turquoise");
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                            "spring.datasource.url" + "=" + ThingRepositoryTest.postgres.getJdbcUrl(),
                            "spring.datasource.username" + "=" + ThingRepositoryTest.postgres.getUsername(),
                            "spring.datasource.password" + "=" + ThingRepositoryTest.postgres.getPassword())
                    .applyTo(applicationContext);
        }
    }
}
