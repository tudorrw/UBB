#include <exception>
#include "BagIterator.h"
#include "Bag.h"

using namespace std;


BagIterator::BagIterator(const Bag& c): bag(c)  //theta(1) Complexity
{
    this->currentIndex = 0;
    this->currentFreq = 0;
}

void BagIterator::first() {  //theta(1) Complexity
    this->currentIndex = 0;
}

void BagIterator::next() { //theta(1) Complexity
    if (!valid()) {
        throw std::exception();
    }
    if (this->currentIndex < bag.getOnlyElements())
        if (currentFreq < bag.elements[currentIndex].second - 1)
            this->currentFreq++;
        else {
            this->currentIndex++;
            this->currentFreq = 0;
        }
}

bool BagIterator::valid() const { //theta(1) Complexity
    if(0 <= this->currentIndex && this->currentIndex < bag.getOnlyElements())
        return true;
    return false;
}

TElem BagIterator::getCurrent() const  //theta(1) Complexity
{   if(valid()) {
        return bag.elements[currentIndex].first;
    }
    else{
        throw std::exception();
    }
}
