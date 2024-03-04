#include "EdgeList.h"
#include <iostream>
#include <fstream>

EdgeList::EdgeList(const std::string &filename) {
    std::ifstream f;
    f.open(filename);
    f >> n >> m;
    int x, y;
    for(int i = 0; i < m; i++){
        f >> x >> y;
        addEdge(x, y);
    }
}

void EdgeList::addEdge(int x, int y) {
    kantenliste.emplace_back(x, y);
}

void EdgeList::DFS(int start, std::vector<bool> &visited){
    std::stack<int> stack;
    stack.push(start);

    while(!stack.empty()){
        int current = stack.top();
        stack.pop();
        if(!visited[current]){
            std::cout << current << " ";
            visited[current] = true;
        }
        for(int i = 0; i < kantenliste.size(); i++){
            int u = kantenliste[i].first;
            int v = kantenliste[i].second;
            if(u == current && !visited[v]){
                stack.push(v);
            }
            if(!visited[u] && v == current){
                stack.push(u);
            }
        }
    }
}

int EdgeList::Anzahlkomponente() {
    std::vector<bool> visited(n, false);
    int komponenten = 0;
    for(int i = 1; i <= n; i++){
        if(!visited[i]){
            komponenten += 1;
            DFS(i, visited);
            std::cout << std::endl;
        }
    }
    return komponenten;
}


std::vector<std::pair<int, int>> EdgeList::findKomponenten(int start, std::vector<bool> &visited, int &numberOfVertices){
    std::stack<int> stack;
    stack.push(start);
    std::vector<std::pair<int,int>> edgeL;
    while(!stack.empty()){
        int current = stack.top();
        stack.pop();
        if(!visited[current]){
            numberOfVertices++;
            visited[current] = true;
        }
        for(int i = 0; i < kantenliste.size(); i++){
            int u = kantenliste[i].first;
            int v = kantenliste[i].second;
            if(u == current && !visited[v]){
                edgeL.emplace_back(u, v);
                stack.push(v);
            }
            if(!visited[u] && v == current){
                edgeL.emplace_back(u, v);
                stack.push(u);
            }
        }
    }
    return edgeL;
}


void EdgeList::printEdgeList() {
    for(int i = 0; i < kantenliste.size(); i++){
        std::cout << "[" << kantenliste[i].first << ", " << kantenliste[i].second << "]" << " ";
    }
    std::cout << std::endl;
}


