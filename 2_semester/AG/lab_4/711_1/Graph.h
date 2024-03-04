#ifndef CIOBANU_TUDOR_711_LAB4_GRAPH_H
#define CIOBANU_TUDOR_711_LAB4_GRAPH_H

#include <vector>
class Graph{
private:
    int n{};
    int m{};
    std::vector<std::vector<int>> adjList;

public:
    Graph(const std::string& filename);

    void addEdge(int x, int y);

    void printGraph();
};



class GewichteterGraph{
private:
    int n{};
    int m{};
    std::vector<std::vector<int>> adjList;
    std::vector<std::vector<int>> gewicht;
public:
    GewichteterGraph(const std::string &filename);

    void addEdge(int x, int y);

    void printGewicht();

    int min_key(std::vector<int> &key, std::vector<bool> &visited);

    void minimalerSpannbaum();
};



#endif //CIOBANU_TUDOR_711_LAB4_GRAPH_H
