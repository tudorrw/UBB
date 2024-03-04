#include "Graph.h"

Node::Node(int val) : value(val), left(nullptr), right(nullptr) {}

Graph::Graph(const std::string& filename) {
    std::ifstream file;
    file.open(filename);

    file >> n >> m;
    int x, y;
    for(int i = 0; i < m; i++){
        file >> x >> y;
        addEdge(x, y);
    }
}

void Graph::addEdge(int x, int y) {
    edgeList.emplace_back(x, y);
}


void Graph::printGraph() {
    for(int i = 0; i < edgeList.size(); i++){
        std::cout << "[" << edgeList[i].first << ", " << edgeList[i].second << "]" << " ";
    }
    std::cout << std::endl;
}


bool isCycle(int src, int parent, const Graph& g, std::vector<bool>& visited){
    visited[src] = true;
    for(int index = 0; index < g.edgeList.size(); index++){
        int u = g.edgeList[index].first;
        int v = g.edgeList[index].second;
        if(src == v){
            if(!visited[u]){
                if(isCycle(u, src, g, visited)){
                    return true;
                }
            }
            else if(u != parent){
                return true;
            }
        }
        else if(src == u){
            if(!visited[v]){
                if(isCycle(v, src, g, visited)){
                    return true;
                }
            }
            else if(v != parent){
                return true;
            }
        }
    }
    return false;
}

bool isBinaryTree(const Graph& g){
    std::vector<bool> visited(g.n, false);
    if(isCycle(4, -1, g, visited)){
        return false;
    }
    for(int node = 1; node <= g.n; node++){
        if(!visited[node]){
            return false;
        }
    }
    return true;
}

void printInOrder(Node *node){
    if(node != nullptr){
        printInOrder(node->left);
        std::cout << node->value << " ";
        printInOrder(node->right);
    }
}
