#include <iostream>
#include <cassert>
#include <exception>
#include "L3_Ciobanu_Tudor_711_DSM.h"
#include "L3_Ciobanu_Tudor_711_DSM.cpp"

void DSM_test(){
    DSM<int> dsm_obj1(4);
    assert(dsm_obj1.size() == 4);
    //set elements
    dsm_obj1.setElementName(0, "GPU");
    dsm_obj1.setElementName(2, "RAM");
    dsm_obj1.setElementName(1, "CPU");
    dsm_obj1.setElementName(3, "Screen");


    assert(dsm_obj1.getName(0) != "RAM");
    try{
        std::string name = dsm_obj1.getName(-1);
        assert(false);
    }
    catch(std::exception &e){
        assert(true);
    }
    std::cout << "set element name test completed" << std::endl;

    //initialise a string array and a dsm
    std::string elementNames[7] = {"CPU", "GPU", "RAM", "ROM", "SSD", "Screen", "Cooling Fan"};
    DSM<int> dsm_obj2(elementNames, 7);
    assert(dsm_obj2.size() == 7);
    //get name
    assert(dsm_obj2.getName(3) == "ROM");
    try{
        std::string name = dsm_obj2.getName(9);
        assert(false);
    }
    catch(std::exception &e){
        assert(true);
    }
    std::cout << "set element name tests completed" << std::endl;
    //set name
    dsm_obj2.setElementName(6, "Battery");
    try{
        dsm_obj2.setElementName(8, "Battery");
        assert(false);
    }
    catch(std::exception &e){
        assert(true);
    }
    assert(dsm_obj2.getName(6) == "Battery");
    std::cout << "get name tests completed" << std::endl;
    //add links
    dsm_obj2.addLink("CPU", "RAM", 1);
    dsm_obj2.addLink("CPU", "ROM", 2);
    dsm_obj2.addLink("CPU", "SSD", 1);
    dsm_obj2.addLink("CPU", "Screen", 2);
    dsm_obj2.addLink("CPU", "Battery", 2);
    dsm_obj2.addLink("GPU", "CPU", 3);
    dsm_obj2.addLink("GPU", "ROM", 1);
    dsm_obj2.addLink("GPU", "Screen", 3);
    dsm_obj2.addLink("GPU", "Battery", 1);
    dsm_obj2.addLink("RAM", "CPU", 5);
    dsm_obj2.addLink("RAM", "ROM", 1);
    dsm_obj2.addLink("RAM", "SSD", 1);
    dsm_obj2.addLink("RAM", "Battery", 2);
    //test expections for addlink method
    try{
        dsm_obj2.addLink("CPU", "CPU", 4);
        assert(false);
    }
    catch(std::exception &e){
        assert(true);
    }

    try{
        dsm_obj2.addLink("Screen", "GPU", 10);
        assert(false);
    }
    catch(std::exception &e){
        assert(true);
    }
    std::cout << "add links tests completed" << std::endl;

    //add link and resize matrix
    dsm_obj2.addLink("CPU", "Cooler", 3);
    std::cout << "add links and resize matrix completed" << std::endl;

//    delete links
    dsm_obj2.deleteLink("RAM", "ROM");

    try{
        dsm_obj2.deleteLink("Battery", "Charger");
        assert(false);
    }
    catch(std::exception &e){
        assert(true);
    }
    std::cout << "delete links tests completed" << std::endl;
    //has links
    assert(dsm_obj2.hasLink("RAM", "ROM") == false);
    assert(dsm_obj2.hasLink("GPU", "Battery") == true);
    assert(dsm_obj2.hasLink("RAM", "Screen") == false);
    try{
        dsm_obj2.hasLink("HDD", "SSD");
        assert(false);
    }
    catch(std::exception &e){
        assert(true);
    }
    std::cout << "has link tests completed" << std::endl;

    //copy constructor
    DSM<int> copyDSM(dsm_obj2);
    //linkWeight
    assert(copyDSM.linkWeight("RAM", "CPU") == 5);
    try{
        copyDSM.linkWeight("Battery", "Charger");
        assert(false);
    }
    catch(std::exception &e){
        assert(true);
    }
    std::cout << "link weight tests completed" << std::endl;
    //count to links
    assert(copyDSM.countToLinks("RAM") == 3);
    assert(copyDSM.countToLinks("Cooler") == 0);

    try{
        copyDSM.countToLinks("Hinges");
        assert(false);
    }
    catch(std::exception &e){
        assert(true);
    }
    std::cout << "copy to links tests completed" << std::endl;
    //count from links
    assert(copyDSM.countFromLinks("Cooler") == 1);
    assert(copyDSM.countFromLinks("SSD") == 2);
    try{
        copyDSM.countFromLinks("USB port");
        assert(false);
    }
    catch(std::exception &e){
        assert(true);
    }
    std::cout << "copy from links tests completed" << std::endl;

    assert(copyDSM.countAllLinks() == 13);
    std::cout << "copy all links test completed" << std::endl;
}


void DSM_print(){

    std::string elementNames[6] = {"CPU", "GPU", "RAM", "ROM", "SSD", "Screen"};
    DSM<int> laptopDsmStructure(elementNames, 6);
    std::cout << "matrix with all empty links" <<std::endl;
    laptopDsmStructure.print();
    laptopDsmStructure.addLink("CPU", "RAM", 1);
    laptopDsmStructure.addLink("CPU", "ROM", 2);
    laptopDsmStructure.addLink("CPU", "SSD", 1);
    laptopDsmStructure.addLink("CPU", "Screen", 2);
    laptopDsmStructure.addLink("GPU", "CPU", 3);
    laptopDsmStructure.addLink("GPU", "ROM", 1);
    laptopDsmStructure.addLink("GPU", "Screen", 4);
    laptopDsmStructure.addLink("RAM", "CPU", 5);
    laptopDsmStructure.addLink("RAM", "ROM", 1);
    laptopDsmStructure.addLink("RAM", "SSD", 1);
    laptopDsmStructure.addLink("ROM", "GPU", 2);
    laptopDsmStructure.addLink("ROM", "RAM", 2);
    laptopDsmStructure.addLink("ROM", "SSD", 1);
    laptopDsmStructure.addLink("SSD", "ROM", 1);
    laptopDsmStructure.addLink("Screen", "GPU", 5);
    std::cout << "matrix with links added"<<std::endl;
    laptopDsmStructure.print();

    laptopDsmStructure.addLink("CPU", "Cooler", 3);
    laptopDsmStructure.addLink("GPU", "Cooler", 3);
    laptopDsmStructure.addLink("Cooler", "CPU", 5);
    laptopDsmStructure.addLink("Cooler", "GPU", 5);
    laptopDsmStructure.addLink("Cooler", "SSD", 5);
    std::cout << "resized matrix" <<std::endl;
    laptopDsmStructure.print();

    laptopDsmStructure.addLink("CPU", "Battery", 2);
    laptopDsmStructure.addLink("GPU", "Battery", 1);
    laptopDsmStructure.addLink("RAM", "Battery", 2);
    laptopDsmStructure.addLink("ROM", "Battery", 1);
    laptopDsmStructure.addLink("SSD", "Battery", 1);
    laptopDsmStructure.addLink("Screen", "Battery", 2);
    std::cout << "even more resized matrix and also the the final laptop DSM" << std::endl;
    laptopDsmStructure.print();

}


std::string menu(){
    return "1. Die Laptop DSM wird an den Bildschirm angezeigt\n"
           "2. Die Testmeldungen\n"
           "3. Exit\n";
}

int main(){
    std::cout << menu();
    bool quit = false;
    while (!quit){
        int option;
        std::cout << "Wahlen Sie eine Option: ";
        std::cin >> option;
        switch(option){

            case 1:
                DSM_print();
                break;

            case 2:
                DSM_test();
                std::cout << "ALL TESTS DONE!" << std::endl;
                break;

            case 3:
                std::cout << "Das Program wird beendet";
                quit = true;
                break;

            default:
                std::cout << "Invalid Option! " << std::endl;
                break;
        }


    }
    return 0;
}