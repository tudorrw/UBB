#ifndef INC_711_2_ADJLIST_H
#define INC_711_2_ADJLIST_H

#include <vector>
#include <stack>
#include <iostream>
#include <fstream>

class AdjList{
private:
    int n{};
    int m{};
    std::vector<std::vector<int>> adjazenzliste;
public:
    AdjList(const std::string& filename);

    void addEdge(int x, int y);

    void DFS(int start, std::vector<bool> &visited);

    int AnzahlKomponente();

    int allTrees();

    void printAdjList();
};

#endif //INC_711_2_ADJLIST_H
