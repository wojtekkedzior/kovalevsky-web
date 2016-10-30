package kovalevsky;


import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import kovalevsky.config.CORSFilter;
import kovalevsky.imaging.DataProcessor;

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