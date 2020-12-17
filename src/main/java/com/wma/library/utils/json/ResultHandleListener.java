package com.wma.library.utils.json;

import com.wma.library.base.BaseModule;

import java.util.List;

/**
 * create by wma
 * on 2020/12/7 0007
 */
public interface ResultHandleListener<T extends BaseModule> {

    public void success(T result);

    public void success(List<T> result);

    public void fail(String msg);
}
