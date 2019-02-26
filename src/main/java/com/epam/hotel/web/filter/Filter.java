package com.epam.hotel.web.filter;

import javax.servlet.*;
import java.io.IOException;

public abstract class Filter implements javax.servlet.Filter {

    private FilterConfig filterConfig;

    @Override
    public abstract void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig=filterConfig;
    }

    @Override
    public void destroy() {
        filterConfig=null;

    }
}
