package com.srwing.gxylib.mmkv;

import android.content.Context;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.srwing.gxylib.GsonUtil;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Description:
 * Created srwing
 * Date: 2023/3/16
 * Email: 694177407@qq.com
 */

public class MmkvPlugin {
    static List<String> deleteAbleKeys;

    public static void initMMKV(Context context) {
        String rootDir = MMKV.initialize(context);
        deleteAbleKeys = new LinkedList<>();
        System.out.println("mmkv root: " + rootDir);
    }

    public static void initMMKV(Context context, String dir) {
        String rootDir = MMKV.initialize(context, dir);
        deleteAbleKeys = new LinkedList<>();
        System.out.println("mmkv root: " + rootDir);
    }

    /**
     * @param context  上下文
     * @param dir      存储目录，默认app私有目录
     * @param logLevel 等级
     */
    public static void initMMKV(Context context, String dir, MMKVLogLevel logLevel) {
        String rootDir = MMKV.initialize(context, dir, logLevel);
        deleteAbleKeys = new LinkedList<>();
        System.out.println("mmkv root: " + rootDir);
    }


    private static MMKV get() {
        return MMKV.defaultMMKV();
    }


    public static <V> void writeDeleteAble(String key, List<V> value) {
        writeDeleteAble(key, GsonUtil.toJson(value));
    }

    //gson 不支持范型,如果强行用范型，list中的数据类型不是T,而是LinkedTreeMap，LinkedTreeMap是Gson库内部数据模型，所以中间加一层 ParameterizedTypeImpl
    public static <T> List<T> readList(String key, Class<T> clazz) {
        try {
            Type type = new ParameterizedTypeImpl<T>(clazz);
            String str = readString(key, "");
            return new Gson().fromJson(str, type);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    //写入的 key-value 可以删除
    public static <V> void writeDeleteAble(String key, V value) {
        if (deleteAbleKeys == null)
            throw new IllegalStateException("You should Call MmkvPlugin.initMMKV() first");
        deleteAbleKeys.add(key);
        writeNoDelete(key, value);
    }

    //写入的 key-value 不可以删除
    public static <V> void writeNoDelete(String key, V value) {
        if (value instanceof Integer) {
            get().encode(key, (int) value);
        } else if (value instanceof Long) {
            get().encode(key, (long) value);
        } else if (value instanceof Float) {
            get().encode(key, (float) value);
        } else if (value instanceof byte[]) {
            get().encode(key, (byte[]) value);
        } else if (value instanceof Double) {
            get().encode(key, (double) value);
        } else if (value instanceof String) {
            get().encode(key, (String) value);
        } else if (value instanceof Boolean) {
            get().encode(key, (Boolean) value);
        } else if (value instanceof Parcelable) {
            get().encode(key, (Parcelable) value);
        } else if (value instanceof Set) {
            Set set = (Set) value;
            if (set.iterator().next() instanceof String) {
                get().encode(key, (Set<String>) value);
            }
        }
    }

    public static int readInt(String key, int defaultValue) {
        return get().decodeInt(key, defaultValue);
    }

    public static long readLong(String key, long defaultValue) {
        return get().decodeLong(key, defaultValue);
    }

    public static float readFloat(String key, float defaultValue) {
        return get().decodeFloat(key, defaultValue);
    }


    public static byte[] readBytes(String key, byte[] defaultValue) {
        return get().decodeBytes(key, defaultValue);
    }

    public static double readDouble(String key, double defaultValue) {
        return get().decodeDouble(key, defaultValue);
    }

    public static String readString(String key, String defaultValue) {
        return get().decodeString(key, defaultValue);
    }

    public static boolean readBoolean(String key, Boolean defaultValue) {
        return get().decodeBool(key, defaultValue);
    }

    public static <T extends Parcelable> T readParcelable(String key, Class<T> tClass) {
        return get().decodeParcelable(key, tClass);
    }

    public static Set<String> readStringSet(String key, Set<String> defaultValue) {
        return get().decodeStringSet(key, defaultValue);
    }

    //查询是否有这个key
    public static boolean containsKey(String key) {
        return get().containsKey(key);
    }

    //删除一个
    public static void removeValueForKey(String key) {
        get().removeValueForKey(key);
        deleteAbleKeys.remove(key);
    }

    //删除全部
    public static void removeValueForKeys() {
        get().removeValuesForKeys(deleteAbleKeys.toArray(new String[0]));
        deleteAbleKeys.clear();
    }

    public static class ParameterizedTypeImpl<T> implements ParameterizedType {
        Class<T> clazz;

        public ParameterizedTypeImpl(Class<T> clz) {
            clazz = clz;
        }

        @NonNull
        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @NonNull
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
