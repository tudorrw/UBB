cmake_minimum_required(VERSION 3.24)
project(711_2)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(711_2
        EdgeList.cpp
        EdgeList.h main.cpp AdjList.cpp AdjMatrix.cpp)
