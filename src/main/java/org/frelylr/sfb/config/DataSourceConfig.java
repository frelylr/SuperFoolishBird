package org.frelylr.sfb.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DataSourceConfig {

    /**
     * CoreMasterDB DataSource
     */
    @Primary
    @Bean(name = "coreMasterDataSource")
    @Qualifier("coreMasterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.core-master")
    public DataSource coreMasterDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * BladeMasterDB DataSource
     */
    @Bean(name = "bladeMasterDataSource")
    @Qualifier("bladeMasterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.blade-master")
    public DataSource bladeMasterDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * CoreMasterDB JdbcTemplate
     */
    @Bean(name = "coreMasterJdbcTemplate")
    public NamedParameterJdbcTemplate coreMasterJdbcTemplate(
            @Qualifier("coreMasterDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * BladeMasterDB JdbcTemplate
     */
    @Bean(name = "bladeMasterJdbcTemplate")
    public NamedParameterJdbcTemplate bladeMasterJdbcTemplate(
            @Qualifier("bladeMasterDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
