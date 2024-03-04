#include "AdjList.h"

AdjList::AdjList(const std::string& filename){
    std::ifstream f;
    f.open(filename);
    f >> n >> m;
    adjazenzliste.resize(n + 1);
    int x, y;
    for(int i = 0; i < m; i++){
        f >> x >> y;
        addEdge(x, y);
    }
}

void AdjList::addEdge(int x, int y) {
    adjazenzliste[x].push_back(y);
    adjazenzliste[y].push_back(x);
}

void AdjList::DFS(int start, std::vector<bool> &visited){

    std::stack<int> stack;
    stack.push(start);
    while (!stack.empty()){
        int current = stack.top();
        stack.pop();
        if(!visited[current]){
            std::cout << current << " ";
            visited[current] = true;
        }

        for(int it : adjazenzliste[current]){
            if(!visited[it]){
                stack.push(it);
            }
        }
    }
}


int AdjList::AnzahlKomponente(){
    std::vector<bool> visited(n, false);
    int komponenten = 0;
    for(int i = 1; i < adjazenzliste.size(); i++){
        if(!visited[i]){
            komponenten++;
            DFS(i, visited);
            std::cout << std::endl;
        }
    }
    return komponenten;
}

int AdjList::allTrees() {
    std::vector<bool> visited(n, false);
    int count = 0;
    for(int i = 1; i < n; i++){
        if(!visited[i]){
            visited[i] = true;
            bool isTree = true;
            std::stack<int> stack;
            stack.push(i);

            while (!stack.empty()){
                int current = stack.top();
                stack.pop();
                for(int it : adjazenzliste[current]){
                    if(!visited[it]){
                        visited[it] = true;
                        stack.push(it);
                    }
                    else if(it != current){
                        isTree = false;
                    }
                }
            }
            if(isTree){
                count++;
            }
        }
    }
    return count;
}


void AdjList::printAdjList() {
    for(int i = 1; i < adjazenzliste.size(); i++){
        std::cout << i << ": ";
        for(int it : adjazenzliste[i]){
            std::cout << it << " ";
        }
        std::cout << std::endl;
    }
}

