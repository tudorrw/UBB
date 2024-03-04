#include "Bag.h"
#include "BagIterator.h"
#include <exception>
#include <iostream>
using namespace std;


Bag::Bag() {
	this->head = nullptr;
    this->tail = nullptr;
}


void Bag::add(TElem elem) {
    // Wenn das Element bereits in der Liste vorhanden ist, erhöhen wir nur die Häufigkeit
    Node *currentNode = this->head;
    while(currentNode != nullptr){
        if(currentNode->value == elem){
            currentNode->freq++;
            return;
        }
        currentNode = currentNode->next;
    }
    // Erstelle einen neuen Knoten, wenn das Element noch nicht in der Liste vorhanden ist
    Node *newNode = new Node();
    newNode->value = elem;
    newNode->freq = 1;
    // Füge den Knoten am Ende der Liste ein
    newNode->prev = this->tail;
    newNode->next = nullptr;
    if(this->tail != nullptr){
        this->tail->next = newNode;
    }
    this->tail = newNode;
    // Wenn die Liste leer war, setze den Kopf auf den neuen Knoten
    if(this->head == nullptr){
        this->head = newNode;
    }
}


bool Bag::remove(TElem elem) {
    // Suche den Knoten mit dem gegebenen Element
	Node *currentNode = this->head;
    while(currentNode != nullptr){
        if(currentNode->value == elem){
            break;
        }
        currentNode = currentNode->next;
    }
    // Wenn das Element nicht in der Liste vorhanden ist, gib false zurück
    if(currentNode == nullptr) {
        return false;
    }

    currentNode->freq--;
    if(!currentNode->freq){
        if(currentNode->prev != nullptr){
            currentNode->prev->next = currentNode->next;
        }
        else{
            this->head = currentNode->next;
        }
        if(currentNode->next != nullptr){
            currentNode->prev->next = currentNode->prev;
        }
        else{
            this->tail = currentNode->prev;
        }
        delete currentNode;
    }
    return true;
}


bool Bag::search(TElem elem) const {
	Node *currentNode = this->head;
    while(currentNode != nullptr){
        if(currentNode->value == elem){
            return true;
        }
        currentNode = currentNode ->next;
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
        int frq = currentNode->freq;
        while(frq > 0){
            frq--;
            bagSize++;
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
	Node *currentNode = this->head, *prev;
    while(currentNode != nullptr){
        prev = currentNode->prev;
        delete currentNode;
        currentNode = prev;
    }

}

