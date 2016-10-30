package kovalevsky;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http
	      .antMatcher("/**")
	      .authorizeRequests()
	        .antMatchers("/login**", "/webjars/**", "/originalImage", "/image" , "/update")
	        .permitAll()
	      .anyRequest()
	        .authenticated();
	    
//	    //TODO
	    http.csrf().disable();
	  }

}
