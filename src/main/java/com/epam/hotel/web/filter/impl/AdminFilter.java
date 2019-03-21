package com.epam.hotel.web.filter.impl;

import com.epam.hotel.entity.User;
import com.epam.hotel.web.filter.Filter;
import com.epam.hotel.web.util.constants.StringConstants;
import com.epam.hotel.web.util.constants.URLConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class validates admin rights before using admin tools.
 *
 * @author Artsem Lashuk
 *
 * @see Filter
 */
public class AdminFilter extends Filter {

    /**
     * The method tries to get current user from the session and to check
     * if the user is an admin by calling {@link User#getUserID()}. If current user
     * is not an admin or its null, method executes a redirect to the login page command.
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

