package com.epam.hotel.web.util.constants;

/**
 * @author Artsem Lashuk
 *
 * The class is used to keep links to the JSP pages and commands
 * @see com.epam.hotel.web.command.Command
 */
public class URLConstants {

    public final static String GO_TO_INDEX = "index.jsp";
    public final static String ROOM_LIST_PAGE = "/WEB-INF/jsp/RoomList.jsp";
    public final static String ROOM_INFO_PAGE = "/WEB-INF/jsp/RoomInfo.jsp";
    public final static String REGISTER_PAGE = "/WEB-INF/jsp/Registration.jsp";
    public final static String INDEX_PAGE = "/WEB-INF/jsp/IndexPage.jsp";
    public final static String LOGIN_PAGE = "/WEB-INF/jsp/SignIn.jsp";
    public final static String ORDER_DETAILS_PAGE = "/WEB-INF/jsp/OrderDetails.jsp";
    public final static String CONTROL_PAGE = "/WEB-INF/jsp/admin/Control.jsp";
    public final static String PROFILE_PAGE = "/WEB-INF/jsp/Profile.jsp";
    public final static String SEARCH_RESULT_PAGE = "/WEB-INF/jsp/RoomSearchResult.jsp";
    public final static String CHANGE_ORDER_FORM = "/WEB-INF/jsp/ChangeOrderForm.jsp";
    public final static String CHANGE_ORDER_RESULT_PAGE = "/WEB-INF/jsp/ChangeOrderSearchResult.jsp";
    public final static String EDIT_INFO_PAGE = "/WEB-INF/jsp/ChangeOrderDetails.jsp";
    public final static String REVIEWS_PAGE = "/WEB-INF/jsp/Reviews.jsp";
    public final static String SUCCESS_REVIEW_PAGE = "/WEB-INF/jsp/ReviewSuccess.jsp";
    public final static String REVIEW_MODERATION_PAGE = "/WEB-INF/jsp/admin/ReviewModeration.jsp";
    public final static String ORDER_SEARCH_PAGE = "/WEB-INF/jsp/admin/OrderSearch.jsp";
    public final static String USER_SEARCH_PAGE = "/WEB-INF/jsp/admin/UserSearch.jsp";
    public final static String ABOUT_PAGE = "/WEB-INF/jsp/About.jsp";

    public final static String RETRY_REGISTRATION_COMMAND = "main?command=register_page&";
    public final static String LOGIN_PAGE_COMMAND = "main?command=login_page";
    public final static String ORDER_DETAILS_COMMAND = "main?command=order_details";
    public final static String CHANGE_ORDER_CONGRATS_PAGE_COMMAND = "user?command=change_order_congrats_page";
    public final static String CHANGE_ORDER_RESULT_PAGE_COMMAND = "user?command=change_order_result_page";
    public final static String CONTROL_COMMAND = "admin?command=control";
    public final static String SUCCESS_REVIEW_PAGE_COMMAND = "main?command=success_review_page";
    public final static String SEARCH_RESULT_PAGE_COMMAND = "main?command=search_result_page";

}
