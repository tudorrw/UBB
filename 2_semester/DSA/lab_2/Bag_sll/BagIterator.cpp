#include <exception>
#include "BagIterator.h"
#include "Bag.h"

using namespace std;


BagIterator::BagIterator(const Bag& c): bag(c)
{
	this->current = this->bag.head;
    if(this->current != nullptr){
        this->freq = this->bag.head->freq;
    }
}

void BagIterator::first() {
    this->current = this->bag.head;
    if(this->current != nullptr){
        this->freq = this->bag.head->freq;
    }
}


void BagIterator::next() {
	if(!valid()){
        throw std::exception();
    }
    if(this->freq == 1){
        this->current = this->current->next;
        if(this->current != nullptr){
            this->freq = this->current->freq;
        }
    }
    else{
        this->freq--;
    }
}


bool BagIterator::valid() const {

	return this->current != nullptr;
}



TElem BagIterator::getCurrent() const
{
	if(!valid()){
        throw std::exception();
    }
    return this->current->value;
}
