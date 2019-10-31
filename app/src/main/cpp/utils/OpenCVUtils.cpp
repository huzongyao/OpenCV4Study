//
// Created by huzongyao on 2019/10/31.
//

#include <api/OpenCVApi.h>
#include "OpenCVUtils.h"

void *OpenCVUtils::lockABitmap(JNIEnv *env, jobject bitmap, AndroidBitmapInfo &info) {
    assert(AndroidBitmap_getInfo(env, bitmap, &info) >= 0);
    void *pixels = nullptr;
    assert(AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0);
    return pixels;
}

void OpenCVUtils::lockABitmap2Mat(JNIEnv *env, jobject bitmap, AndroidBitmapInfo &info, Mat &mat) {
    if (AndroidBitmap_getInfo(env, bitmap, &info) < 0) {
        LOGE("AndroidBitmap_getInfo Error!");
        return;
    }
    if (info.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
        LOGE("Bitmap Should Be RGBA8888!");
        return;
    }
    void *pixels = nullptr;
    if (AndroidBitmap_lockPixels(env, bitmap, &pixels) < 0) {
        LOGE("AndroidBitmap_lockPixels Error!");
        return;
    }
    mat = Mat(info.height, info.width, CV_8UC4, pixels);
}

jintArray OpenCVUtils::rectVector2AIntArray(JNIEnv *env, const std::vector<Rect> &rects) {
    const size_t count = rects.size();
    jintArray ret = env->NewIntArray(count * 4);
    auto buffer = new int[count * 4];
    int index = 0;
    for (const auto &r : rects) {
        buffer[index++] = r.x;
        buffer[index++] = r.y;
        buffer[index++] = r.x + r.width;
        buffer[index++] = r.y + r.height;
    }
    env->SetIntArrayRegion(ret, 0, count * 4, buffer);
    delete[] buffer;
    return ret;
}
