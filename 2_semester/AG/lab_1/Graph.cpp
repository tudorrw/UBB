#include "Graph.h"
#include <fstream>
#include <iostream>

using namespace std;

Graph::Graph(){

    ifstream f;
    f.open("data.in");
    f >> n;
    f >> m;
    int x,y;
    for(int i=0; i<m; i++){
        f>>x>>y;
        addEdge(x,y);
    }

}

void Graph::addEdge(int x, int y){
    matrix[x][y] = 1;
    matrix[y][x] = 1;
}

bool Graph::isEdge(int x, int y){
    return matrix[x][y] == 1;
}

void Graph::printGraph(){
    for (int i=0; i<n; i++){
        for(int j=0; j<n; j++){
            cout<<matrix[i][j]<<" ";
        }
        cout<<endl;
    }
}