# JNI
## 参考
- [linux/Ubuntu 下使用 java 调用 so 动态链接库详细步骤](http://blog.csdn.net/hongquan1991/article/details/12426615)
- [ 用 javah 导出类的头文件， 常见的错误及正确的使用方法](http://blog.csdn.net/hejinjing_tom_com/article/details/8125648)

## 总结
- System.load
	- 绝对路径 System.load('/home/...main.so'); 需要后缀
	- 文件名 System.loadLibrary("main"); 不需要后缀，so 位于 Java 的 lib 加载路径中，即 LD_LIBRARY_PATH 的路径。
- 编译 Java 类，生成 .class 文件
	- javac xxx.java
- 使用 class 文件生成 .h 头文件
	- 在 src 目录
	- javah com.xxx.xxx.hello
- 编写 hello.c 文件
- 生成 .o 
- 生成 .so
	- gcc hello.o -o main.so -shared

