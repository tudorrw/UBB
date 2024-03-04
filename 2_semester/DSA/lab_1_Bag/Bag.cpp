#include "Bag.h"
#include "BagIterator.h"
#include <exception>
#include <iostream>
using namespace std;


Bag::Bag(){
    this->nrElements = 0;
    this->capacity = 2;
    this->elements = new pair<TElem, int> [this->capacity];
}

Bag::Bag(const Bag& other){   //copy constructor
    this->nrElements = other.nrElements;
    this->capacity = other.capacity;
    this->elements = new pair<TElem, int> [this->capacity];
    for(int index = 0; index < other.getOnlyElements(); index++){
        this->elements[index] = other.elements[index];
    }
}

int Bag::getOnlyElements() const{  //theta(n) Complexity
    int length = this->nrElements;
    int index = 0;
    while(index < length){
        length = length - this->elements[index].second + 1; //wir subtrahieren die frequenz aus der length, damit konnen wir nur die Anzahl von elementen finden
        index++;
    }
    return length;
}

void Bag::changeCapacity() {  // theta(n) complexity
    this->capacity *= 2;
    pair<TElem, int>* tempArray = new pair<TElem, int> [this->capacity];
    for (int i = 0; i < getOnlyElements(); i++) {
        tempArray[i] = this->elements[i];
    }
    delete[]this->elements;
    this->elements = tempArray;
}

void Bag::add(TElem elem) {  //
	if(this->capacity == this->nrElements){
        changeCapacity();
    }

    int length = getOnlyElements();
    for(int index = 0; index < length; index++)
        if(elem == elements[index].first){
            elements[index].second++;
            this->nrElements++;
            return;
        }
    this->elements[length] = make_pair(elem, 1);
    this->nrElements++;
}

bool Bag::remove(TElem elem) {
    int length = getOnlyElements();
    int index = 0;
    while(index < length && this->elements[index].first != elem){
        index++;
    }
    if(index < length){
        if(elements[index].second > 1){
            elements[index].second--;
        }
        else{
            elements[index].first = elements[length - 1].first;
            elements[index].second = elements[length - 1].second;
            }
        this->nrElements--;
        return true;
    }
	return false; 
}

bool Bag::search(TElem elem) const {
    int length = getOnlyElements();
    int index = 0;
    while(index < length){
        if(this->elements[index].first == elem){
            return true;
        }
        index++;
    }
	return false; 
}

int Bag::nrOccurrences(TElem elem) const {
    int index = 0;
    int length = getOnlyElements();
    while(index < length && this->elements[index].first != elem){
        index++;
    }
    if(index < length)
        return this->elements[index].second;
    return 0;
}

int Bag::size() const {  //theta(1) Complexity
	return this->nrElements;
}

bool Bag::isEmpty() const {  //theta(1) Complexity
	return this->nrElements == 0;
}


BagIterator Bag::iterator() const { //theta(1) Complexity

	return BagIterator(*this);
}


Bag::~Bag() {  //theta(1) Complexity
    delete[] this->elements;
}


Bag Bag::reunion(const Bag& other) const{  // theta(n^2) Complexity
    Bag reuniteBag(other);

    int thisLength = getOnlyElements();
    int otherLength= other.getOnlyElements();

    int booleanArray[thisLength];
    for(int index = 0; index < thisLength; index++){
        booleanArray[index] = 0;
    }
    for(int i = 0; i < otherLength; i++){
        for(int j = 0; j < thisLength; j++){
            if(reuniteBag.elements[i].first == this->elements[j].first && !booleanArray[j]){
                reuniteBag.elements[i].second += this->elements[j].second;
                reuniteBag.nrElements += this->elements[j].second;
                booleanArray[j] = 1;
            }
        }
    }

    for(int index = 0; index < thisLength; index++){
        if(reuniteBag.capacity == reuniteBag.nrElements){   //wenn die capacity kleiner als nrElements, doppeln wir die capacity
            reuniteBag.capacity *= 2;
            pair<TElem, int>* tempArray = new pair<TElem, int> [reuniteBag.capacity];
            for (int i = 0; i < getOnlyElements(); i++) {
                tempArray[i] = reuniteBag.elements[i];
            }
            delete[] reuniteBag.elements;
            reuniteBag.elements = tempArray;
        }
        if(!booleanArray[index]){   // geben wir die bleibende Elementen von den other dynamic array von reuniteBag mit einen Frequenz von 1
            reuniteBag.elements[otherLength++] = make_pair(this->elements[index].first, 1);
            reuniteBag.nrElements++;
        }
    }
    return reuniteBag;
}

void Bag::printBag() const {
    int length = getOnlyElements();
    int index = 0;
    while(index < length){
        std::cout << "[" << elements[index].first << ", " << elements[index].second << "]" << " ";
        index++;
    }
    cout << endl;
}


//method reunion(other class instance) is: //class type
//
//boolLength <- getOnlyElements method
//booleanArray[boolLength] // boolean array which occupies
//for i in boolLength, execute
//        booleanArray[i] <-0
//
//length <- other.getOnlyElements method
//for i in length, execute
    //for i in getOnlyElements(), execute





