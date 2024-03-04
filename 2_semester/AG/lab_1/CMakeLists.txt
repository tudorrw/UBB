cmake_minimum_required(VERSION 3.24)
project(Labor_1)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(Labor_1
        app.cpp
        Graph.cpp
        Graph.h Graph_2.h Graph_2.cpp)
