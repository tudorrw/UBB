#include "Set.h"
#include "SetITerator.h"

Set::Set() {
	this->slla_capacity = 3;
    this->slla_size = 0;
    this->elements = new TElem[this->slla_capacity];
    this->next = new int[this->slla_capacity];
    this->head = -1;
    this->firstEmpty = 0;
    for(int i = 0; i < this->slla_capacity - 1; i++){
        this->next[i] = i + 1;
    }
    this->next[this->slla_capacity - 1] = -1;
}

void Set::resizeUp() {
    this->slla_capacity *= 2;
    TElem *newElems = new TElem[this->slla_capacity];
    int *newNext = new int[this->slla_capacity];
    for(int i = 0; i < this->slla_size; i++){
        newElems[i] = this->elements[i];
        newNext[i] = this->next[i];
    }
    for(int i = this->slla_capacity / 2; i < this->slla_capacity - 1; i++){
        newNext[i] = i + 1;
    }
    newNext[this->slla_capacity - 1] = -1;

    delete[] this->elements;
    delete[] this->next;
    this->elements = newElems;
    this->next = newNext;
    this->firstEmpty = this->slla_capacity / 2;

}
bool Set::add(TElem elem) {
	if(search(elem)){
        return false;
    }
    if(this->firstEmpty == -1){
        resizeUp();
    }
    int newPos = this->firstEmpty;
    this->firstEmpty = this->next[this->firstEmpty];
    this->elements[newPos] = elem;
    this->next[newPos] = this->head;
    if(this->head == -1){
        this->head = newPos;
    }
    else{
        this->next[newPos] = this->head;
        this->head = newPos;
    }
    this->slla_size++;
    return true;

}


bool Set::remove(TElem elem) {
	int previous = -1;
    int current = this->head;
    while (current != -1 && this->elements[current] != elem){
        previous = current;
        current = this->next[current];
    }
    if(current != -1){
        if(current == this->head){
            this->head = this->next[this->head];
        }
        else{
            this->next[previous] = this->next[current];
        }
        this->next[current] = this->firstEmpty;
        this->firstEmpty = current;
        this->slla_size--;
        return true;
    }
	return false;
}

bool Set::search(TElem elem) const {
	int current = this->head;
    while(current != -1){
        if(this->elements[current] == elem){
            return true;
        }
        current = this->next[current];
    }
	return false;
}


int Set::size() const {
	return this->slla_size;
}


bool Set::isEmpty() const {
    return this->slla_size == 0;
}


Set::~Set() {
	delete[] this->next;
    delete[] this->elements;
}

void Set::print_slla_set() {
    std::cout << "Elements: ";
    for (int i = 0; i < this->slla_size; i++) {
        std::cout << this->elements[i] << " ";
    }
    std::cout << std::endl;
    std::cout << "Next:     ";
    for (int i = 0; i < this->slla_size; i++) {
        std::cout << this->next[i] << " ";
    }
}
SetIterator Set::iterator() const {
	return SetIterator(*this);
}


