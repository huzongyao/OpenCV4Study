//
// Created by huzongyao on 2019/11/1.
//

#ifndef OPENCV4STUDY_EFFECTUTILS_H
#define OPENCV4STUDY_EFFECTUTILS_H

#include <opencv2/core.hpp>

using namespace cv;

class EffectUtils {
public:
    static void negativeColor(Mat& image);

    static void rgba2Gray(Mat& image);

    static void cannyImage(Mat& image);

    static void adaptiveThresholdImage(Mat& image);

    static void claheImage(Mat& image);
};


#endif //OPENCV4STUDY_EFFECTUTILS_H
