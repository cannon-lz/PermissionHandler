package com.permission.library.aspect;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.permission.library.PermissionRequester;
import com.permission.library.annotation.Permissions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by zhangluya on 2017/5/10.
 */
@Aspect
public class PermissionAspect {

    @Pointcut("execution(@ com.permission.library.annotation.Permissions * *(..)) && @annotation(ann)")
    public void method(Permissions ann) {
    }

    @Around("method(ann)")
    public Object requestPermissionAndExecute(final ProceedingJoinPoint joinPoint, Permissions ann) throws Throwable {
        String[] permission = ann.value();
        final Object[] args = joinPoint.getArgs();
        Context context = null;
        final Object firstArg = args[0];
        if (firstArg instanceof FragmentActivity) {
            context = (FragmentActivity) firstArg;
        }

        if (context == null) {
            context = (FragmentActivity) args[1];
        }

        PermissionRequester.getDefault().targetPermissions(permission).callback(new PermissionCallback(args, firstArg, joinPoint)).apply(context);
        return null;
    }

}
