#include <iostream>
#include <fstream>
#include <queue>
#include "Graph.h"


Graph::Graph(const std::string& filename) {
    std::ifstream f;
    f.open(filename);
    f >> n >> m;
    adj.resize(n + 1);
    int x, y;
    for(int i = 0; i < m; i++){
        f >> x >> y;
        addEdge(x, y);
    }
}

void Graph::addEdge(int x, int y) {
    adj[x].push_back(y);
    adj[y].push_back(x);
}

void Graph::BFS(int s) {

    //all the nodes as not visited
    std::vector<int> visited;
    visited.resize(adj.size(), false);
    visited[s] = true; //mark the starting node as visited
    //a queue to hold the nodes to be visited
    std::queue<int> q;
    q.push(s);
    //a vector that contains the nodes in bfs traversal
    std::vector<int> bfs_array;

    while(!q.empty()){
        int current = q.front();
        q.pop();
        bfs_array.push_back(current);
        for(int child: adj[current]){
            if(!visited[child]){
                q.push(child);
                visited[child] = true;
            }
        }
    }
    for(int i = 0; i < bfs_array.size(); i++){
        std::cout << bfs_array[i] << " ";
    }
    std::cout << std::endl;
}

int Graph::weg(int start, int stop) {

    std::vector<bool> visited;
    visited.resize(n, false);
    visited[start] = true;

    std::queue<int> q;
    q.push(start);

    std::vector<int> distance;
    distance.resize(n, 0);

    while(!q.empty()){
        int current = q.front();
        q.pop();
        for(int child : adj[current]){
            if(!visited[child]){
                q.push(child);
                visited[child] = true;
                distance[child] = distance[current] + 1;
                if(child == stop){
                    return distance[child];
                }
            }
        }
    }
    std::cout << "kein Weg" << std::endl;
    return -1;
}


void Graph::printGraph() {
    for(int i = 0; i < adj.size(); i++){
        std::cout << i << ": ";
        for(int it : adj[i]){
            std::cout << it << " ";
        }
        std::cout << std::endl;
    }
}