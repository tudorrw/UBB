#ifndef INC_711_1_GRAPH_H
#define INC_711_1_GRAPH_H
#include <vector>
#include <utility>


class Graph{
private:
    int n{};
    int m{};
    std::vector<int> adjazenzliste[100];

public:

    Graph(const std::string& filename);

    void addEdge(int x, int y);

    bool isEdge(int x, int y);

    void printGraph();

    void absteigend();

    void kreis(int x, const std::string& filename);

};
#endif //INC_711_1_GRAPH_H
