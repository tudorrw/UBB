#include "Graph.h"
#include <iostream>
int main(){

    Graph g2("grph.txt");
    g2.printGraph();
    std::cout << std::endl;
    g2.coloringAlgorithm(1);
//    Graph g3("brebretrs");
//    g2.coloringAlgorithm(1);
    return 0;
}