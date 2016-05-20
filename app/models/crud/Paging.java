package models.crud;

import java.util.ArrayList;
import java.util.List;

public class Paging {


    public static Integer getNowPage(Integer i, Integer maxPage) {
        if(i <= 1) {
            return 1;
        } else if(i >= maxPage) {
            return maxPage;
        } else {
            return i;
        }
    }

    public static Integer getPrevPage(Integer i) {
        if(i - 1 < 1) {
            return 0;
        } else {
            return i - 1;
        }
    }

    public static Boolean canPrevPage(Integer i) {
        if(i - 1 < 1) {
            return false;
        } else {
            return true;
        }
    }

    public static Integer getNextPage(Integer i, Integer maxPage) {
        if(i >= maxPage) {
            return maxPage;
        } else {
            return i + 1;
        }
    }

    public static Boolean canNextPage(Integer i, Integer maxPage) {
        if(i >= maxPage) {
            return false;
        } else {
            return true;
        }
    }

    public static List<PagingBean> getPagingList(Integer page, Integer maxPage, Integer display) {
        List<PagingBean> result = new ArrayList<PagingBean>();
        Integer displayHalf = display / 2;
        Integer endNavPageRest = (displayHalf - (maxPage - page) > 0) ? displayHalf - (maxPage - page) : 0;
        Integer startNavPage = (page - displayHalf - endNavPageRest > 1) ? page - displayHalf - endNavPageRest : 1;
        Integer startNavPageRest = (display - page - displayHalf > 0 && maxPage > display) ? display - page - displayHalf : 0;
        Integer endNavPageOption = (display > maxPage && maxPage - page - displayHalf > 0) ? maxPage - page - displayHalf : 0;
        Integer endNavPage = (displayHalf + page > maxPage) ? maxPage : displayHalf + page + startNavPageRest + endNavPageOption;

        for (Integer i = startNavPage; i <= endNavPage; i++) {
            result.add(new PagingBean(i, (i == page) ? true : false));
        }
        return result;
    }

}
