package com.permission.library;

import android.content.Context;

/**
 * Created by zhangluya on 2016/12/7.
 */

public interface PermissionInterface {

    void requestPermissions(String[] permissions);

    String[] getPermissions();

    Context context();
}
