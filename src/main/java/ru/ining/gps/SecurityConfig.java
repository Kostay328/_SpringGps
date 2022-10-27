package ru.ining.gps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
            http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/css/**", "/js/**", "/images/**", "/pdf/**", "rest/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/").hasAnyAuthority("0", "1", "AADMIN")
                    .antMatchers(HttpMethod.GET, "/add_doc/**").hasAnyAuthority("0", "1", "AADMIN")
                    .antMatchers(HttpMethod.GET, "/edit_doc/**").hasAnyAuthority("0", "1", "AADMIN")
                    .antMatchers(HttpMethod.GET, "/multi_add_doc/**").hasAnyAuthority("1")
                    .antMatchers(HttpMethod.GET, "/add_tmp/**").hasAnyAuthority("1")
                    .antMatchers(HttpMethod.GET, "/edit_tmp/**").hasAnyAuthority("1")
                    .antMatchers(HttpMethod.GET, "/tmp_lst/**").hasAnyAuthority("1")
//            .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Value("${user.mssql.url}")
    private String userUrl;

    @Value("${spring.datasource.mssql.username}")
    private String username;

    @Value("${spring.datasource.mssql.password}")
    private String password;



    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(userUrl);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) {

        try {
            auth.jdbcAuthentication().dataSource(dataSource())
                    .usersByUsernameQuery("select Email, CONCAT('{bcrypt}',newpass),1 as enabled from Psnmst where Email = ?")
                    .authoritiesByUsernameQuery("SELECT Email as login, Rank as Authority FROM Psnmst WHERE Email = ?");
        } catch (Exception e) { }
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}
