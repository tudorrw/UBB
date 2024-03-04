#include "Set.h"
#include "SetITerator.h"

Set::Set() {
	this->head = nullptr;
    this->set_length = 0;
}


bool Set::add(TElem elem) {
	if(search(elem))
	    return false;
    Node *newNode = new Node();
    newNode->value = elem;
    newNode->next = this->head;
    this->head = newNode;
    this->set_length++;
    return true;
}


bool Set::remove(TElem elem) {
	Node *currentNode = this->head, *prevNode = nullptr;
    while(currentNode != nullptr && currentNode->value != elem){
        prevNode = currentNode;
        currentNode = currentNode->next;
    }
    if(currentNode != nullptr){
        if(prevNode == nullptr){
            this->head = this->head->next;
        }
        else{
            prevNode->next = currentNode->next;
        }
        this->set_length--;
        delete currentNode;
        return true;
    }
	return false;
}

bool Set::search(TElem elem) const {
	Node *currentNode = this->head;
    while(currentNode != nullptr && currentNode->value != elem){
        currentNode = currentNode->next;
    }
    if(currentNode != nullptr){
        return true;
    }
	return false;
}


int Set::size() const {
	return this->set_length;
}


bool Set::isEmpty() const {
	return size() == 0;
}


Set::~Set() {
	Node *currentNode = this->head, *next;
    while(currentNode != nullptr){
        next = currentNode->next;
        delete currentNode;
        currentNode = next;
    }
}


SetIterator Set::iterator() const {
	return SetIterator(*this);
}


