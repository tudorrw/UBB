cmake_minimum_required(VERSION 3.26)
project(Map_dll)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(Map_dll
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        Map.cpp
        Map.h
        MapIterator.cpp
        MapIterator.h
        ShortTest.cpp
        ShortTest.h)
