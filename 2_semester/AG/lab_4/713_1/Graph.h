#ifndef INC_713_1_GRAPH_H
#define INC_713_1_GRAPH_H

#include <vector>
#include <utility>
#include <fstream>
#include <iostream>

struct Node{
    int value;
    Node *left;
    Node *right;
public:
    Node(int val);
};

class Graph{
public:
    int n{}, m{};
    std::vector<std::pair<int, int>> edgeList;

    Graph(const std::string& filename);

    void addEdge(int x, int y);

    void printGraph();
};

bool isCycle(int src, int parent, const Graph& g, std::vector<bool>& visited);

bool isBinaryTree(const Graph& g);

void printInOrder(Node *node);
#endif //INC_713_1_GRAPH_H
