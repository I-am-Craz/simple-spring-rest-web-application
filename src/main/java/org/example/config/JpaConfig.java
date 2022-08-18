package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.hibernate.jpa.HibernatePersistenceProvider;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.example.repositories"})
public class JpaConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("org.example.entities");
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(additionalProperties());

        return entityManagerFactoryBean;
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setUsername(System.getenv("DB_USERNAME"));
        hikariConfig.setPassword(System.getenv("DB_PASSWORD"));
        hikariConfig.setMaximumPoolSize(100);
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/db");
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public Properties additionalProperties(){
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        hibernateProperties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory().getObject()));
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        return new HibernateJpaVendorAdapter();
    }

}
