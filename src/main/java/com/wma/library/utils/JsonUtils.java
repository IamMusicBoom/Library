package com.wma.library.utils;

import com.google.gson.Gson;
import com.wma.library.base.BaseLoadFragment;
import com.wma.library.base.BaseModule;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * create by wma
 * on 2020/10/28 0028
 */
public class JsonUtils<T extends BaseModule> {


    Class<?> mAClass;

    public JsonUtils(Class<?> aClass) {
        mAClass = aClass;
    }

    public <T> List<T> getList(String json, Class clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        List<T> list =  new Gson().fromJson(json, type);
        return list;
    }


    public <T> T getObject(String json){
        T t =  new Gson().fromJson(json, getType());
        return t;
    }

    public Type getType() {
        ParameterizedType genType = (ParameterizedType) mAClass.getGenericSuperclass();

        Type[] actualTypeArguments = ((ParameterizedType) genType).getActualTypeArguments();

        return actualTypeArguments[0];
    }


    private  class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
