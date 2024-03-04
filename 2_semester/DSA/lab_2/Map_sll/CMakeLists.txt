cmake_minimum_required(VERSION 3.24)
project(Map_sll)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(Map_sll
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        Map.cpp
        Map.h
        MapIterator.cpp
        MapIterator.h
        ShortTest.cpp
        ShortTest.h)
