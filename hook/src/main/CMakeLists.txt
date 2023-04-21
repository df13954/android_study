# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.18.1)

# Declares and names the project.

project("hook")

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI})

ADD_SUBDIRECTORY(${PROJECT_SOURCE_DIR}/cpp/hello)
ADD_SUBDIRECTORY(${PROJECT_SOURCE_DIR}/cpp/other)
ADD_SUBDIRECTORY(${PROJECT_SOURCE_DIR}/cpp/native_hook)