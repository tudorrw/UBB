#include "Map.h"
#include "MapIterator.h"
#include <exception>
using namespace std;

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
MapIterator::MapIterator(const Map& d) : map(d)
{
    this->current = this->map.head;

}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
void MapIterator::first() {
    this->current = this->map.head;
}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
void MapIterator::next() {
    if (!valid()) {
        throw std::exception();
    }
    this->current = this->current->next;
}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
TElem MapIterator::getCurrent(){
    if (!valid()) {
        throw std::exception();
    }
    return TElem(this->current->key, this->current->value);
}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
bool MapIterator::valid() const {
    return this->current != nullptr;
}



