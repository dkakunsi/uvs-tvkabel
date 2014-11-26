package com.unitedvision.tvkabel.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.unitedvision.tvkabel.core.ApplicationConfig;

@Configuration
@EnableJpaRepositories
@ComponentScan("com.unitedvision.tvkabel.persistence")
@Import(ApplicationConfig.class)
public class SpringDataJpaConfig {
	private static final String HOST = "mysql-uvision.whelastic.net";
	private static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String PROPERTY_NAME_DATABASE_URL = String.format("jdbc:mysql://%s:3306/unitedvision_tvkabel", HOST);
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "uv_tvk";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "tvkabel001";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.unitedvision.tvkabel.persistence.entity";
    
    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public HibernatePersistence persistenceProvider() {
        return new HibernatePersistence();
    }

    @Bean
    public DataSource dataSource() {
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();

    	dataSource.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
    	dataSource.setUrl(PROPERTY_NAME_DATABASE_URL);
    	dataSource.setUsername(PROPERTY_NAME_DATABASE_USERNAME);
    	dataSource.setPassword(PROPERTY_NAME_DATABASE_PASSWORD);
    	
    	return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
    	LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    	entityManagerFactoryBean.setPackagesToScan(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProvider(persistenceProvider());
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setJpaPropertyMap(propertiesMap());
        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public JpaTransactionManager transactionManager() {
    	JpaTransactionManager transactionManager = new JpaTransactionManager();
    	
    	transactionManager.setEntityManagerFactory(entityManagerFactory());
    	
    	return transactionManager;
    }
    
    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    private Map<String, String> propertiesMap() {
        Map<String, String> propertiesMap = new HashMap<>();
        propertiesMap.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        propertiesMap.put("hibernate.show_sql", "true");
        propertiesMap.put("hibernate.format_sql", "true");
        return propertiesMap;
    }
}
