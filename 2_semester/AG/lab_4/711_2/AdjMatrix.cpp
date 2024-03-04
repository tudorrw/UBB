#include "AdjMatrix.h"
AdjMatrix::AdjMatrix(const std::string &filename) {
    std::ifstream f;
    f.open(filename);
    f >> n;
    int x;
    for(int i = 1; i <= n; i++){
        for(int j = 1; j <= n; j++){
            f >> x;
            matrix[i][j] = x;
        }
    }
    f.close();
}

void AdjMatrix::DFS(int start, std::vector<bool> &visited) {
    std::stack<int> stack;
    stack.push(start);
    while(!stack.empty()){
        int current = stack.top();
        stack.pop();
        if(!visited[current]){
            std::cout << current << " ";
            visited[current] = true;
        }
        for(int i = 1; i <= n; i++){
            if(matrix[current][i] == 1 && !visited[i]){
                stack.push(i);
            }
        }
    }
}

int AdjMatrix::AnzahlKomponente() {
    std::vector<bool> visited(n, false);
    int komponenten = 0;
    for(int i = 1; i <= n; i++){
        if(!visited[i]){
            DFS(i, visited);
            std::cout << std::endl;
            komponenten++;
        }
    }
    return komponenten;
}


void AdjMatrix::printAdjMatrix() {
    for(int i = 1; i <= n; i++){
        for(int j = 1; j <= n; j++){
            std::cout << matrix[i][j] << " ";
        }
        std::cout << std::endl;
    }
}
