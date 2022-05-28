package ru.ining.gps;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class MBcfg {
    @Value("${spring.datasource.mysql.url}")
    private String url;
    @Value("${spring.datasource.mysql.username}")
    private String username;
    @Value("${spring.datasource.mysql.password}")
    private String password;

    @Value("${spring.datasource.mssql.url}")
    private String ms_url;
    @Value("${spring.datasource.mssql.username}")
    private String ms_username;
    @Value("${spring.datasource.mssql.password}")
    private String ms_password;


    @Bean
    DataSource dsMy() {
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(url)
                .username(username)
                .password(password)
                .build();

//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setDatabaseName(url);
//        dataSource.setUser(username);
//        dataSource.setPassword(password);

        return dataSource;
    }


    @Bean
    DataSource dsMs() {
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                .url(ms_url)
                .username(ms_username)
                .password(ms_password)
                .build();

//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setDatabaseName(url);
//        dataSource.setUser(username);
//        dataSource.setPassword(password);

        return dataSource;
    }
}
