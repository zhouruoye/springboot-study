package com.cest.config;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源1 mysql1
 */
@Configuration
@MapperScan(basePackages = Mysql1DataSourceConfig.MYSQL1DAOPATH, sqlSessionFactoryRef = "mysql1SqlSessionFactory")
public class Mysql1DataSourceConfig {

    //dao层扫描路径
    static final String MYSQL1DAOPATH = "com.cest.dao.mysql1";

    //mapper映射路径
    static final String MYSQL1XMLPATH = "classpath:mapper/mysql1/*.xml";

    @Primary
    @Bean(name = "mysql1DataSource")
    @ConfigurationProperties("spring.datasource.druid.mysql1")
    public DataSource mysql1DataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "mysql1TransactionManager")
    @Primary
    public DataSourceTransactionManager mysql1TransactionManager() {
        return new DataSourceTransactionManager(mysql1DataSource());
    }

    @Bean(name = "mysql1SqlSessionFactory")
    @Primary
    public SqlSessionFactory mysql1SqlSessionFactory(@Qualifier("mysql1DataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //如果不使用xml的方式配置mapper，则可以省去下面这行mapper location的配置。
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(Mysql1DataSourceConfig.MYSQL1XMLPATH));
        return sessionFactory.getObject();
    }

}
