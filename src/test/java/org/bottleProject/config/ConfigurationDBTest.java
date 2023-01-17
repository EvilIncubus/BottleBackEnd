package org.bottleProject.config;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class ConfigurationDBTest {

    public ConfigurationDBTest() {
    }

    @Bean
    public DataSource testDataSource(Environment environment) {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
        driverManagerDataSource.setUrl(environment.getProperty("spring.datasource.url"));
        driverManagerDataSource.setUsername(environment.getProperty("spring.datasource.username"));
        driverManagerDataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return driverManagerDataSource;
    }
}
