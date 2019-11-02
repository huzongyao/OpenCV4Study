//
// Created by huzongyao on 2019/11/1.
//

#include <opencv2/imgproc.hpp>
#include "EffectUtils.h"

void EffectUtils::negativeColor(Mat &image) {
    std::vector<uchar> LUT(256, 0);
    for (int i = 0; i < 256; ++i)
        LUT[i] = (uchar) (255 - i);
    for (int i = 0; i < image.rows; ++i) {
        for (int j = 0; j < image.cols; ++j) {
            image.at<Vec4b>(i, j)[0] = LUT[image.at<Vec4b>(i, j)[0]];
            image.at<Vec4b>(i, j)[1] = LUT[image.at<Vec4b>(i, j)[1]];
            image.at<Vec4b>(i, j)[2] = LUT[image.at<Vec4b>(i, j)[2]];
        }
    }
}

void EffectUtils::rgba2Gray(Mat &image) {
    Mat gray;
    cvtColor(image, gray, COLOR_RGBA2GRAY);
    cvtColor(gray, image, COLOR_GRAY2RGBA);
}

void EffectUtils::cannyImage(Mat &image) {
    Mat gray;
    cvtColor(image, gray, COLOR_RGBA2GRAY);
    GaussianBlur(gray, gray, Size(3, 3), 0, 0, BORDER_DEFAULT);
    Canny(gray, gray, 10, 100, 3, true);
    cvtColor(gray, image, COLOR_GRAY2RGBA);
}

void EffectUtils::adaptiveThresholdImage(Mat &image) {
    Mat gray;
    cvtColor(image, gray, COLOR_RGBA2GRAY);
    adaptiveThreshold(gray, gray, 255, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY, 25, 10);
    cvtColor(gray, image, COLOR_GRAY2RGBA);
}
