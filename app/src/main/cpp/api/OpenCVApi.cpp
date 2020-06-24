//
// Created by Administrator on 2019/9/6.
//

#include "OpenCVApi.h"
#include "utils/OpenCVUtils.h"

#include <opencv2/core.hpp>
#include <opencv2/objdetect.hpp>
#include <android/bitmap.h>
#include <utils/EffectUtils.h>

using namespace cv;

/**
 * get native opencv version info
 * @param env
 * @param type
 * @return
 */
JNIEXPORT jstring JNICALL
JNI_FUNC(getVersionString)(JNIEnv *env, jclass type) {
    return env->NewStringUTF(CV_VERSION);
}

/**
 * detect faces from a image
 * @param env
 * @param type
 * @param cascadePath_
 * @param bitmap
 * @return  face rect points[(lx, ly, rx, ry)]
 */
JNIEXPORT jintArray JNICALL
JNI_FUNC(detectFaceFromBitmap)(JNIEnv *env, jclass type, jstring cascadePath_, jobject bitmap) {
    AndroidBitmapInfo info;
    Scalar rectColor(255, 11, 0);
    // load models
    const char *cascadePath = env->GetStringUTFChars(cascadePath_, nullptr);
    CascadeClassifier cascade(cascadePath);
    // read bitmap
    Mat image;
    OpenCVUtils::lockABitmap2Mat(env, bitmap, info, image);
    // convert bitmap to gray
    Mat gray;
    cvtColor(image, gray, COLOR_RGBA2GRAY);
    // detect
    std::vector<Rect> faces;
    cascade.detectMultiScale(gray, faces);
    // release resources
    env->ReleaseStringUTFChars(cascadePath_, cascadePath);
    AndroidBitmap_unlockPixels(env, bitmap);
    LOGD("[%d] Faces Detected!!", faces.size());
    for (const auto &face : faces) {
        rectangle(image, face, rectColor, 4);
    }
    jintArray rectPoints = OpenCVUtils::rectVector2AIntArray(env, faces);
    return rectPoints;
}


/**
 * blur a image
 * changes just made to the input image
 * @param env
 * @param type
 * @param bitmap
 * @param level blur level
 * @return status
 */
JNIEXPORT jint JNICALL
JNI_FUNC(blurBitmap)(JNIEnv *env, jclass type, jobject bitmap, jint level) {
    AndroidBitmapInfo info;
    // read bitmap
    Mat image;
    OpenCVUtils::lockABitmap2Mat(env, bitmap, info, image);
    LOGD("Blur Bitmap [%d x %d] [%d] !!", info.width, info.height, level);
    int kRadius = level * 10 + 1;
    GaussianBlur(image, image, Size(kRadius, kRadius), 0);
    // show blur level
    std::ostringstream oss;
    oss << "Blur Level: " << level;
    // draw text
    putText(image, oss.str(), Point(0, (int) (info.height * 0.95)), FONT_HERSHEY_SIMPLEX,
            2, Scalar(10, 255, 0), 4, LINE_AA);
    AndroidBitmap_unlockPixels(env, bitmap);
    return 0;
}

JNIEXPORT jint JNICALL
JNI_FUNC(negativeColor)(JNIEnv *env, jclass type, jobject bitmap) {
    AndroidBitmapInfo info;
    // read bitmap
    Mat image;
    OpenCVUtils::lockABitmap2Mat(env, bitmap, info, image);
    LOGD("Load Bitmap [%d x %d] !!", info.width, info.height);
    EffectUtils::negativeColor(image);
    AndroidBitmap_unlockPixels(env, bitmap);
    return 0;
}

JNIEXPORT jint JNICALL
JNI_FUNC(rgba2Gray)(JNIEnv *env, jclass type, jobject bitmap) {
    AndroidBitmapInfo info;
    Mat image;
    OpenCVUtils::lockABitmap2Mat(env, bitmap, info, image);
    LOGD("Load Bitmap [%d x %d] !!", info.width, info.height);
    EffectUtils::rgba2Gray(image);
    AndroidBitmap_unlockPixels(env, bitmap);
    return 0;
}

JNIEXPORT jint JNICALL
JNI_FUNC(cannyImage)(JNIEnv *env, jclass type, jobject bitmap) {
    AndroidBitmapInfo info;
    Mat image;
    OpenCVUtils::lockABitmap2Mat(env, bitmap, info, image);
    LOGD("Load Bitmap [%d x %d] !!", info.width, info.height);
    EffectUtils::cannyImage(image);
    AndroidBitmap_unlockPixels(env, bitmap);
    return 0;
}

JNIEXPORT jint JNICALL
JNI_FUNC(adaptiveThreshold)(JNIEnv *env, jclass type, jobject bitmap) {
    AndroidBitmapInfo info;
    Mat image;
    OpenCVUtils::lockABitmap2Mat(env, bitmap, info, image);
    LOGD("Load Bitmap [%d x %d] !!", info.width, info.height);
    EffectUtils::adaptiveThresholdImage(image);
    AndroidBitmap_unlockPixels(env, bitmap);
    return 0;
}

JNIEXPORT jint JNICALL
JNI_FUNC(claheImage)(JNIEnv *env, jclass type, jobject bitmap) {
    AndroidBitmapInfo info;
    Mat image;
    OpenCVUtils::lockABitmap2Mat(env, bitmap, info, image);
    LOGD("Load Bitmap [%d x %d] !!", info.width, info.height);
    EffectUtils::claheImage(image);
    AndroidBitmap_unlockPixels(env, bitmap);
    return 0;
}

JNIEXPORT jint JNICALL
JNI_FUNC(bilateralFilter)(JNIEnv *env, jclass type, jobject bitmap) {
    AndroidBitmapInfo info;
    Mat image;
    OpenCVUtils::lockABitmap2Mat(env, bitmap, info, image);
    LOGD("Load Bitmap [%d x %d] !!", info.width, info.height);
    EffectUtils::bilateralImage(image);
    AndroidBitmap_unlockPixels(env, bitmap);
    return 0;
}

JNIEXPORT jint JNICALL
JNI_FUNC(blobDetect)(JNIEnv *env, jclass type, jobject bitmap) {
    AndroidBitmapInfo info;
    Mat image;
    OpenCVUtils::lockABitmap2Mat(env, bitmap, info, image);
    LOGD("Load Bitmap [%d x %d] !!", info.width, info.height);
    EffectUtils::blobDetect(image);
    AndroidBitmap_unlockPixels(env, bitmap);
    return 0;
}