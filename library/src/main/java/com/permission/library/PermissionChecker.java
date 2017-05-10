package com.permission.library;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangluya on 2016/12/6.
 */
 class PermissionChecker {

    static CheckResult check(Context context, String[] permissions) {
        CheckResult checkResult = new CheckResult();
        // 需要申请的全部权限
        final ArrayList<String> permissionsList = new ArrayList<>();
        final ArrayList<String> permissionsNeededShowRationale = new ArrayList<>();
        for (String permission : permissions) {
            if (addPermission(context, permissionsList, permission)) {
                permissionsNeededShowRationale.add(permission);
            }
        }
        checkResult.setGranted(permissionsList.size() <= 0);
        checkResult.setNeedShowRationale(permissionsNeededShowRationale.size() > 0);
        checkResult.setNeededShowRationalePermissions(permissionsNeededShowRationale);
        checkResult.setPermissions(permissionsList);
        return checkResult;
    }

    private static boolean addPermission(Context context, List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                return true;
            }
        }
        return false;
    }

    static class CheckResult implements Parcelable {

        private boolean isGranted;
        private boolean needShowRationale;
        private ArrayList<String> permissions;
        private ArrayList<String> neededShowRationalePermissions;

        CheckResult() {}

        private CheckResult(Parcel in) {
            isGranted = in.readByte() != 0;
            needShowRationale = in.readByte() != 0;
            permissions = in.createStringArrayList();
            neededShowRationalePermissions = in.createStringArrayList();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte((byte) (isGranted ? 1 : 0));
            dest.writeByte((byte) (needShowRationale ? 1 : 0));
            dest.writeStringList(permissions);
            dest.writeStringList(neededShowRationalePermissions);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CheckResult> CREATOR = new Creator<CheckResult>() {
            @Override
            public CheckResult createFromParcel(Parcel in) {
                return new CheckResult(in);
            }

            @Override
            public CheckResult[] newArray(int size) {
                return new CheckResult[size];
            }
        };

        boolean isGranted() {
            return isGranted;
        }

        void setGranted(boolean granted) {
            this.isGranted = granted;
        }

        boolean isNeedShowRationale() {
            return needShowRationale;
        }

        void setNeedShowRationale(boolean needShowRationale) {
            this.needShowRationale = needShowRationale;
        }

        List<String> getNeededShowRationalePermissions() {
            return neededShowRationalePermissions;
        }

        void setNeededShowRationalePermissions(ArrayList<String> mNeededShowRationalePermissions) {
            this.neededShowRationalePermissions = mNeededShowRationalePermissions;
        }

        ArrayList<String> getPermissions() {
            return permissions;
        }

        void setPermissions(ArrayList<String> permissions) {
            this.permissions = permissions;
        }
    }
}
