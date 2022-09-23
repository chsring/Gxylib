package com.srwing.gxylib;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/17
 * Email: 694177407@qq.com
 */
public class GsonUtil {
    public static <T> T fromJson(String str, Class<T> tClass) {
        try {
            Gson g = new Gson();
            return g.fromJson(str, tClass);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toJson(Object clazz) {
        Gson g = new Gson();
        return g.toJson(clazz);
    }

    public static <T> T getObject(String jsonString, Type listType) {
        T t = null;
        try {
            Gson g = new Gson();
            t = g.fromJson(jsonString, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
