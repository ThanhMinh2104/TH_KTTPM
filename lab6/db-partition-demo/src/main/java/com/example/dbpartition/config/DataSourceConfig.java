package com.example.dbpartition.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    // =========================================================
    //  1. Tao 2 DataSource rieng biet
    // =========================================================

    @Bean("maleDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.male")
    public DataSource maleDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("femaleDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.female")
    public DataSource femaleDataSource() {
        return DataSourceBuilder.create().build();
    }

    // =========================================================
    //  2. Tao Routing DataSource (gom 2 DS tren)
    // =========================================================

    @Bean
    @Primary
    public DataSource routingDataSource(
            @Qualifier("maleDataSource")   DataSource male,
            @Qualifier("femaleDataSource") DataSource female) {

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(UserRoutingDataSource.MALE,   male);
        targetDataSources.put(UserRoutingDataSource.FEMALE, female);

        UserRoutingDataSource routing = new UserRoutingDataSource();
        routing.setTargetDataSources(targetDataSources);
        routing.setDefaultTargetDataSource(male); // mac dinh dung MALE
        routing.afterPropertiesSet();
        return routing;
    }

    // =========================================================
    //  3. EntityManagerFactory dung routingDataSource
    // =========================================================

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("routingDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.dbpartition.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties props = new Properties();
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        // Khi dung SQL Server, thay bang:
        // props.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        em.setJpaProperties(props);

        return em;
    }

    // =========================================================
    //  4. Transaction Manager
    // =========================================================

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
