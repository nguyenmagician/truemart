package com.example.truemart.config;

import com.example.truemart.entity.HttpSessionCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class BeansConfig {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public HttpSessionCollector httpSessionCollector() {
        return new HttpSessionCollector();
    }


}
