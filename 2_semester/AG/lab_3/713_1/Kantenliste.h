#ifndef INC_713_1_edge_list_H
#define INC_713_1_edge_list_H

#include <utility>
#include <vector>

class Graph{
private:
    int n{};
    int m{};
    std::vector<std::pair<int, int>> edge_list;

public:
    Graph(const std::string& filename);

    void addEdge(int x, int y);

    void BFS(int x);

    void printGraph();

};

#endif //INC_713_1_edge_list_H
