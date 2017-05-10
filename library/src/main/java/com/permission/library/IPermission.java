package com.permission.library;

import android.content.Context;

import com.permission.library.callback.OnRequestPermissionListener;

/**
 * Created by zhangluya on 16/8/17.
 */
public interface IPermission {

    void request(Context context, String[] permission, OnRequestPermissionListener listener);
}
