#include "Map.h"
#include "MapIterator.h"

#include <iostream>
Map::Map() {
	this->head = nullptr;
    this->map_size = 0;
}

Map::~Map() {
	Node *currentNode = this->head, *next;
    while(currentNode != nullptr){
        next = currentNode->next;
        delete currentNode;
        currentNode = next;
    }
}

TValue Map::add(TKey c, TValue v){
	Node *newNode = new Node();
    newNode->key = c;
    newNode->value = v;
    if(isEmpty()){
        this->head = newNode;
    }
    else{
        Node *currentNode = this->head;
        while(currentNode != nullptr && currentNode->key != c){
            currentNode = currentNode->next;
        }
        if(currentNode != nullptr){
            int oldVal = currentNode->value;
            currentNode->value = v;
            return oldVal;
        }
        else{
            newNode->next = this->head;
            this->head = newNode;
        }
    }
    this->map_size++;
    return NULL_TVALUE;
}

TValue Map::search(TKey c) const{
    if(!isEmpty()){
        Node *currentNode = this->head;
        while(currentNode != nullptr && currentNode->key != c){
            currentNode = currentNode->next;
        }
        if(currentNode != nullptr){
            return currentNode->value;
        }
    }
    return NULL_TVALUE;
}

TValue Map::remove(TKey c){
	if(!isEmpty()){
        Node *prevNode = nullptr;
        Node *currentNode = this->head;

        while(currentNode != nullptr && currentNode->key != c){
            prevNode = currentNode;
            currentNode = currentNode->next;
        }

        if(currentNode != nullptr){
            int val = currentNode->value;
            if(prevNode == nullptr){
                this->head = this->head->next;
            }
            else{
                prevNode->next = currentNode->next;
            }
            delete currentNode;
            this->map_size--;
            return val;
        }
    }
	return NULL_TVALUE;
}


int Map::size() const {
	return this->map_size;

}

bool Map::isEmpty() const{
    return this->map_size == 0;
}

MapIterator Map::iterator() const {
	return MapIterator(*this);
}



