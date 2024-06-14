#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <iostream>

//best case: theta(log(n)) complexity
//average case: theta(log(n)) complexity
//worst case: theta(n) - when the bst is completely unbalanced, traverses the entire left branch to reach the leaf and also the leftmost node.
//general time complexity: O(n)
SMMIterator::SMMIterator(const SortedMultiMap& d) : map(d){
    first();
}

//best case: theta(1) complexity
//average case: theta(1) complexity
//worst case: theta(1) complexity
//general time complexity: theta(1)
void SMMIterator::pushToStack(int elem) {
    StackNode *newNode = new StackNode();
    newNode->elem = elem;
    if(this->stackHead == nullptr){   //everytime the newNode will be inserted at the beginning of SLL
        this->stackHead = newNode;
    } else{
        newNode->next = this->stackHead;
        this->stackHead = newNode;
    }
}

//best case: theta(1) complexity
//average case: theta(1) complexity
//worst case: theta(1) complexity
//general time complexity: theta(1)
//remove node from beginning
void SMMIterator::popFromStack() {
    if (this->stackHead != nullptr) {
        StackNode *deletedNode = this->stackHead;
        this->stackHead = this->stackHead->next;
        delete deletedNode;
    }
}
//best case: theta(log(n)) complexity
//average case: theta(log(n)) complexity
//worst case: theta(n) - when the bst is completely unbalanced, traverses the entire left branch to reach the leaf and also the leftmost node.
//general time complexity: O(n)
void SMMIterator::first(){
    this->currentNode = this->map.root;
    this->currentValueIndex = 0;
    this->stackHead = nullptr;

    while (this->currentNode != -1) {
        pushToStack(this->currentNode);
        this->currentNode = this->map.elements[this->currentNode].left;
    }

    if (this->stackHead != nullptr) {
        this->currentNode = this->stackHead->elem;
        this->currentValueIndex = 0;
        popFromStack();
    } else {
        this->currentNode = -1;
    }
}

//best case: theta(1) complexity
//average case: theta(log(n)) complexity
//worst case: theta(n) - when the bst is completely unbalanced, traverses the entire left branch to reach the leaf and also the leftmost node.
//general time complexity: O(n)
void SMMIterator::next(){
    if (!valid()) {
        throw std::exception();
    }
//    cout << "index: "<< this->currentValueIndex << "size: " << this->map.elements[this->currentNode].size << endl;
    // if there are more values at the current node, move to the next value
    if (this->currentValueIndex < this->map.elements[this->currentNode].size - 1) {
        this->currentValueIndex++;
    } else {
        // if there are no more values, move to the next node in the in-order traversal
        if (this->map.elements[this->currentNode].right != -1) {
            this->currentNode = this->map.elements[this->currentNode].right;
            while (this->currentNode != -1) {
                pushToStack(this->currentNode);
                this->currentNode = this->map.elements[this->currentNode].left;
            }
        }

        if (this->stackHead != nullptr) {
            this->currentNode = this->stackHead->elem;
            this->currentValueIndex = 0; // Reset the value index for the new node
            popFromStack();
        } else {
            this->currentNode = -1;
        }
    }
}

//best case: theta(1) complexity
//average case: theta(1) complexity
//worst case: theta(1) complexity
//general time complexity: theta(1)
bool SMMIterator::valid() const{
    return this->currentNode != -1;
}

//best case: theta(1) complexity
//average case: theta(1) complexity
//worst case: theta(1) complexity
//general time complexity: theta(1)
TElem SMMIterator::getCurrent() const{
    if (!valid()) {
        throw std::exception();
    }
    TKey currentKey = this->map.elements[this->currentNode].key;
    int valueIndex = 0; // You might want to implement a way to track the value index if there are multiple values
    TValue currentValue = this->map.elements[this->currentNode].values[this->currentValueIndex];
    return make_pair(currentKey, currentValue);
}


