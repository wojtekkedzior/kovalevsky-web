package kovalevsky;


import javax.servlet.Filter;

import kovalevsky.app.DataProcessor;
import kovalevsky.config.CORSFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication 
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public Filter  getFilterConfig() {
      return new CORSFilter();
      
    }
    
    @Bean
    public DataProcessor getDataProcessor() {
      return new DataProcessor();
    }
    
}