//
// Created by huzongyao on 2019/10/31.
//

#ifndef OPENCV4STUDY_OPENCVUTILS_H
#define OPENCV4STUDY_OPENCVUTILS_H

#include <jni.h>
#include <opencv2/opencv.hpp>
#include <android/bitmap.h>

using namespace cv;

class OpenCVUtils {
public:
    static void *lockABitmap(JNIEnv *env, jobject bitmap, AndroidBitmapInfo &info);

    static Mat lockABitmap2Mat(JNIEnv *env, jobject bitmap, AndroidBitmapInfo &info);

    static jintArray rectVector2AIntArray(JNIEnv *env, const std::vector<Rect> &rects);
};


#endif //OPENCV4STUDY_OPENCVUTILS_H
