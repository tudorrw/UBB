#include <iostream>
#include "Graph.h"

int main(){

    Graph grph("grph.txt");
    grph.printGraph();

    std::cout << std::endl;
    Graph graph1("graph1.in");
    graph1.printGraph();
    std::cout << graph1.get_kantenliste().capacity();

    std::cout << std::endl;
    Graph copyy(grph);
    copyy.printGraph();

    std::cout << std::endl;
    Graph leer;
    leer.printGraph();
    std::cout << leer.get_kantenliste().capacity() << std::endl;

    Graph durchschnittsgraph = graph1.Durchschnitt(grph);
    durchschnittsgraph.printGraph();

    return 0;
}
