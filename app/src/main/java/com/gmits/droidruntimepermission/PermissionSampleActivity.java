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

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * {@link PermissionSampleActivity} is a sample activity which shows how to use
 * {@link RuntimePermissionActivity} to request single and group runtime permission
 * <p>
 * Created by vhpatoliya on 10,April,2017.
 * GoldenMace IT Solutions
 */
public class PermissionSampleActivity extends RuntimePermissionActivity {
    // Single permission request code
    private static final int REQUEST_CONTACT_PERMISSIONS = 101;
    private static final int REQUEST_CAMERA_PERMISSIONS = 102;
    private static final int REQUEST_EXTERNAL_PERMISSIONS = 103;
    // ===============

    // Group permission request code
    private static final int REQUEST_GROUP_PERMISSIONS = 104;
    // ===============

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permission);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btnAccessContact, R.id.btnAccessCamera, R.id.btnAccessExternalStorage, R.id.btnGroupPermission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // Ask Single Permission
            case R.id.btnAccessContact:
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    askRequestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, R.string.enable_permission, REQUEST_CONTACT_PERMISSIONS);
                } else {
                    readContactEnable();
                }
                break;
            case R.id.btnAccessCamera:
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    askRequestPermissions(new String[]{Manifest.permission.CAMERA}, R.string.enable_permission, REQUEST_CAMERA_PERMISSIONS);
                } else {
                    cameraEnable();
                }
                break;
            case R.id.btnAccessExternalStorage:
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    askRequestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, R.string.enable_permission, REQUEST_EXTERNAL_PERMISSIONS);
                } else {
                    externalStorageEnable();
                }
                break;
            // Ask Group Permission
            case R.id.btnGroupPermission:
                if ((ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                        || (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                        || (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    askRequestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            R.string.enable_permission, REQUEST_GROUP_PERMISSIONS);
                } else {
                    groupPermissionEnable();
                }
                break;
        }
    }

    private void readContactEnable() {
        //TODO Add your code
        Toast.makeText(this, "Read Contact runtime permission enable successfully...", Toast.LENGTH_SHORT).show();
    }

    private void cameraEnable() {
        //TODO Add your code
        Toast.makeText(this, "Camera runtime permission enable successfully...", Toast.LENGTH_SHORT).show();
    }

    private void externalStorageEnable() {
        //TODO Add your code
        Toast.makeText(this, "External Storage runtime permission enable successfully...", Toast.LENGTH_SHORT).show();
    }

    private void groupPermissionEnable() {
        //TODO Add your code
        Toast.makeText(this, "Group runtime permission enable successfully...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsGranted(int requestCode) {
        switch (requestCode) {
            // Single permission granted
            case REQUEST_CONTACT_PERMISSIONS:
                readContactEnable();
                break;
            case REQUEST_CAMERA_PERMISSIONS:
                cameraEnable();
                break;
            case REQUEST_EXTERNAL_PERMISSIONS:
                externalStorageEnable();
                break;
            // Group permission granted
            case REQUEST_GROUP_PERMISSIONS:
                groupPermissionEnable();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
