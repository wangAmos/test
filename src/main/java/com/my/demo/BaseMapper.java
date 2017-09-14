package com.my.demo;

import java.util.List;

public interface BaseMapper<T> {

    void insert(T t);

    int update(T t);

    int updateBySelective(T t);

    int deleteById(Object id);

    T getById(Object id);

    List<T> findBySelective(T t);

    List<T> findPageList(T t);

    int findPageCount(T t);
}
