#include "Graph.h"
#include "Graph_2.h"

#include<iostream>


using namespace std;

int main(){

    Graph g;
    cout<<g.isEdge(0,1)<<endl;
    g.printGraph();

    Graph2 g2;
    g2.printAdjazenzListe();
//    cout << g2.isAdjazenz(2,1) << endl;
    cout << g2.minimalenGrad() << endl;
    cout << g2.maximalenGrad() << endl;

}