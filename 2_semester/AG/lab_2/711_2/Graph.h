
#ifndef INC_711_2_GRAPH_H
#define INC_711_2_GRAPH_H
#include <vector>
#include <utility>
#include <string>

class Graph{
private:
    int n{};
    int m{};
    std::vector<std::pair<int, int>> kantenliste;
    std::vector<int> adjazenzliste[100];
public:

    std::vector<std::pair<int, int>> get_kantenliste();

    Graph(const std::string& filename);

    Graph();

    Graph(const Graph& other);

    void addEdge(int x, int y);

    bool isEdge(int x, int y);

    void printGraph();

    Graph Durchschnitt(const Graph& other) const;
};


#endif //INC_711_2_GRAPH_H
