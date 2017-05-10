package com.permission.library.aspect;

import com.permission.library.annotation.Complete;
import com.permission.library.annotation.Refused;
import com.permission.library.callback.RequestPermissionCallback;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;

/**
 * Created by zhangluya on 2017/5/10.
 */
class PermissionCallback extends RequestPermissionCallback {

    private Object[] jointArgs;
    private Object callback;
    private ProceedingJoinPoint jointPoint;

    PermissionCallback(Object[] jointArgs, Object callback, ProceedingJoinPoint jointPoint) {
        this.jointArgs = jointArgs;
        this.callback = callback;
        this.jointPoint = jointPoint;
    }

    @Override
    public void complete() {
        MethodUtils.invokeByAnnotation(callback, Complete.class);
    }

    @Override
    public void refused(List<String> permission) {
        MethodUtils.invokeByAnnotation(callback, Refused.class, permission);
    }

    @Override
    public void onAllowed() {
        try {
            jointPoint.proceed(jointArgs);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
