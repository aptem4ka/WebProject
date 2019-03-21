package com.epam.hotel.web.filter.impl;

import com.epam.hotel.entity.User;
import com.epam.hotel.web.filter.Filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This filter is used to set character encoding UTF-8 to every request and response.
 *
 * @author Artsem Lashuk
 *
 * @see Filter
 */
public class EncodingFilter extends Filter {
    /**
     *This method sets UTF-8 character encoding to ServletRequest and ServletResponse.
     *
     * @param servletRequest {@link ServletRequest}
     * @param servletResponse {@link ServletResponse}
     * @param filterChain {@link FilterChain}
     * @throws IOException if In/Out exception occurs
     * @throws ServletException if Servlet exception occurs
     * @see User
     * @see HttpSession
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html; charset=UTF-8");

        filterChain.doFilter(servletRequest, servletResponse);


    }
}
