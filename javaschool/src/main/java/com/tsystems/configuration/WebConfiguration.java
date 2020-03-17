package com.tsystems.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

/**
 * Contains definition and dependence of bean components.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.tsystems")
public class WebConfiguration  {

    /**
     * Determines where to look for web views.
     */
    @Bean
    ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * For Entity-DTO conversion
     * @return bean
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
