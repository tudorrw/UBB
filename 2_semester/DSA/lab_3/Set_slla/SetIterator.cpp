#include "SetIterator.h"
#include "Set.h"


SetIterator::SetIterator(const Set& set) : set(set)
{
	this->currentElement = set.head;
}


void SetIterator::first() {
    this->currentElement = set.head;
}


void SetIterator::next() {
    if(!valid()){
        throw std::exception();
    }
    this->currentElement = this->set.next[this->currentElement];
}


TElem SetIterator::getCurrent(){
    if(!valid()){
        throw std::exception();
    }
    return this->set.elements[this->currentElement];

}

bool SetIterator::valid() const {
    return this->currentElement != -1;
}



