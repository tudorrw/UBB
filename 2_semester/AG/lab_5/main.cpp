#include "Graph.h"

int main(){
    Graph g1("cities.txt");
    g1.shortestPath("Ravenna", "Toledo");

    Graph g2("cities.txt");
//    g2.shortestPath("Waterloo", "Waco");
//    g1.print_graph();
}