#ifndef INC_711_2_GRAPH_H
#define INC_711_2_GRAPH_H

#include <vector>

class Graph{

private:
    int n{};
    int m{};
    std::vector<std::vector<int>> adj;

public:
    Graph(const std::string& filename);

    void addEdge(int x, int y);

    void BFS(int s);

    int weg(int start, int stop);

    void printGraph();
};


#endif //INC_711_2_GRAPH_H
