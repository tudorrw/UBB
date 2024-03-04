#include "ListIterator.h"
#include "IndexedList.h"
#include <exception>

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
ListIterator::ListIterator(const IndexedList& list) : list(list){
    this->currentElement = list.head;
}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
void ListIterator::first(){
    //TODO - Implementation
    this->currentElement = list.head;

}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
void ListIterator::next(){
    if(this->currentElement == nullptr){
        throw std::exception();
    }
    this->currentElement = this->currentElement->next;
}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
bool ListIterator::valid() const{
    return currentElement != nullptr;
}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
TElem ListIterator::getCurrent() const{
   if(currentElement == nullptr){
       throw std::exception();
   }
	return currentElement->info;
}