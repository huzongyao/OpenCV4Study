//
// Created by Administrator on 2019/9/6.
//

#ifndef OPENCVSTUDY_OPENCVAPI_H
#define OPENCVSTUDY_OPENCVAPI_H

#include <jni.h>

#define JNI_FUNC(x) Java_com_hzy_cv_demo_ndk_OpenCVApi_##x

#ifdef __cplusplus
extern "C" {
#endif

#ifdef NDEBUG
#define LOGD(...) do{}while(0)
#define LOGI(...) do{}while(0)
#define LOGW(...) do{}while(0)
#define LOGE(...) do{}while(0)
#define LOGF(...) do{}while(0)
#else
#define LOG_TAG "NATIVE.LOG"

#include <android/log.h>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,LOG_TAG,__VA_ARGS__)
#endif

JNIEXPORT jstring JNICALL
JNI_FUNC(getVersionString)(JNIEnv *env, jclass type);

JNIEXPORT jintArray JNICALL
JNI_FUNC(detectFaceFromBitmap)(JNIEnv *env, jclass type, jstring cascadePath_, jobject bitmap);

JNIEXPORT jint JNICALL
JNI_FUNC(blurBitmap)(JNIEnv *env, jclass type, jobject bitmap, jint level);

JNIEXPORT jint JNICALL
JNI_FUNC(negativeColor)(JNIEnv *env, jclass type, jobject bitmap);

JNIEXPORT jint JNICALL
JNI_FUNC(rgba2Gray)(JNIEnv *env, jclass type, jobject bitmap);

#ifdef __cplusplus
}
#endif

#endif //OPENCVSTUDY_OPENCVAPI_H