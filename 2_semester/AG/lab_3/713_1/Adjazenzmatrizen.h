#ifndef INC_713_1_ADJAZENZMATRIZEN_H
#define INC_713_1_ADJAZENZMATRIZEN_H

class Graph2{
private:
    int n;
    int m;
    int matrix[100][100];
public:
    explicit Graph2(const std::string& filename);

    void addEdge(int x, int y);

    void BFS(int x);

    void printGraph();
};




#endif //INC_713_1_ADJAZENZMATRIZEN_H
