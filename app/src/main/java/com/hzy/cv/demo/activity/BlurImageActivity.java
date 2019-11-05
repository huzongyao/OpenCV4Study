package com.hzy.cv.demo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ImageUtils;
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

@Route(path = RouterHub.BLUR_ACTIVITY)
public class BlurImageActivity extends AppCompatActivity {

    public static final int MAX_BITMAP_SIZE = 2000;
    @BindView(R.id.blur_info_text)
    TextView mBlurInfoText;
    private Bitmap mDemoBitmap;

    @BindView(R.id.level_seek)
    AppCompatSeekBar mLevelSeek;
    @BindView(R.id.image_before)
    ImageView mImageBefore;
    @BindView(R.id.image_after)
    ImageView mImageAfter;
    int mBlurLevel;
    private ExecutorService mExecutorService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExecutorService = Executors.newSingleThreadExecutor();
        setContentView(R.layout.activity_blur_image);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        loadBitmapFromImage();
        setSeekBarEvent();
        doBlurProgress();
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

    private void setSeekBarEvent() {
        mBlurLevel = mLevelSeek.getProgress();
        mLevelSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mBlurLevel = i;
                doBlurProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
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
                        doBlurProgress();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void doBlurProgress() {
        mBlurInfoText.setText(getString(R.string.blur_level_int, mBlurLevel));
        mExecutorService.submit(() -> {
            Bitmap bitmap = mDemoBitmap.copy(Bitmap.Config.ARGB_8888, true);
            OpenCVApi.blurBitmap(bitmap, mBlurLevel);
            mImageAfter.post(() -> mImageAfter.setImageBitmap(bitmap));
        });
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

    @OnClick(R.id.image_before)
    public void onImageBeforeClicked() {
        ActionUtils.startImageContentAction(this, RequestCode.CHOOSE_IMAGE);
    }
}
