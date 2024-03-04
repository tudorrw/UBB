#include <iostream>
#include "Kantenliste.h"
#include "Adjazenzmatrizen.h"

int main(){
    Graph g("grph.in");
    g.printGraph();
    std::cout << std::endl;
    g.BFS(1);

    std::cout << std::endl;

    Graph2 g2("grph.in");
    g2.printGraph();
    std::cout << std::endl;
    g2.BFS(1);
    return 0;
}