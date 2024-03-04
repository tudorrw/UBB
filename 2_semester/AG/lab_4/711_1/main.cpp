
#include <iostream>
#include "Graph.h"

int main(){

    GewichteterGraph adjList("grph.txt");
    adjList.printGewicht();
    std::cout << std::endl;
    adjList.minimalerSpannbaum();

    std::cout << std::endl;


    return 0;
}
