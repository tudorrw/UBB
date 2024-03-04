
#include <iostream>
#include "Graph.h"
int main(){
    int src, dest;
    Graph g1("labyrinth2.txt", src, dest);
    std::cout << src << " " << dest << std::endl;
    g1.printGraph();
}