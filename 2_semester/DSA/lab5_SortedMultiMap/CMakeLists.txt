cmake_minimum_required(VERSION 3.26)
project(lab5_SortedMultiMap)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(lab5_SortedMultiMap
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        ShortTest.cpp
        ShortTest.h
        SMMIterator.cpp
        SMMIterator.h
        SortedMultiMap.cpp
        SortedMultiMap.h)
