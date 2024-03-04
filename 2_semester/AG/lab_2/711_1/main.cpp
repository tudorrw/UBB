#include <iostream>
#include "Graph.h"

int main() {


    Graph g1("grph.in");
    g1.printGraph();
    g1.absteigend();
    std::cout << std::endl;
    g1.kreis(4, "grph.out");
    return 0;
}