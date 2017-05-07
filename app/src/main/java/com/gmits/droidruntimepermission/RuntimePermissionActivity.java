/*
 *   Copyright (C) 2017 Goldenmace IT Solutions
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.gmits.droidruntimepermission;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;

/**
 * {@link RuntimePermissionActivity} is an abstract class which can be used a super class for the
 * {@link AppCompatActivity} which needs to ask for Runtime permission.
 * <p>
 * <p>
 * How to use :
 * <p>
 * <code>
 * askRequestPermissions(permission array, button text,
 * permission identifier);
 * </code>
 * <p>
 * <p>
 *
 * Created by vhpatoliya on 10,April,2017.
 * GoldenMace IT Solutions
 */

public abstract class RuntimePermissionActivity extends AppCompatActivity {
    private SparseIntArray mDeniedKeys;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeniedKeys = new SparseIntArray();
    }

    /**
     * Call Request App Permission
     *
     * @param requestedPermissions list of the permissions which you want to be granted from user
     * @param stringId
     * @param requestCode
     */
    public void askRequestPermissions(final String[] requestedPermissions,
                                      final int stringId, final int requestCode) {
        mDeniedKeys.put(requestCode, stringId);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean shouldShowRequestPermissionRationale = false;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale) {
                // You can either show Snackbar or AlertDialog based on your requirement
                Snackbar.make(findViewById(android.R.id.content), stringId,
                        Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).setAction("GRANT",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(RuntimePermissionActivity.this, requestedPermissions, requestCode);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
            }
        } else {
            onRequestPermissionsGranted(requestCode);
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *                     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     * @see android.support.v4.app.FragmentActivity#onRequestPermissionsResult(int, String[], int[])
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onRequestPermissionsGranted(requestCode);
        } else {
            openAppSetting(requestCode);
        }
    }

    /**
     * To redirect user to the app setting to enable permission manually.
     *
     * @param requestCode request code which is used to stored access permission in {@link #mDeniedKeys}
     */
    private void openAppSetting(int requestCode) {
        Snackbar.make(findViewById(android.R.id.content), mDeniedKeys.get(requestCode), Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).setAction("ENABLE", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        }).show();
    }

    /**
     * Callback when requested permission is granted. By overriding this method user can process further on permission granted.
     *
     * @param requestCode The request code passed in {@link #onRequestPermissionsResult(int, String[], int[])}
     */
    public abstract void onRequestPermissionsGranted(int requestCode);
}
