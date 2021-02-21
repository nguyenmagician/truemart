package com.example.truemart.config;

import com.example.truemart.model.RefererRedirectionAuthenticationSuccessHandler;
import com.example.truemart.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean
    public CustomUserDetailService customUserDetailService() {
        return  new CustomUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new SimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new CsrfRequestDataValueProcessor();
    }

    @Autowired
    public void config(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(customUserDetailService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/","/login").permitAll();

        http.authorizeRequests().antMatchers("/user","/requirelogin","/checkout","/wishlist","/compare","/cart" ).access("hasAnyAuthority('USER','ADMIN')");

        http.authorizeRequests().antMatchers("/admin").access("hasAuthority('ADMIN')");

        http.authorizeRequests()

                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler( myAuthenticationSuccessHandler())
                .failureForwardUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .rememberMe().key("uniqueAndSecret")
                .tokenValiditySeconds(86400);


    }

//                .defaultSuccessUrl("/",true)
}
