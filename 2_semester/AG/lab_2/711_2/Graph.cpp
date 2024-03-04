#include "Graph.h"
#include <fstream>
#include <iostream>
Graph::Graph(const std::string& filename){
    std::ifstream f;
    f.open(filename);
    f >> n >> m;
    int x, y;
    for(int i = 0; i < m; i++){
        f >> x >> y;
        kantenliste.emplace_back(x,y);
    }
    for(auto kante : kantenliste){
        x = kante.first;
        y = kante.second;
        addEdge(x, y);
    }
}
Graph::Graph() {
    n = 0;
    m = 0;
}

Graph::Graph(const Graph& other) {
    n = other.n;
    m = other.m;
    int x, y;
    kantenliste = other.kantenliste;
    for(auto kante : kantenliste){
        x = kante.first;
        y = kante.second;
        addEdge(x, y);
    }
}

std::vector<std::pair<int, int>> Graph::get_kantenliste() {
        return kantenliste;
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

Graph Graph::Durchschnitt(const Graph &other) const {
    Graph durchschnittsgraph = *this;
    int x, y;
    durchschnittsgraph.kantenliste.insert(durchschnittsgraph.kantenliste.end(), other.kantenliste.begin(),other.kantenliste.end());
    for(auto kante : durchschnittsgraph.kantenliste){
        x = kante.first;
        y = kante.second;
        if(!durchschnittsgraph.isEdge(x, y)){
            durchschnittsgraph.addEdge(x, y);
        }
    }
    return durchschnittsgraph;
}


