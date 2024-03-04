#ifndef INC_711_2_ADJMATRIX_H
#define INC_711_2_ADJMATRIX_H

#include <iostream>
#include <fstream>
#include <vector>
#include <stack>

class AdjMatrix{
private:
    int n{};
    int matrix[100][100];
public:
    AdjMatrix(const std::string &filename);

    void DFS(int start, std::vector<bool> &visited);

    int AnzahlKomponente();

    void printAdjMatrix();
};

#endif //INC_711_2_ADJMATRIX_H
