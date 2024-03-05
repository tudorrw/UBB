cmake_minimum_required(VERSION 3.24)
project(MultiMapLab3Final)

set(CMAKE_CXX_STANDARD 14)

include_directories(MultiMapLab3Final)

add_executable(MultiMapLab3Final
        MultiMapLab3Final/App.cpp
        MultiMapLab3Final/ExtendedTest.cpp
        MultiMapLab3Final/ExtendedTest.h
        MultiMapLab3Final/MultiMap.cpp
        MultiMapLab3Final/MultiMap.h
        MultiMapLab3Final/MultiMapIterator.cpp
        MultiMapLab3Final/MultiMapIterator.h
        MultiMapLab3Final/ShortTest.cpp
        MultiMapLab3Final/ShortTest.h)
