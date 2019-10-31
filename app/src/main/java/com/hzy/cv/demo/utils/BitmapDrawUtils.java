package com.hzy.cv.demo.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class BitmapDrawUtils {

    public static Rect[] getFromIntArray(int[] array) {
        int rectLength = array.length / 4;
        Rect[] rectArray = new Rect[rectLength];
        for (int i = 0; i < rectLength; i++) {
            Rect rect = new Rect(array[i * 4],
                    array[i * 4 + 1],
                    array[i * 4 + 2],
                    array[i * 4 + 3]);
            rectArray[i] = rect;
        }
        return rectArray;
    }

    // draw by canvas
    public static void drawRectOnBitmap(Bitmap bitmap, Rect[] rects) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        for (Rect rect : rects) {
            canvas.drawRect(rect, paint);
        }
    }
}
