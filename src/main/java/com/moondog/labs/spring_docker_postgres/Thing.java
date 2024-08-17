package com.moondog.labs.spring_docker_postgres;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ExposeEntityId
public class Thing {
    @Id
    @UuidGenerator
    private UUID id;

    private String shape;
    private String color;
}
