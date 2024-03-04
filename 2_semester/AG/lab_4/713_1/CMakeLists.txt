cmake_minimum_required(VERSION 3.24)
project(713_1)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(713_1
        Graph.cpp
        Graph.h main.cpp)
