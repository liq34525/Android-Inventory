package comp3350.ei.presentation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class Camera {
    public static final int CAMERA_REQUEST = 1;

    private final Context context;
    private final Activity activity;

    public Camera(Context context) {
        this.context = context;
        this.activity = (Activity) context;
    }

    public void createPicture() {
        if (deviceHasCamera()) {
            if (!hasCameraPermissions()) {
                requestPermissions();
            } else {
                launchCameraApp();
            }
        }
    }

    private void launchCameraApp() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            ((Activity)context).startActivityForResult(takePictureIntent, 1);
        }
    }

    private boolean deviceHasCamera() {
        return (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA));
    }

    //Android 6.0 and higher need permission request
    private boolean hasCameraPermissions() {
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.CAMERA},
                CAMERA_REQUEST);
    }
}
