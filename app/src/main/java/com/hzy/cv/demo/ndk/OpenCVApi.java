package com.hzy.cv.demo.ndk;

import android.graphics.Bitmap;

public class OpenCVApi {

    public static native String getVersionString();

    public static native int[] detectFaceFromBitmap(String cascadePath, Bitmap bitmap);

    public static native int blurBitmap(Bitmap bitmap, int level);

    static {
        System.loadLibrary("opencv");
    }
}
