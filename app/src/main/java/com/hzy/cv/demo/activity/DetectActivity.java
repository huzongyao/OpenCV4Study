package com.hzy.cv.demo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.hzy.cv.demo.R;
import com.hzy.cv.demo.consts.RequestCode;
import com.hzy.cv.demo.consts.RouterHub;
import com.hzy.cv.demo.ndk.OpenCVApi;
import com.hzy.cv.demo.utils.ActionUtils;
import com.hzy.cv.demo.utils.BitmapDrawUtils;
import com.hzy.cv.demo.utils.FaceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterHub.DETECT_ACTIVITY)
public class DetectActivity extends AppCompatActivity {

    @BindView(R.id.opencv_image)
    ImageView mOpenCvImage;
    public static final int MAX_BITMAP_SIZE = 2000;
    private Bitmap mDemoBitmap;
    private ExecutorService mExecutorService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExecutorService = Executors.newSingleThreadExecutor();
        setContentView(R.layout.activity_face_detect);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        loadBitmapFromImage();
        detectFromBitmap();
    }

    @Override
    protected void onDestroy() {
        mExecutorService.shutdownNow();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void detectFromBitmap() {
        snakeBarShow("Detecting... Please Wait!!");
        mExecutorService.submit(() -> {
            String cascadePath = FaceUtils.ensureCascadeFile();
            int[] pointList = OpenCVApi.detectFaceFromBitmap(cascadePath, mDemoBitmap);
            Rect[] rectList = BitmapDrawUtils.getFromIntArray(pointList);
            BitmapDrawUtils.drawRectOnBitmap(mDemoBitmap, rectList);
            mOpenCvImage.post(() -> {
                mOpenCvImage.setImageBitmap(mDemoBitmap);
                String msg = getString(R.string.faces_detected, rectList.length);
                snakeBarShow(msg);
            });
        });
    }

    private void snakeBarShow(String msg) {
        SnackbarUtils.with(mOpenCvImage).setMessage(msg).show();
    }

    private void loadBitmapFromImage() {
        try {
            InputStream is = getAssets().open("demo.jpg");
            Bitmap bitmap = ImageUtils.getBitmap(is, MAX_BITMAP_SIZE, MAX_BITMAP_SIZE);
            mDemoBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            bitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mOpenCvImage.setImageBitmap(mDemoBitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCode.CHOOSE_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Bitmap bitmap = ActionUtils.getBmpFromIntent(data);
                    if (bitmap != null) {
                        mDemoBitmap.recycle();
                        bitmap = ImageUtils.compressBySampleSize(bitmap,
                                MAX_BITMAP_SIZE, MAX_BITMAP_SIZE, true);
                        mDemoBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                        bitmap.recycle();
                        mOpenCvImage.setImageBitmap(mDemoBitmap);
                        detectFromBitmap();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.opencv_image)
    public void onChooseImageClicked() {
        ActionUtils.startImageContentAction(this, RequestCode.CHOOSE_IMAGE);
    }
}
