#include "Graph.h"

int main(){
    Graph g1("liste1.txt");
    g1.printGraph();
    std::cout << isBinaryTree(g1) << std::endl;


    Node *root = new Node(4);
    root->left = new Node(2);
    root->right = new Node(6);
    root->left->right = new Node(3);
    root->left->left = new Node(1);
    root->right->left = new Node(5);
    printInOrder(root);

}
