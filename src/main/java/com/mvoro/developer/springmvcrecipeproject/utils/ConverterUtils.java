package com.mvoro.developer.springmvcrecipeproject.utils;

import java.util.Set;

public class ConverterUtils {

    public static <T> boolean verifyNotNullOrEmpty(Set<T> set) {
        return set != null && set.size() > 0;
    }

}
