# OpenCV4Study
To Compile And Study OpenCV on Android Based on OpenCV 4.x

|  Face Detection | Blur | Threshold | Gray |
| ------------ | -------------- | -------------- | -------------- |
|![pic](https://github.com/huzongyao/OpenCV4Study/blob/master/misc/demo1.png?raw=true)|![pic](https://github.com/huzongyao/OpenCV4Study/blob/master/misc/demo2.png?raw=true)|![pic](https://github.com/huzongyao/OpenCV4Study/blob/master/misc/demo3.png?raw=true)|![pic](https://github.com/huzongyao/OpenCV4Study/blob/master/misc/demo4.png?raw=true)|

### Details
#### Instructions
1. In Order to Speed up Repository clone process, OpenCV SDK files are git ignored by this project.
So after first time clone the Repository, Please download the OpenCV Android SDK and
copy folders under sdk/native/ into Project/cpp/opencv/, and then compile could process.
Download Address: https://opencv.org/releases/

#### 使用说明
1. 如果编译出错，需要先确认是否下载了OpenCV官方安卓库，为了避免大量无需修改的SDK的库文件和头文件占用Git仓库，
导致第一次更新太慢，我把OpenCV SDK的库文件排除版本控制，所以第一次克隆项目后，需要下载OpenCV SDK，
并把sdk/native目录下的文件拷贝到项目目录/cpp/opencv/目录下，
再编译即可，否则编译报错，找不到OpenCV.mk文件。下载地址：https://opencv.org/releases/

2. 使用官方的SDK或者他们的动态库，仅仅4个so文件大小就达到100M以上，生产环境使用不太可行。
所以我是用他们的静态库链接我的代码生成so文件，编译文件大小取决于App用到多少功能，一般不会太大，生产环境也可行了。

3. OpenCV 的haar分类器下载：https://github.com/opencv/opencv/tree/master/data/haarcascades

#### 常用操作
1. 引用并使用OpenCV静态库：
``` mk
OPENCV_LIB_TYPE:=STATIC
include $(LOCAL_PATH)/opencv/jni/OpenCV.mk
```

2. 自带人脸识别功能，先下载相关分类器(SDK包里也有)
``` cpp
CascadeClassifier cascade(cascadePath);
cvtColor(image, gray, COLOR_RGBA2GRAY);
cascade.detectMultiScale(gray, faces);
```

### About Me
 * GitHub: [https://huzongyao.github.io/](https://huzongyao.github.io/)
 * ITEye博客：[https://hzy3774.iteye.com/](https://hzy3774.iteye.com/)
 * 新浪微博: [https://weibo.com/hzy3774](https://weibo.com/hzy3774)

### Contact To Me
 * QQ: [377406997](https://wpa.qq.com/msgrd?v=3&uin=377406997&site=qq&menu=yes)
 * Gmail: [hzy3774@gmail.com](mailto:hzy3774@gmail.com)
 * Foxmail: [hzy3774@qq.com](mailto:hzy3774@qq.com)
 * WeChat: hzy3774

 ![image](https://raw.githubusercontent.com/hzy3774/AndroidP7zip/master/misc/wechat.png)

### Others
 * 想捐助我喝杯热水(¥0.01起捐)</br>
 ![donate](https://github.com/huzongyao/JChineseChess/blob/master/misc/donate.png?raw=true)