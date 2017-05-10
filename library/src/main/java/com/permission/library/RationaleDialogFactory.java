package com.permission.library;

import android.app.Dialog;

/**
 * Created by zhangluya on 2016/12/7.
 */

public interface RationaleDialogFactory {

    Dialog createDialog(PermissionInterface permission);
}
