package models.crud;

import models.crud.PagingSetting;
import play.Logger;
import play.db.ebean.Model;
import static play.libs.F.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static play.libs.F.None;
import static play.libs.F.Some;

public class SearchEngine {

    public static <T> Option<List<T>> getSearchResult(Class<T> clazz, String column, String keyword, String pageSource, Integer pageMax) {

        Model.Finder<Long, T> find = new Model.Finder<Long, T>(Long.class, clazz);
        Option<Integer> pageNumber = toInt(pageSource);
        if(pageNumber.isDefined() && hasField(clazz, column).isDefined()) {
            Integer pageLow = (pageNumber.get() - 1 < 0) ? 0 : pageNumber.get() - 1;
            Integer page = (pageLow >= pageMax && pageMax > 0) ? pageMax - 1 : pageLow;
            System.out.println(pageLow + " / " + page + " / " + pageMax);
            return OptionUtil.apply(find
                    .where().eq(column, keyword)
                    .order().asc("created")
                    .findPagingList(PagingSetting.LIMIT).getPage(page)
                    .getList());
        }
        if(pageNumber.isDefined() && !hasField(clazz, column).isDefined()) {
            Integer pageLow = (pageNumber.get() - 1 < 0) ? 0 : pageNumber.get() - 1;
            Integer page = (pageLow >= pageMax && pageMax > 0) ? pageMax - 1 : pageLow;
            return OptionUtil.apply(find
                    .order().asc("created")
                    .findPagingList(PagingSetting.LIMIT).getPage(page)
                    .getList());
        }
        if(!pageNumber.isDefined() && !hasField(clazz, column).isDefined()) {
            return OptionUtil.apply(find
                    .order().asc("created")
                    .findPagingList(PagingSetting.LIMIT).getPage(0)
                    .getList());
        }
        return new None<List<T>>();
    }

    public static <T> Option<Integer> getSearchMaxPage(Class<T> clazz, String column, String keyword) {
        Model.Finder<Long, T> find = new Model.Finder<Long, T>(Long.class, clazz);
        if(hasField(clazz, column).isDefined()) {
            return OptionUtil.apply(find
                    .where().eq(column, keyword)
                    .findPagingList(PagingSetting.LIMIT)
                    .getTotalPageCount());
        }
        if(!hasField(clazz, column).isDefined()) {
            return OptionUtil.apply(find
                    .findPagingList(PagingSetting.LIMIT)
                    .getTotalPageCount());
        }

        return new None<Integer>();
    }

    public static class Form {
        public String column;
        public String keyword;
        public Integer page;
    }

    public static Option<Integer> toInt(String s) {
        Integer result;
        try {
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return new Some<Integer>(1);
        } catch (Exception e) {
            return new Some<Integer>(1);
        }
        return new Some<Integer>(result);
    }

    /**
     *
     * @param fieldName
     * @return
     */
    public static <T> Option<Class<T>> hasField(Class<T> clazz, String fieldName) {
        try {
            Field field = clazz.getField(fieldName);
            Logger.info(">>>> Has Field!");
            return new Some<Class<T>>(clazz);
        } catch (NoSuchFieldException e) {
            Logger.error(">>>> NoSuchFieldException!");
            e.printStackTrace();
            return new None<Class<T>>();
        }

    }
}

