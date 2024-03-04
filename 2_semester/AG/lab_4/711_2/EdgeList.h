#ifndef INC_711_2_EDGELIST_H
#define INC_711_2_EDGELIST_H

#include <utility>
#include <vector>
#include <string>
#include <stack>

class EdgeList{
private:
    int n{};
    int m{};
    std::vector<std::pair<int, int >> kantenliste;
public:
    EdgeList(const std::string& filename);

    void addEdge(int x, int y);

    int Anzahlkomponente();

    void DFS(int start, std::vector<bool> &visited);

    std::vector<std::pair<int, int>> findKomponenten(int start, std::vector<bool> &visited, int &numberOfVertices);

    int countTrees();

    void printEdgeList();

};

#endif //INC_711_2_EDGELIST_H
