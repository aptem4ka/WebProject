package com.epam.hotel.web.util;

import com.epam.hotel.web.util.constants.StringConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


/**
 * This class is designed to save previous Http-request to the Http-session.
 *
 * @author  Artsem Lashuk
 *
 * @see HttpServletRequest
 * @see javax.servlet.http.HttpSession
 */
public class SavePreviousCommand {

    /**
     * The method gets HTTP-session and saves
     * previous HTTP-GET request as an attribute.
     * "page" - is the name of HTTP-request parameter which used
     * to identify page scrolling. It shouldn't be saved.
     *
     * @param req {@link HttpServletRequest}
     */
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
