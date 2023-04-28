package com.booktown.servlet;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RepeatableFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
  
        HttpServletRequest httpServletRequest = new RepeatableHttpServletRequest((HttpServletRequest)servletRequest);
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    @Override
    public void destroy() {

    }

}