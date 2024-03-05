#include "MultiMapIterator.h"
#include "MultiMap.h"

//best case: theta(1) complexity
// average case: theta(1) complexity
// worst case: theta(1) complexity
MultiMapIterator::MultiMapIterator(const MultiMap& c): col(c) {
    this->keyIndex = this->col.head_key;
    if(this->keyIndex != -1){
        this->valueIndex = this->col.key_nodes[this->keyIndex].head_value;
    }
    else{
        this->valueIndex = -1;
    }
}

//best case: theta(1) complexity
// average case: theta(1) complexity
// worst case: theta(1) complexity
// general complexity: theta(1) complexity
TElem MultiMapIterator::getCurrent() const{
	if(!valid()){
        throw std::exception();
    }
    return make_pair(this->col.key_nodes[keyIndex].key, this->col.key_nodes[this->keyIndex].value_nodes[this->valueIndex].value);
}

//best case: theta(1) complexity
// average case: theta(1) complexity
// worst case: theta(1) complexity
// general complexity: theta(1) complexity
bool MultiMapIterator::valid() const {
	if(this->keyIndex > -1 && this->keyIndex < this->col.capacity_key && this->valueIndex > -1 && this->valueIndex < this->col.key_nodes[this->keyIndex].capacity_value)
	    return true;
    return false;
}

//best case: theta(1) complexity
// average case: theta(1) complexity
// worst case: theta(1) complexity
// general complexity: theta(1) complexity
void MultiMapIterator::next() {
	if(!valid()){
        throw std::exception();
    }
    this->valueIndex = this->col.key_nodes[this->keyIndex].value_nodes[this->valueIndex].next_value;
    if(this->valueIndex == -1){
        this->keyIndex = this->col.key_nodes[this->keyIndex].next_key;
        this->valueIndex = this->col.key_nodes[this->keyIndex].head_value;
    }
}

//best case: theta(1) complexity
// average case: theta(1) complexity
// worst case: theta(1) complexity
// general complexity: theta(1) complexity
void MultiMapIterator::first() {
    this->keyIndex = this->col.head_key;
    if(this->keyIndex != -1){
        this->valueIndex = this->col.key_nodes[this->keyIndex].head_value;
    }
    else{
        this->valueIndex = -1;
    }
}

