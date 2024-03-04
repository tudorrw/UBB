cmake_minimum_required(VERSION 3.24)
project(SortedIndexedList_dll)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(SortedIndexedList_dll
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        ListIterator.cpp
        ListIterator.h
        ShortTest.cpp
        ShortTest.h
        SortedIndexedList.cpp
        SortedIndexedList.h)
