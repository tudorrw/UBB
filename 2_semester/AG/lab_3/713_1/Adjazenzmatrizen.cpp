
#include <iostream>
#include <fstream>
#include <queue>
#include "Adjazenzmatrizen.h"

Graph2::Graph2(const std::string& filename) {
    std::ifstream f;
    f.open(filename);
    f >> n >> m;
    int x, y;
    for(int i = 0; i < m; i++){
        f >> x >> y;
        addEdge(x, y);
    }
}

void Graph2::BFS(int x){

    std::vector<bool> visited;
    visited.resize(n, false);
    visited[x] = true;

    std::queue<int> q;
    q.push(x);

    std::vector<int> bfs_array;
    bfs_array.push_back(x);

    while(!q.empty()){
        int current = q.front();
        q.pop();
        for(int i = 0; i < n; i++){
            if(matrix[current][i] == 1 && !visited[i]){
                q.push(i);
                visited[i] = true;
                bfs_array.push_back(i);
            }
        }
    }
    for(int i : bfs_array){
        std::cout << i << " ";
    }
}

void Graph2::addEdge(int x, int y) {
    matrix[x][y] = 1;
    matrix[y][x] = 1;
}

void Graph2::printGraph(){
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            std::cout << matrix[i][j] << " ";
        }
        std::cout << std::endl;
    }
}
