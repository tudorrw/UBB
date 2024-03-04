
#ifndef LABOR_1_GRAPH_2_H
#define LABOR_1_GRAPH_2_H
#include <vector>

class Graph2 {
private:
    int n{};
    int m{};
    std::vector<int> adjazenzliste[100];
public:
    Graph2();

    void addVektor(int x, int y);

    bool isAdjazenz(int x, int y);

    void printAdjazenzListe() const;

    int minimalenGrad();

    int maximalenGrad();



};



#endif
