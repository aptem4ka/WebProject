package com.epam.hotel.web.filter.impl;

import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.filter.Filter;
import com.epam.hotel.web.util.constants.StringConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class is used to get information about orders that need confirmation.
 *
 * @author Artsem Lashuk
 *
 * @see Filter
 */
public class NeedConfirmationOrdersFilter extends Filter {

    /**
     * The method checks if the user is an admin by calling {@link User#getUserID()}
     * and calls {@link com.epam.hotel.service.AdminService} to read number of
     * orders waiting for confirmation and to put them in the session as an attribute.
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

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        int orders = 0;

        User user = (User)session.getAttribute(StringConstants.CURRENT_USER);

        if (user!=null){
            if (user.getUserID()==1){
                try {
                    orders = ServiceFactory.getInstance().getAdminService().needConfirmationOrders();
                    session.setAttribute(StringConstants.ORDERS_WAITING, orders);
                }catch (ServiceException e){
                    throw new ServletException(e);
                }
            }
        }
        filterChain.doFilter(request,response);

    }
}
