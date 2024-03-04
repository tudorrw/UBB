cmake_minimum_required(VERSION 3.24)
project(PriorityQueue_dll)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(PriorityQueue_dll
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        PriorityQueue.cpp
        PriorityQueue.h
        ShortTest.cpp
        ShortTest.h)
