cmake_minimum_required(VERSION 3.24)
project(lab_5_and_6_combined)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(lab_5_and_6_combined
        Controller.cpp
        Controller.h
        CSVRepository.cpp
        CSVRepository.h
        Domain.cpp
        Domain.h
        InMemoryRepository.cpp
        InMemoryRepository.h
        main.cpp
        Repository.h
        UI.cpp
        UI.h
        Unit_Tests.cpp
        Unit_Tests.h)
