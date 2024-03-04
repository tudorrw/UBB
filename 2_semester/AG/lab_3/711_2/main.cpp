#include <iostream>
#include "Graph.h"

int main(){
    Graph g1("grph.in");
    g1.printGraph();
    std::cout << std::endl;
    g1.BFS(1);

    std::cout << std::endl;
    std::cout << g1.weg(1, 4);

    Graph g2("10k.txt");
    std::cout << g2.weg(0, 7738) << " " << g2.weg(793, 6174) << std::endl;

    Graph g3("100k.txt");
    std::cout << g3.weg(81768, 55850) << " " << g3.weg(19126, 53548);

    Graph g4("1mil.txt");
//    std::cout << g4.weg(696751, 505396) << " " << g4.weg(0, 895812);

    return 0;
}