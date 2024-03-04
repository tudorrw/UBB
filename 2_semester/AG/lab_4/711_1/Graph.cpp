#include <iostream>
#include <fstream>
#include "Graph.h"
#include <climits>
#include <queue>
#include <utility>
#define INT_MAX



Graph::Graph(const std::string& filename) {
    std::ifstream f;
    f.open(filename);
    f >> n >> m;
    adjList.resize(n + 1);
    int x, y;
    for(int i = 0; i < m; i++){
        f >> x >> y;
        addEdge(x,y);
    }
}

void Graph::addEdge(int x, int y) {
    adjList[x].push_back(y);
    adjList[y].push_back(x);
}


void Graph::printGraph() {
    for(int i = 0; i < adjList.size(); i++){
        std::cout << i << ": ";
        for(int it : adjList[i]){
            std::cout << it  << " ";
        }
        std::cout << std::endl;
    }
}


GewichteterGraph::GewichteterGraph(const std::string &filename){
    std::ifstream f;
    f.open(filename);
    f >> n >> m;
    adjList.resize(n + 1);
    gewicht.resize(n+1, std::vector<int>(n+1));
    int v1, v2, c;
    for(int i = 0; i < m; i++){
        f >> v1 >> v2 >> c;
        addEdge(v1, v2);
        gewicht[v1][v2] = c;
        gewicht[v2][v1] = c;
    }
}
void GewichteterGraph::addEdge(int x, int y) {
    adjList[x].push_back(y);
    adjList[y].push_back(x);
}

void GewichteterGraph::printGewicht(){
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            std::cout << gewicht[i][j] << " ";
        }
        std::cout << std::endl;
    }
}

int GewichteterGraph::min_key(std::vector<int> &key, std::vector<bool> &visited){
    int min = 1000, minIdx;
    for(int i = 0; i < n; i++){
        if(!visited[i] && key[i] < min){
            min = key[i];
            minIdx = i;
        }
    }
    return minIdx;
}
void GewichteterGraph::minimalerSpannbaum() {
    std::vector<bool> visited(n, false);
    std::vector<int> key(n, 1000);

    for(int i = 0; i < n; i++){
        int u = min_key(key, visited);
        visited[u] = true;
        std::cout << u << " ";
        for(int j = 0; j < n; j++){
            if(gewicht[u][j] && visited[j] == false && gewicht[u][j] < key[j]){
                key[j] = gewicht[u][j];
            }
        }
    }

}












