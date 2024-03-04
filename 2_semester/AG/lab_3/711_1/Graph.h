#ifndef INC_711_1_GRAPH_H
#define INC_711_1_GRAPH_H
#include <vector>
#include <utility>
#include <map>
#include <string>
#include <queue>
class Graph{
private:
    int rows{};
    int columns{};
    std::vector<std::vector<std::pair<int, int>>> adjList;

public:
    Graph(const std::string &filename, int &src, int &dest);

    void addEdge(int x, int y, int w = 1);

    void solution(int src, int dest);

    void printGraph();
};



#endif //INC_711_1_GRAPH_H
