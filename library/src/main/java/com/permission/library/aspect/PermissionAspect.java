package com.permission.library.aspect;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

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
        final Object[] args = joinPoint.getArgs();
        Object target = joinPoint.getTarget();
        Log.i("PermissionAspectj", String.format("target %s", target.getClass().getName()));
        FragmentActivity context = null;
        if (target instanceof FragmentActivity) {
            context = (FragmentActivity) target;
        }
        if (context == null) {
            context = findContextByArgs(args);
        }
        if (context == null) {
            throw new IllegalArgumentException(String.format("The '%s' method needs to provide a parameter of type FragmentActivity", joinPoint.getSignature().getName()));
        }
        String[] permission = ann.value();
        boolean showRationale = ann.isShowRationale();
        PermissionRequester.getDefault()
                .targetPermissions(permission)
                .showRationale(showRationale)
                .callback(new PermissionCallback(args, target, joinPoint)).apply(context);
        return null;
    }

    private FragmentActivity findContextByArgs(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof FragmentActivity) {
                return (FragmentActivity) arg;
            }
        }
        return null;
    }
}
