#include "SortedSetIterator.h"
#include <exception>

using namespace std;

//best case: theta(log(n)) complexity
//average case: theta(log(n)) complexity
//worst case: theta(n) - when the bst is completely unbalanced, traverses the entire left branch to reach the leaf and also the leftmost node.
//general time complexity: O(n)
SortedSetIterator::SortedSetIterator(const SortedSet& m) : multime(m)
{
    this->currentNode = this->multime.root;
    this->stackHead = nullptr;

    while(this->currentNode != -1){
        pushToStack(this->currentNode);
        currentNode = this->multime.elements[this->currentNode].left;
    }

    if(this->stackHead != nullptr){
        this->currentNode = this->stackHead->elem; //top stack
        popFromStack();
    }
    else{
        this->currentNode = -1;
    }
}

//best case: theta(1) complexity
//average case: theta(1) complexity
//worst case: theta(1) complexity
//general time complexity: theta(1)
void SortedSetIterator::pushToStack(int elem) {  //add node to beginning
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
void SortedSetIterator::popFromStack(){
    if(this->stackHead != nullptr){
        StackNode *deletedNode = this->stackHead;
        this->stackHead = this->stackHead->next;
        delete deletedNode;
    }
}

//best case: theta(log(n)) complexity
//average case: theta(log(n)) complexity
//worst case: theta(n) - when the bst is completely unbalanced, traverses the entire left branch to reach the leaf and also the leftmost node.
//general time complexity: O(n)
void SortedSetIterator:: first() {
    this->currentNode = this->multime.root;
    this->stackHead = nullptr;

    while(this->currentNode != -1){
        pushToStack(this->currentNode);
        currentNode = this->multime.elements[this->currentNode].left;
    }

    if(this->stackHead != nullptr){
        this->currentNode = this->stackHead->elem; //top stack
        popFromStack();
    }
    else{
        this->currentNode = -1;
    }
}



void SortedSetIterator::previous() {
    if (!valid()) {
        throw std::exception();
    }
    if(this->multime.elements[this->currentNode].left != -1){
        this->currentNode = this->multime.elements[this->currentNode].left;

        while(this->currentNode != -1){
            pushToStack(this->currentNode);
            this->currentNode = this->multime.elements[this->currentNode].right;
        }
    }

    if(this->stackHead != nullptr){
        this->currentNode = this->stackHead->elem;
        popFromStack();
    }
    else{
        this->currentNode = -1;
    }

}


//best case: theta(log(n)) complexity
//average case: theta(log(n)) complexity
//worst case: theta(n) - when the bst is completely unbalanced, traverses the entire left branch to reach the leaf and also the leftmost node.
//general time complexity: O(n)
//the next function makes an extra step, that checks if a node has a right child and also visits them
void SortedSetIterator::next() {
	if(!valid()) {
        throw std::exception();
    }

    if(this->multime.elements[this->currentNode].right != -1){
        this->currentNode = this->multime.elements[this->currentNode].right;

        while(this->currentNode != -1){
            pushToStack(this->currentNode);
            this->currentNode = this->multime.elements[this->currentNode].left;
        }
    }

    if(this->stackHead != nullptr){
        this->currentNode = this->stackHead->elem;
        popFromStack();
    }
    else{
        this->currentNode = -1;
    }
}

//best case: theta(1) complexity
//average case: theta(1) complexity
//worst case: theta(1) complexity
//general time complexity: theta(1)
TElem SortedSetIterator::getCurrent()
{
	if(!valid()){
        throw std::exception();
    }
	return this->multime.elements[currentNode].info;
}

//best case: theta(1) complexity
//average case: theta(1) complexity
//worst case: theta(1) complexity
//general time complexity: theta(1)
bool SortedSetIterator::valid() const {
	return this->currentNode != -1;
}

