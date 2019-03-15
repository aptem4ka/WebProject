package com.epam.hotel.web.util.pagination;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Pagination {
    private int startPos;
    private int offset;
    private int currentPage;
    private boolean lastPage;


    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getStartPos() {
        return startPos;
    }

    public void nextPage(){
        currentPage++;
        startPos+=offset-1;
    }

    public void prevPage(){
        currentPage--;
        startPos-=offset-1;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset+1;
    }


    public <T> void lastPageControl(List<T> list){
        if (list.size()<=(offset-1)){
            setLastPage(true);
        }else {
            setLastPage(false);
            list.remove(list.size()-1);
        }

    }

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
