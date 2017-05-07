Android Marshmallow includes a new functionality to let users accept or denied permissions when running an app instead of granting them all when installing it. This approach gives the user more control over applications but requires developers to add lots of code to support it.

droid-Runtime Permission is an Android sample project that simplifies the process of requesting permissions at runtime.

Screenshots
-----------

![screenshot][1]

Usage
-----

To start using this sample project you just need to extends `RuntimePermissionActivity` :

```java
public class PermissionSampleActivity extends RuntimePermissionActivity {
   ...
 }
```

Single permission
-----
For each single permission :

```java
if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
    askRequestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, R.string.enable_permission, REQUEST_CONTACT_PERMISSIONS);
} else {
    // Permission already granted. 
    // add your logic
}
```


Multiple permissions
-----
For each group permission :

```java
if ((ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
     || (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
     || (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
     askRequestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
             R.string.enable_permission, REQUEST_GROUP_PERMISSIONS);
} else {
      // Permission already granted. 
      // add your logic
}
```
Permission Granted
-----
After granting permission write down your method or custom code :

```java
@Override
    public void onRequestPermissionsGranted(int requestCode) {
        switch (requestCode) {
            // Single permission granted
            case REQUEST_CONTACT_PERMISSIONS:
                //TODO Add your logic
                break;
            
            // Group permission granted
            case REQUEST_GROUP_PERMISSIONS:
                //TODO Add your logic
                break;
        }
    }
```

Libraries used in this project
------------------------------

* [Butterknife][2]

License
-------

    Copyright (C) 2017 Goldenmace IT Solutions
     
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at
     
         http://www.apache.org/licenses/LICENSE-2.0
    
     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.

[1]: ./art/gmits.gif
[2]: https://github.com/JakeWharton/butterknife
