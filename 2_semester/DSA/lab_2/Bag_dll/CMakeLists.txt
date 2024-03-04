cmake_minimum_required(VERSION 3.24)
project(Bag_dll)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(Bag_dll
        App.cpp
        Bag.cpp
        Bag.h
        BagIterator.cpp
        BagIterator.h
        ExtendedTest.cpp
        ExtendedTest.h
        ShortTest.cpp
        ShortTest.h)
