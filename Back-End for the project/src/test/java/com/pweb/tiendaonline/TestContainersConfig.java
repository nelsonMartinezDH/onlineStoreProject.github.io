package com.pweb.tiendaonline;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestContainersConfig {
    PostgreSQLContainer<?> PostgreSQLContainer(){
        return new PostgreSQLContainer<>("postgres:16.2");
    }
}
