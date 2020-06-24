package com.hzy.cv.demo.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ImageUtils;
import com.github.chrisbanes.photoview.PhotoView;
import com.hzy.cv.demo.R;
import com.hzy.cv.demo.consts.RequestCode;
import com.hzy.cv.demo.consts.RouterHub;
import com.hzy.cv.demo.ndk.OpenCVApi;
import com.hzy.cv.demo.utils.ActionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterHub.SIMPLE_EFFECT_ACTIVITY)
public class SimpleEffectActivity extends AppCompatActivity {

    public static final int MAX_BITMAP_SIZE = 2000;
    @BindView(R.id.effect_spinner)
    Spinner mEffectSpinner;
    @BindView(R.id.image_before)
    ImageView mImageBefore;
    @BindView(R.id.image_after)
    ImageView mImageAfter;

    private ExecutorService mExecutorService;
    private Bitmap mDemoBitmap;
    private int mEffectIndex = 0;
    private Dialog mPreviewDialog;
    private PhotoView mPhotoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_effect);
        ButterKnife.bind(this);
        mExecutorService = Executors.newSingleThreadExecutor();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        loadBitmapFromImage();
        mEffectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mEffectIndex = i;
                doImageProcessAsync();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        mPreviewDialog = new Dialog(this);
        mPreviewDialog.setContentView(R.layout.dialog_preview_image);
        mPhotoView = mPreviewDialog.findViewById(R.id.preview_view);
    }

    private void doImageProcessAsync() {
        mExecutorService.submit(() -> {
            Bitmap bitmap = mDemoBitmap.copy(Bitmap.Config.ARGB_8888, true);
            switch (mEffectIndex) {
                case 0:
                    OpenCVApi.adaptiveThreshold(bitmap);
                    break;
                case 1:
                    OpenCVApi.negativeColor(bitmap);
                    break;
                case 2:
                    OpenCVApi.rgba2Gray(bitmap);
                    break;
                case 3:
                    OpenCVApi.cannyImage(bitmap);
                    break;
                case 4:
                    OpenCVApi.claheImage(bitmap);
                    break;
                case 5:
                    OpenCVApi.bilateralFilter(bitmap);
                    break;
                case 6:
                    OpenCVApi.blobDetect(bitmap);
                    break;
            }
            runOnUiThread(() -> {
                mImageAfter.setImageBitmap(bitmap);
                mPhotoView.setImageBitmap(bitmap);
            });
        });
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
                        mImageBefore.setImageBitmap(mDemoBitmap);
                        mImageAfter.setImageBitmap(mDemoBitmap);
                        doImageProcessAsync();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadBitmapFromImage() {
        try {
            InputStream is = getAssets().open("demo.jpg");
            mDemoBitmap = ImageUtils.getBitmap(is, MAX_BITMAP_SIZE, MAX_BITMAP_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mImageBefore.setImageBitmap(mDemoBitmap);
        mImageAfter.setImageBitmap(mDemoBitmap);
    }

    @OnClick({R.id.image_before, R.id.image_after})
    public void onImageViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_before:
                ActionUtils.startImageContentAction(this, RequestCode.CHOOSE_IMAGE);
                break;
            case R.id.image_after:
                mPreviewDialog.show();
                break;
        }
    }
}
