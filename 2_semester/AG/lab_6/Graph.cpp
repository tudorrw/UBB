#include <iostream>
#include <fstream>
#include <stack>
#include <unordered_map>
#include "Graph.h"

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

void Graph::coloringAlgorithm(int start){
    std::unordered_map<int, int> colors;
    std::vector<bool> visited;
    visited.resize(adjList.size(), false);

    std::stack<int> stack;
    stack.push(start);
    int current;

    while(!stack.empty()){
        current = stack.top();
        stack.pop();
        if(!visited[current]){
//            std::cout << current << " ";
            visited[current] = true;
            colors.insert(std::pair<int, int>(current, 0));
        }
        for(int it : adjList[current]){
            if(!visited[it]){
                stack.push(it);
            }
        }
    }
    int numColors = 1;
    colors[current] = 1; //first color
//    for(auto it : colors){
//        if(it.first != current){
//            int minColor = numColors + 1;
//            for (int neighbor : adjList[it.first]) {
//                if (visited[neighbor] && colors[neighbor] < minColor) {
//                    colors[neighbor] = minColor;
//                }
//            }
//            colors[it.second] = minColor;
//            if (minColor > numColors) {
//                numColors = minColor;
//            }
//        }
//    }


    for(int i = 1; i < adjList.size(); i++){
        if(visited[i]){
            bool hasSameColor = false;
            for(int neighbor : adjList[i]){
                if(colors[neighbor] == colors[i]){
                    hasSameColor = true;
                }
            }
            if(!hasSameColor){
                colors[i] = numColors;
            }
            else{
                numColors++;
                colors[i]=numColors;
            }
        }
    }
    std::cout << numColors << "\n";
    for(auto it : colors){
        std::cout << it.first  << " " << it.second << "\n";
    }
}

void Graph::printGraph() {
    for(int i = 1; i < adjList.size(); i++){
        std::cout << i << ": ";
        for(int it : adjList[i]){
            std::cout << it  << " ";
        }
        std::cout << std::endl;
    }
}

