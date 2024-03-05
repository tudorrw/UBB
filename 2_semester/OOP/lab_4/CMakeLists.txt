cmake_minimum_required(VERSION 3.24)
project(lab_4)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(lab_4
        Fruit.cpp
        Fruit.h
        FruitController.cpp
        FruitController.h
        FruitRepo.cpp
        FruitRepo.h
        FruitTest.cpp
        FruitTest.h
        FruitUI.cpp
        FruitUI.h
        Shop.cpp)
