package com.example.pam_app1.Utils;

import java.lang.reflect.Array;

public class ArrayUtils {
    public static <T> T[] merge(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    public static <T> T[] append(T[] a, T... b) {
        return merge(a, b);
    }

    public static <T> T[] prepend(T[] a, T... b) {
        return merge(b, a);
    }
}
