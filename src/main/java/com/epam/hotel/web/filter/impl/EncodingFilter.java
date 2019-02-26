package com.epam.hotel.web.filter.impl;

import com.epam.hotel.web.filter.Filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EncodingFilter extends Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("running encoding filter");
        //Setting the character set for the request
        servletRequest.setCharacterEncoding("UTF-8");
        System.out.println("setting char enc fr responce");
        servletResponse.setContentType("text/html; charset=UTF-8");
        // pass the request on
        filterChain.doFilter(servletRequest, servletResponse);

        //Setting the character set for the response


    }
}
