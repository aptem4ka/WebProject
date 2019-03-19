package com.epam.hotel.web.util;

import com.epam.hotel.web.util.constants.StringConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class SavePreviousCommand {

    public static void saveCommand(HttpServletRequest req){
        String paramName;
        StringBuffer url;
        Enumeration<String> params=req.getParameterNames();
        url=req.getRequestURL().append("?");

        while (params.hasMoreElements()){
            paramName=params.nextElement();
            if (!paramName.equals("page")) {
                url.append(paramName + "=" + req.getParameter(paramName) + "&");
            }
        }
        req.getSession().setAttribute(StringConstants.PREV_PAGE_URL, url.toString());
    }
}
