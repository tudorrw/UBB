#include <iostream>
#include <fstream>
#include <algorithm>
#include "Graph.h"

Graph::Graph(const std::string& filename) {
    std::ifstream f;
    f.open(filename);
    f >> n >> m;
    int x, y;
    for (int i = 0; i < m; i++) {
        f >> x >> y;
        addEdge(x, y);
    }
}

void Graph::addEdge(int x, int y) {
    adjazenzliste[x].push_back(y);
    adjazenzliste[y].push_back(x);
}

bool Graph::isEdge(int x, int y){
    for(int it : adjazenzliste[x]){
        if(it == y){
            return true;
        }
    }
    return false;
}

void Graph::printGraph() {
    for(int i = 0; i < n; i++){
        std::cout << i << ": ";
        for(int it : adjazenzliste[i]){
            std::cout << it << " ";
        }
        std::cout << std::endl;
    }
}

void Graph::absteigend() {
    std::vector<int> gradfolge;
    for(int i = 0; i < n; i++){
        int grad = 0;
        for(int it: adjazenzliste[i]){
            grad++;
        }
        gradfolge.push_back(grad);
    }
    std::sort(gradfolge.begin(), gradfolge.end(), std::greater<int>());
    for(int elem : gradfolge)
        std::cout << elem << " ";
}

void Graph::kreis(int x, const std::string& filename){
    std::ofstream f;
    f.open(filename);
    for(int it : adjazenzliste[x]){
        if(isEdge(x,it)) {
            int index = it + 1;
            while(index < n){
                if(isEdge(it, index) && isEdge(index, x)){
                    f << x << " " << it << " " << index << "\n";
                }
                index++;
            }
        }
    }
}