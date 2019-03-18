package com.epam.hotel.web.filter.impl;

import com.epam.hotel.entity.User;
import com.epam.hotel.web.filter.Filter;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminFilter extends Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession().getAttribute(StringConstants.CURRENT_USER);

        if (user == null){
            response.sendRedirect(URLConstants.LOGIN_PAGE_COMMAND);
        }else if (user.getUserID()!=1){
            response.sendRedirect((String)request.getSession().getAttribute(StringConstants.PREV_PAGE_URL));

        }else{
                filterChain.doFilter(request, response);
        }
    }


}

