package com.moondog.labs.spring_docker_postgres;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ThingRepository extends CrudRepository<Thing, UUID> {}
