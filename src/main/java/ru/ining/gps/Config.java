package ru.ining.gps;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "ru.ining.gps.mappers", sqlSessionFactoryRef = "sqlSessionFactoryMySql")
public class Config implements WebMvcConfigurer {
    @Resource(name = "dsMy")
    DataSource dsMy;

    @Bean
    SqlSessionFactory sqlSessionFactoryMySql() {
        SqlSessionFactory sessionFactory = null;
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dsMy);
            sessionFactory = bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    @Bean
    SqlSessionTemplate sqlSessionTemplateMySql() {
        return new SqlSessionTemplate(sqlSessionFactoryMySql());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("logiN");
    }

    @Configuration
    @MapperScan(basePackages = "ru.ining.gps.mssqlmappers", sqlSessionFactoryRef = "sqlSessionFactoryMsSql")
    public class ConfigMSSQL {
        @Resource(name = "dsMs")
        DataSource dsMs;

        @Bean
        SqlSessionFactory sqlSessionFactoryMsSql() {
            SqlSessionFactory sessionFactory = null;
            try {
                SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
                bean.setDataSource(dsMs);
                sessionFactory = bean.getObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sessionFactory;
        }

        @Bean
        SqlSessionTemplate sqlSessionTemplateMsSql() {
            return new SqlSessionTemplate(sqlSessionFactoryMsSql());
        }

    }
}
