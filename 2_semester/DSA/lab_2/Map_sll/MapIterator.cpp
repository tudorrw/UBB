#include "Map.h"
#include "MapIterator.h"
#include <exception>
using namespace std;


MapIterator::MapIterator(const Map& d) : map(d)
{
    this->current = this->map.head;
}


void MapIterator::first() {
    this->current = this->map.head;
}


void MapIterator::next() {
    if(!valid()){
        throw std::exception();
    }
    this->current = this->current->next;
}


TElem MapIterator::getCurrent(){
	if(!valid()){
        throw std::exception();
    }
    return TElem(this->current->key, this->current->value);
}


bool MapIterator::valid() const {
	return this->current != nullptr;
}



