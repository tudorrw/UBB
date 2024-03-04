#ifndef L5_CIOBANU_TUDOR_711_GRAPH_H
#define L5_CIOBANU_TUDOR_711_GRAPH_H

#include <string>
#include <iostream>
#include <fstream>
#include <vector>
#include <utility>
#include <climits>
#include <map>

struct Node{
    std::string city1;
    std::string city2;
    int weight;
    Node *next;

};

class Graph{
private:
    Node *head;
public:
    Graph(const std::string &filename);

    void print_graph();

    std::vector<std::string> listOfCities();

    void shortestPath(std::string knoten1, std::string knoten2);
};

#endif //L5_CIOBANU_TUDOR_711_GRAPH_H
