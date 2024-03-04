#ifndef INC_711_1_GRAPH_H
#define INC_711_1_GRAPH_H
#include <string>
#include <vector>
class Graph{
private:
    int n{};
    int m{};
    std::vector<std::vector<int>> adjList;
public:
    Graph(const std::string& filename);

    void addEdge(int x, int y);

    void coloringAlgorithm(int start);

    void printGraph();
};
#endif //INC_711_1_GRAPH_H
