#include "EdgeList.h"
#include "AdjList.h"
#include "AdjMatrix.h"
#include <iostream>

int main(){
    EdgeList kntl("liste1.txt");
    kntl.printEdgeList();
    std::cout << kntl.Anzahlkomponente() << std::endl;
    std::cout << "*************************************************" << std::endl;
    AdjList adjList("liste1.txt");
    adjList.printAdjList();
    std::cout << adjList.AnzahlKomponente() << std::endl;
    std::cout << adjList.allTrees() << std::endl;
    std::cout << "*************************************************" << std::endl;
    AdjList adjList2("liste2.txt");
    adjList2.printAdjList();
    std::cout << adjList2.AnzahlKomponente() << std::endl;
    std::cout << adjList2.allTrees() << std::endl;

    std::cout << "*************************************************" << std::endl;
    AdjMatrix adjmatrix("matrix1.txt");
    adjmatrix.printAdjMatrix();
    std::cout << adjmatrix.AnzahlKomponente() << std::endl;
    std::cout << "*************************************************" << std::endl;
    AdjMatrix adjmatrix2("matrix2.txt");
    adjmatrix2.printAdjMatrix();
    std::cout << adjmatrix2.AnzahlKomponente() << std::endl;
    return 0;
}