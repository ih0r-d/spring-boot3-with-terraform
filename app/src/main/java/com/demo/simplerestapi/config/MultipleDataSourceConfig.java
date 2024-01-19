package com.demo.simplerestapi.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class MultipleDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.users")
    public DataSourceProperties usersDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public HikariDataSource usersDataSource(@Qualifier("usersDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public DataSourceScriptDatabaseInitializer usersDataSourceScriptDatabaseInitializer(@Qualifier("usersDataSource") DataSource dataSource) {
        var initializationSettings = new DatabaseInitializationSettings();
        initializationSettings.setSchemaLocations(List.of("classpath:users-data-schema.sql"));
        initializationSettings.setMode(DatabaseInitializationMode.ALWAYS);
        return new DataSourceScriptDatabaseInitializer(dataSource, initializationSettings);
    }

    @Bean
    @ConfigurationProperties("app.datasource.random")
    public DataSourceProperties randomDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public HikariDataSource randomDataSource(@Qualifier("randomDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public DataSourceScriptDatabaseInitializer randomDataSourceScriptDatabaseInitializer(@Qualifier("randomDataSource") DataSource dataSource) {
        var initializationSettings = new DatabaseInitializationSettings();
        initializationSettings.setSchemaLocations(List.of("classpath:random-data-schema.sql"));
        initializationSettings.setMode(DatabaseInitializationMode.ALWAYS);
        return new DataSourceScriptDatabaseInitializer(dataSource, initializationSettings);
    }

    @Bean
    public JdbcClient usersJdbcClient(@Qualifier("usersDataSource") DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }

    @Bean
    public JdbcClient randomJdbcClient(@Qualifier("randomDataSource") DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }
}
