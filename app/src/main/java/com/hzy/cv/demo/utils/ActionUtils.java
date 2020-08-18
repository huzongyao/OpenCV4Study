package com.hzy.cv.demo.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;

public class ActionUtils {

    public static void startViewAction(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(url));
            Intent chooserIntent = Intent.createChooser(intent, null);
            ActivityUtils.startActivity(chooserIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getBmpFromIntent(Intent data) {
        Uri uri = data.getData();
        Bitmap bitmap = null;
        try {
            if (uri != null) {
                bitmap = MediaStore.Images.Media.getBitmap(Utils.getApp().getContentResolver(), uri);
            } else {
                Bundle bundleExtras = data.getExtras();
                if (bundleExtras != null) {
                    bitmap = bundleExtras.getParcelable("data");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void startImageContentAction(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
                .addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {"image/jpeg", "image/png", "image/gif"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent = Intent.createChooser(intent, null);
        ActivityUtils.startActivityForResult(activity, intent, requestCode);
    }

    public static void startImagePickerAction(Activity activity, int requestCode) {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent = Intent.createChooser(intent, null);
            ActivityUtils.startActivityForResult(activity, intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
