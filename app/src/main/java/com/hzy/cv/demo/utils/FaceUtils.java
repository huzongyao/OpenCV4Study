package com.hzy.cv.demo.utils;

import android.app.Application;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.Utils;
import com.hzy.cv.demo.consts.ConfigConstant;

import java.io.File;
import java.io.InputStream;

public class FaceUtils {

    public static String ensureCascadeFile() {
        Application app = Utils.getApp();
        File dir = app.getExternalFilesDir(ConfigConstant.CASCADE_FOLDER_NAME);
        File cascadeFile = new File(dir, ConfigConstant.CASCADE_FILE_NAME);
        if (!cascadeFile.exists()) {
            try {
                InputStream is = app.getAssets().open(ConfigConstant.CASCADE_FILE_NAME);
                FileIOUtils.writeFileFromIS(cascadeFile, is);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cascadeFile.getPath();
    }
}
