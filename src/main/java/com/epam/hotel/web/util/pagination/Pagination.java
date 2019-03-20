package com.epam.hotel.web.util.pagination;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Artsem Lashuk
 *
 * This class is used to paginate long lists.
 */

public class Pagination {

    private int startPos;
    private int offset;
    private int currentPage;
    private boolean lastPage;

    private Pagination() {
    }

    /**
     * The method gets a field {@link Pagination#lastPage}
     * @return returns true if the actual page is the last
     */
    public boolean isLastPage() {
        return lastPage;
    }

    /**
     * The method sets a field {@link Pagination#lastPage}
     * @param lastPage is true if the actual page is the last
     */
    private void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    /**
     * The method gets a field {@link Pagination#startPos}
     * @return returns the start position for reading data from DB
     */
    public int getStartPos() {
        return startPos;
    }

    /**
     * Increases the value of the field {@link Pagination#currentPage}
     * and adds offset to the value of the start position
     */
    private void nextPage(){
        currentPage++;
        startPos+=offset-1;
    }

    /**
     * Decreases the value of the field {@link Pagination#currentPage}
     * and subtracts offset from the value of the start position
     */
    private void prevPage(){
        currentPage--;
        startPos-=offset-1;
    }

    /**
     * The method sets a filed {@link Pagination#startPos}
     * @param startPos is the start position for reading data from DB
     */
    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    /**
     * The method gets a field {@link Pagination#offset}
     * @return the current offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * The method sets a field {@link Pagination#offset}
     * @param offset is the number of records to read from DB
     */
    public void setOffset(int offset) {
        this.offset = offset+1;
    }


    /**
     * The method is used to check if the current page is last.
     * Paginator adds 1 extra record to check if there more records.
     * After the last page control this extra record will be removed
     * and method will return the number of records that equals to the
     * offset.
     *
     * @param list the list of records with one extra record for last
     *             page control.
     * @param <T> any java bean
     */
    public <T> void lastPageControl(List<T> list){
        if (list.size()<=(offset-1)){
            setLastPage(true);
        }else {
            setLastPage(false);
            list.remove(list.size()-1);
        }

    }

    /**
     * This static method is used to add paginator to the session attribute
     * or to get existing paginator from the session if it exists.
     *
     * @param req {@link HttpServletRequest}
     * @param paginatorName it the name of the paginator which is used as the name
     *                      of the session attribute. This name will be used from
     *                      the JSP page to read the current condition of the paginator.
     * @return {@link Pagination} reference which is created or loaded
     * from the current session
     * @see javax.servlet.http.HttpSession
     */
    public static Pagination setupPaginator(HttpServletRequest req, String paginatorName){
        Pagination pagination;
        String page = req.getParameter("page");
        if (page==null || req.getSession().getAttribute(paginatorName)==null){
            pagination = new Pagination();
            pagination.setStartPos(0);
            pagination.setOffset(5);
            req.getSession().setAttribute(paginatorName,pagination);
        } else {
                pagination = (Pagination)req.getSession().getAttribute(paginatorName);
                if (req.getParameter("page").equals("next")){
                    pagination.nextPage();
                }else if (req.getParameter("page").equals("prev")){
                    pagination.prevPage();
                }
        }
        return pagination;
    }



}
