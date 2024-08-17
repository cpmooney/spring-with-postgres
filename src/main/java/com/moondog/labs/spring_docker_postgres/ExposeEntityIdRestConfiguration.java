package com.moondog.labs.spring_docker_postgres;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@RequiredArgsConstructor
public class ExposeEntityIdRestConfiguration implements RepositoryRestConfigurer {
    private final ApplicationContext applicationContext;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(entities());
    }

    private Class<?>[] entities() {
        EntityManager entityManager = applicationContext.getBean(EntityManager.class);
        return entityManager.getMetamodel().getEntities().stream()
                .map(EntityType::getJavaType)
                .filter(this::hasExposeIdAnnotation)
                .toArray(Class<?>[]::new);
    }

    private boolean hasExposeIdAnnotation(Class<?> clazz) {
        return clazz.getAnnotation(ExposeEntityId.class) != null;
    }
}
