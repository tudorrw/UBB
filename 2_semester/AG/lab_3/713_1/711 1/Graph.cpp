#include <iostream>
#include <fstream>
#include "Graph.h"


Graph::Graph(const std::string &filename, int &src, int &dest) {
    std::ifstream f;
    f.open(filename);
    f >> rows >> columns;

    int numOfVertices = 0;
    std::map<std::pair<int, int>, int> vertexMap;

    for(int i = 0; i < rows; ++i){
        for(int j = 0; j < columns; ++j){
            char cell;
            f >> cell;
            if(cell == 'S'){
                src = numOfVertices;
            }
            else if(cell == 'F'){
                dest = numOfVertices;
            }
            else if(cell != 'X'){
                vertexMap[std::make_pair(i, j)] = numOfVertices;
                ++numOfVertices;
            }
        }
    }

    adjList.resize(numOfVertices);


    for(int i = 0; i < rows; ++i){
        for(int j = 0; j < columns; ++j){
            if(vertexMap.count(std::make_pair(i, j))){
                int x = vertexMap[std::make_pair(i, j)];
                if (i > 0 && vertexMap.count(std::make_pair(i - 1, j))) {
                    int y = vertexMap[std::make_pair(i - 1, j)];
                    addEdge(x, y);
                }
                if (i < rows - 1 && vertexMap.count(std::make_pair(i + 1, j))) {
                    int y = vertexMap[std::make_pair(i + 1, j)];
                    addEdge(x, y);
                }
                if (j > 0 && vertexMap.count(std::make_pair(i, j - 1))) {
                    int y = vertexMap[std::make_pair(i, j - 1)];
                    addEdge(x, y);
                }
                if (j < columns - 1 && vertexMap.count(std::make_pair(i, j + 1))) {
                    int y = vertexMap[std::make_pair(i, j + 1)];
                    addEdge(x, y);
                }
            }
        }
    }
}

void Graph::addEdge(int x, int y, int weight){
    adjList[x].push_back(std::make_pair(y, weight));
    adjList[y].push_back(std::make_pair(x, weight));
}

void Graph::solution(int src, int dest) {
    //all the nodes as not visited
    std::vector<int> visited;
    visited.resize(adjList.size(), false);
    visited[src] = true; //mark the starting node as visited
    //a queue to hold the nodes to be visited
    std::queue<int> q;
    q.push(src);
    //a vector that contains the nodes in bfs traversal
    std::vector<int> bfs_array;

    while(!q.empty()){
        int current = q.front();
        q.pop();
        bfs_array.push_back(current);

    }

}


void Graph::printGraph() {
    for(int i = 0; i < adjList.size(); i++){
        std::cout << i << ": ";
        for(auto [j, weight] : adjList[i]){
            std::cout << j << " ";
        }
        std::cout << std::endl;
    }
}

