package com.hzy.cv.demo.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hzy.cv.demo.R;
import com.hzy.cv.demo.consts.RouterHub;
import com.hzy.cv.demo.ndk.OpenCVApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.opencv_version)
    TextView mOpenCvVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showVersionInfo();
    }

    private void showVersionInfo() {
        String vInfo = getString(R.string.opencv_version) + OpenCVApi.getVersionString();
        mOpenCvVersion.setText(vInfo);
    }

    @OnClick(R.id.button_face_detect)
    public void onDetectClicked() {
        ARouter.getInstance().build(RouterHub.DETECT_ACTIVITY).navigation();
    }

    @OnClick(R.id.button_blur_demo)
    public void onBlueImageClicked() {
        ARouter.getInstance().build(RouterHub.BLUR_ACTIVITY).navigation();
    }
}
