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
import java.io.IOException;

/**
 * @author Artsem Lashuk
 *
 * This class is used to control access to user content
 *
 * @see Filter
 */
public class UserFilter extends Filter {

    /**
     * The method checks availability of the user in the current session.
     * It redirects to the login page if there is no user in the session.
     *
     * @param servletRequest {@link ServletRequest}
     * @param servletResponse {@link ServletResponse}
     * @param filterChain {@link FilterChain}
     * @throws IOException if the In/Out exception occurs
     * @throws ServletException if Servlet exception occurs
     * @see javax.servlet.http.HttpSession
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession().getAttribute(StringConstants.CURRENT_USER);

        if (user == null){
            response.sendRedirect(URLConstants.LOGIN_PAGE_COMMAND);
        }else{
            filterChain.doFilter(request, response);
        }

    }
}
