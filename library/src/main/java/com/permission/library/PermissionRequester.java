package com.permission.library;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.permission.library.callback.OnRequestPermissionListener;

/**
 * Created by zhangluya on 16/8/17.
 */
public class PermissionRequester implements IPermission {

    private static PermissionRequester sInstance;

    private boolean mIsShowRationale;
    private RationaleDialogFactory mFactory;
    private OnRequestPermissionListener mListener;
    private String[] mTargetPermissions;

    private PermissionRequester() {
    }

    public static PermissionRequester getDefault() {
        if (sInstance == null) {
            sInstance = new PermissionRequester();
        }
        return sInstance;
    }

    public PermissionRequester showRationale(boolean isShowRationale) {
        this.mIsShowRationale = isShowRationale;
        return this;
    }

    public PermissionRequester setRationaleDialogFactory(RationaleDialogFactory factory) {
        mFactory = factory;
        return this;
    }

    public PermissionRequester callback(OnRequestPermissionListener listener) {
        mListener = listener;
        return this;
    }

    public PermissionRequester targetPermissions(String... permissions) {
        mTargetPermissions = permissions;
        return this;
    }

    public void apply(Context context) {
        request(context, mTargetPermissions, mListener);
    }

    @Override
    public void request(Context context, String[] permissions, OnRequestPermissionListener listener) {
        PermissionChecker.CheckResult result = PermissionChecker.check(context, permissions);
        if (result.isGranted()) {
            callListener(listener);
            return;
        }

        PermissionFragment permissionFragment =
                mFactory == null
                        ? PermissionFragment.createPermissionFragment(result, mIsShowRationale, listener) :
                        PermissionFragment.createPermissionFragment(result, mIsShowRationale, listener, mFactory);
        ((FragmentActivity) context)
                .getSupportFragmentManager()
                .beginTransaction()
                .add(permissionFragment, permissionFragment.getClass().getName())
                .addToBackStack("Permission")
                .commit();
    }

    void destroyListener() {
        if (mListener != null) {
            mListener = null;
        }
        if (mFactory != null) {
            mFactory = null;
        }
        if (mTargetPermissions != null) {
            mTargetPermissions = null;
        }
    }

    private void callListener(OnRequestPermissionListener listener) {
        if (listener != null) {
            listener.onAllowed();
            listener.complete();
        }
        destroyListener();
    }
}
