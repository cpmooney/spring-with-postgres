package com.moondog.labs.spring_docker_postgres;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ExposeEntityId
public class Thing {
    @Id
    @UuidGenerator
    private UUID id;

    private String shape;
    private String color;
}
