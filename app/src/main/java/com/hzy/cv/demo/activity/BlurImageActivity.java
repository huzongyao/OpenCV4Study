package com.hzy.cv.demo.activity;

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
import com.hzy.cv.demo.consts.RouterHub;
import com.hzy.cv.demo.ndk.OpenCVApi;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void doBlurProgress() {
        mBlurInfoText.setText(getString(R.string.blur_level_int, mBlurLevel));
        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = mDemoBitmap.copy(Bitmap.Config.ARGB_8888, true);
                OpenCVApi.blurBitmap(bitmap, mBlurLevel);
                mImageAfter.post(() -> mImageAfter.setImageBitmap(bitmap));
            }
        }.start();
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
}
