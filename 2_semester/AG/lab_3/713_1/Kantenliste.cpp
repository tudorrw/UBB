#include <iostream>
#include <fstream>
#include <queue>
#include "Kantenliste.h"

Graph::Graph(const std::string &filename){
    std::ifstream f;
    f.open(filename);
    f >> n >> m;
    int x, y;
    for(int i = 0; i < m; i++){
        f >> x >> y;
        addEdge(x, y);
    }
}

void Graph::addEdge(int x, int y) {
    edge_list.emplace_back(x, y);
}


void Graph::BFS(int x){
    std::vector<bool> visited;
    visited.resize(edge_list.size(), false);
    visited[x] = true;

    std::queue<int> q;
    q.push(x);

    std::vector<int> bfs_array;
    bfs_array.push_back(x);

    while(!q.empty()){
        int current = q.front();
        q.pop();
        for(int i = 0; i < edge_list.size(); i++){
            int u = edge_list[i].first;
            int v = edge_list[i].second;
            if(u == current && !visited[v]){
                q.push(v);
                visited[v] = true;
                bfs_array.push_back(v);
            }
            else if(v == current && !visited[u]){
                q.push(u);
                visited[u] = true;
                bfs_array.push_back(u);
            }
        }
    }
    for(int i = 0; i < bfs_array.size(); i++){
        std::cout << bfs_array[i] << " ";
    }
}

void Graph::printGraph() {
    for(int i = 0; i < edge_list.size(); i++){
        std::cout << "[" << edge_list[i].first << ", " << edge_list[i].second << "] ";
    }
}