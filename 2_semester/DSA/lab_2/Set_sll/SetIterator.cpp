#include "SetIterator.h"
#include "Set.h"
#include <exception>

SetIterator::SetIterator(const Set& m) : set(m)
{
	this->current = this->set.head;
}


void SetIterator::first() {
    this->current = this->set.head;
}


void SetIterator::next() {
	if(!valid()){
        throw std::exception();
    }
    this->current = this->current->next;
}


TElem SetIterator::getCurrent()
{
	if(!valid()){
        throw std::exception();
    }
	return this->current->value;
}

bool SetIterator::valid() const {

	return this->current != nullptr;
}



