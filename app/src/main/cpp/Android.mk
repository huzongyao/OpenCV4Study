LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
OPENCV_CAMERA_MODULES:=off
OPENCV_INSTALL_MODULES:=on
OPENCV_LIB_TYPE:=STATIC
# Download OpenCV SDK: https://opencv.org/releases/
include $(LOCAL_PATH)/opencv/jni/OpenCV.mk

# include $(CLEAR_VARS)
LOCAL_MODULE := opencv

LOCAL_C_INCLUDES := \
    $(LOCAL_PATH) \
    $(LOCAL_PATH)/opencv/jni/include \

LOCAL_SRC_FILES := \
	$(wildcard $(LOCAL_PATH)/api/*.cpp) \
	$(wildcard $(LOCAL_PATH)/utils/*.cpp) \

LOCAL_CFLAGS += -Wall -ffunction-sections -fdata-sections
LOCAL_CXXFLAGS += -Wall -std=c++11 -frtti -fexceptions -ffunction-sections -fdata-sections
LOCAL_LDFLAGS += -Wl,--gc-sections

LOCAL_LDLIBS := -llog -lz -landroid -ljnigraphics -lm

include $(BUILD_SHARED_LIBRARY)