package kovalevsky.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {
  
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, 
          FilterChain filterChain) throws IOException, ServletException {
      final HttpServletResponse response = (HttpServletResponse) servletResponse;
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Credentials", "true");
      response.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS");
      response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, x-auth-token, "
              + "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
      filterChain.doFilter(servletRequest, servletResponse);
  }

  public void init(FilterConfig arg0) throws ServletException {
    // TODO Auto-generated method stub
    
  }
  
  public void destroy() {
    // TODO Auto-generated method stub
    
  }


}