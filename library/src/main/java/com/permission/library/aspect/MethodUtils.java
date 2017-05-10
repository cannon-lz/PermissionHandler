package com.permission.library.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zhangluya on 2017/5/10.
 */

public class MethodUtils {

    public static Object invokeByAnnotation(Object target, Class<? extends Annotation> ann, Object... args) {
        Class<?> targetClass = target.getClass();
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(ann)) {
                try {
                    return method.invoke(target, args);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
