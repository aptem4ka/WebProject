package com.epam.hotel.web.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class URLFromRequest {
    private StringBuffer url;
    String paramName;

    public String createURL(HttpServletRequest req){

        Enumeration<String> params=req.getParameterNames();
        url=req.getRequestURL().append("?");

        while (params.hasMoreElements()){
        paramName=params.nextElement();
        url.append(paramName+"="+req.getParameter(paramName)+"&");
        }

    return url.toString();
    }
}
