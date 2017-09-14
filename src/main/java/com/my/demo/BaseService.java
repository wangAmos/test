package com.my.demo;

import java.util.List;

public interface BaseService<T> {

    boolean insert(T t);

    boolean update(T t);

    boolean updateBySelective(T t);

    boolean deleteById(Object id);

    T getById(Object id);

    List<T> findBySelective(T t);

    List<T> findPageList(T t);

    int findPageCount(T t);

}
