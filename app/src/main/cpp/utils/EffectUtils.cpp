//
// Created by huzongyao on 2019/11/1.
//

#include <opencv2/imgproc.hpp>
#include <api/OpenCVApi.h>
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

void EffectUtils::claheImage(Mat &image) {
    Mat gray;
    cvtColor(image, gray, COLOR_RGBA2GRAY);
    auto clahe = createCLAHE(10);
    clahe->apply(gray, gray);
    cvtColor(gray, image, COLOR_GRAY2RGBA);
}

void EffectUtils::bilateralImage(Mat &image) {
    Mat gray, output;
    cvtColor(image, gray, COLOR_RGBA2RGB);
    bilateralFilter(gray, output, 7, 50, 50);
    cvtColor(output, image, COLOR_RGB2RGBA);
}

void EffectUtils::blobDetect(Mat &image) {
    Mat gray;
    cvtColor(image, gray, COLOR_RGBA2GRAY);
    // Setup SimpleBlobDetector parameters.
    SimpleBlobDetector::Params params;
    // Change thresholds
    params.minThreshold = 10;
    params.maxThreshold = 200;
    // Filter by Area.
    params.filterByArea = true;
    params.minArea = 1500;
    // Filter by Circularity
    params.filterByCircularity = true;
    params.minCircularity = 0.1;
    // Filter by Convexity
    params.filterByConvexity = true;
    params.minConvexity = 0.87;
    // Filter by Inertia
    params.filterByInertia = true;
    params.minInertiaRatio = 0.01;
    // Storage for blobs
    std::vector<KeyPoint> keypoints;
    // Set up detector with params
    Ptr<SimpleBlobDetector> detector = SimpleBlobDetector::create(params);
    // Detect blobs
    detector->detect(gray, keypoints);
    LOGE("Count: %d", keypoints.size());
    drawKeypoints(image, keypoints, image, Scalar::all(-1), DrawMatchesFlags::DRAW_RICH_KEYPOINTS);
}
