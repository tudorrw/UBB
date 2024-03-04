#include "Graph_2.h"
#include <iostream>
#include <fstream>

Graph2::Graph2(){
    std::ifstream f;
    f.open("grph.txt");
    f >> n;
    f >> m;
    int x, y;
    for(int i=0; i<m; i++){
        f>>x>>y;
        addVektor(x,y);
    }
}

void Graph2::addVektor(int x, int y) {
    adjazenzliste[x].push_back(y);
    adjazenzliste[y].push_back(x);
}

bool Graph2::isAdjazenz(int x, int y) {
    for(int i=0; i<adjazenzliste[x].size(); i++){
        if(y==i)
            return true;
    }
    return false;
}

void Graph2::printAdjazenzListe() const {
    for(int i = 0; i < n; i++){
        std::cout << i << ": ";
//        for(auto it = adjazenzliste[i].begin(); it != adjazenzliste[i].end(); ++it){
//            std::cout << *it << " ";
//        }
        for(int it : adjazenzliste[i]){
            std::cout << it << " ";
        }
        std::cout << std::endl;
    }
}

int Graph2::minimalenGrad(){
    if(adjazenzliste->empty()){
        return 0;
    }
    int minim = INT_MAX;
    for(int i = 0; i < n; i++){
        int counter{0};
        for(int it : adjazenzliste[i]){
            counter++;
        }
        if(minim > counter){
            minim = counter;
        }
    }
    return minim;
}


int Graph2::maximalenGrad() {
    if(adjazenzliste->empty()){
        return 0;
    }
    int maxim = INT_MIN;
    for(int i = 0; i < n; i++){
        int counter{0};
        for(int it : adjazenzliste[i]){
            counter++;
        }
        if(maxim < counter) {
            maxim = counter;
        }
    }
    return maxim;
}

