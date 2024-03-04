#include "ListIterator.h"
#include "SortedIndexedList.h"
#include <iostream>
using namespace std;
#include <exception>

SortedIndexedList::SortedIndexedList(Relation r) {
	this->relation = r;
    this->head = nullptr;
    this->tail = nullptr;
    this->elements = 0;
}

int SortedIndexedList::size() const {
	//TODO - Implementation
	return 0;
}

bool SortedIndexedList::isEmpty() const {
	//TODO - Implementation
	return false;
}

TComp SortedIndexedList::getElement(int i) const{
	//TODO - Implementation
	return NULL_TCOMP;
}

TComp SortedIndexedList::remove(int i) {
	//TODO - Implementation
	return NULL_TCOMP;
}

int SortedIndexedList::search(TComp e) const {
	//TODO - Implementation
	return 0;
}

void SortedIndexedList::add(TComp e) {
    DLLNode *newNode = new DLLNode();
    newNode->info = e;
    newNode->next = nullptr;

    if(this->head == nullptr){

    }

    DLLNode *current = this->head;
    while(current != nullptr && current->info != e){
        current = current->next;
    }

}

ListIterator SortedIndexedList::iterator(){
	return ListIterator(*this);
}

//destructor
SortedIndexedList::~SortedIndexedList() {
	//TODO - Implementation
}
