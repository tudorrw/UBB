#include "Bag.h"
#include "BagIterator.h"
#include <exception>
#include <iostream>
using namespace std;


Bag::Bag() {
	this->head = nullptr;
}


void Bag::add(TElem elem) {
    Node* currentElement = this->head;
    while(currentElement != nullptr){
        if(currentElement->value == elem){
            currentElement->freq++;
            return;
        }
        currentElement = currentElement->next;
    }
    currentElement = new Node();
    currentElement->value = elem;
    currentElement->freq = 1;
    currentElement->next = this->head;
    this->head = currentElement;
}


bool Bag::remove(TElem elem) {
    Node *prevNode = nullptr;
	Node *currentNode = this->head;
    while(currentNode != nullptr && currentNode->value != elem){
        prevNode = currentNode;
        currentNode = currentNode->next;
    }
    if(currentNode != nullptr){
        currentNode->freq--;
        if(!currentNode->freq){
            if(prevNode == nullptr){
                this->head = this->head->next;
            }
            else{
                prevNode->next = currentNode->next;
            }
            delete currentNode;
        }
        return true;
    }
	return false; 
}


bool Bag::search(TElem elem) const {
	Node *currentNode = this->head;
    while(currentNode != nullptr){
        if(currentNode->value == elem){
            return true;
        }
        currentNode = currentNode->next;
    }
	return false; 
}

int Bag::nrOccurrences(TElem elem) const {
	Node *currentNode = this->head;
    while(currentNode != nullptr){
        if(currentNode->value == elem){
            return currentNode->freq;
        }
        currentNode = currentNode->next;
    }
	return 0; 
}


int Bag::size() const {
    int bagSize = 0;
	Node *currentNode = this->head;
    while(currentNode != nullptr){
        int currFreq = currentNode->freq;
        while(currFreq > 0){
            bagSize++;
            currFreq--;
        }
        currentNode = currentNode->next;
    }
	return bagSize;
}

bool Bag::isEmpty() const {
	return this->size() == 0;
}

BagIterator Bag::iterator() const {
	return BagIterator(*this);
}


Bag::~Bag() {
	Node *currentNode = this->head;
    while(currentNode != nullptr){
        Node *newNode = currentNode->next;
        delete currentNode;
        currentNode = newNode;

    }
}

