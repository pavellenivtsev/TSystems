package com.tsystems.configuration;

import com.tsystems.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Security configuration
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                //for unregistered users
                .authorizeRequests().antMatchers("/registration").not().fullyAuthenticated()
                //for admins
                .antMatchers("/admin/**").hasRole("ADMIN")
                //for drivers
                .antMatchers("/driver/**").hasRole("DRIVER")
                //for registered users
                .antMatchers("/user/**").hasRole("USER")
                //for managers
                .antMatchers("/truck/**").hasRole("MANAGER")
                .antMatchers("/order/**").hasRole("MANAGER")
                .antMatchers("/manager/**").hasRole("MANAGER")
                //for all
                .antMatchers("/", "/resources/**").permitAll()
                .anyRequest().authenticated()
                .and()

                //Setting up to log in
                .formLogin().loginPage("/login").loginProcessingUrl("/loginAction")
                //Redirect to the cabinet after successful login
                .defaultSuccessUrl("/cabinet")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                //Redirect to the home page after logout
                .and()
                .logout().permitAll().logoutSuccessUrl("/");
    }

    //Global security configuration
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(bCryptPasswordEncoder().encode("admin"))
                .authorities("ADMIN");
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}