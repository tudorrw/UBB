/**
 * ADT PriorityQueue – repräsentiert mithilfe einer SLL von Paaren (Element, Priorität)
 * sortiert nach den Prioritäten mithilfe einer Relation
 */
#include "PriorityQueue.h"
#include <exception>
using namespace std;

//best case: theta(1) complexity
//average case: theta(1) complexity
//worst case: theta(1) complexity
//general complexity: theta(1)
PriorityQueue::PriorityQueue(Relation r) {
	this->head = nullptr;
    this->relation = r;
}

//best case: theta(1): wenn es in der ersten if-Bedingung eintritt
//worst case: theta(n): wenn das als Parameter angegebene Element die höchste Priorität hat
void PriorityQueue::push(TElem e, TPriority p) {
    Node *newNode = new Node();
    newNode->data = Element(e, p);
    //falls die Schlange leer ist oder das als Parameter angegebene Element die niedrigste Priorität hat
    if(this->head == nullptr || relation(p, this->head->data.second)){
        newNode->next = this->head;
        this->head = newNode;
    }
    else{
        Node *currentNode = this->head;
        while(currentNode->next != nullptr && !relation(p, currentNode->next->data.second)){
            currentNode = currentNode->next;
        }
        newNode->next = currentNode->next;
        currentNode->next = newNode;
    }

}

//throws exception if the queue is empty
//best case: theta(1) complexity
//average case: theta(1) complexity
//worst case: theta(1) complexity
//general complexity: theta(1)
Element PriorityQueue::top() const {
	if(isEmpty()){
        throw std::exception();
    }
	return this->head->data;
}

//best case: theta(1) complexity
//average case: theta(1) complexity
//worst case: theta(1) complexity
//general complexity: theta(1)
Element PriorityQueue::pop() {
    if(isEmpty()){
        throw std::exception();
    }
	 Element elem = this->head->data;
     Node *temp = this->head;
     this->head = this->head->next;
     delete temp;
     return elem;
}

//best case: theta(1) complexity
//average case: theta(1) complexity
//worst case: theta(1) complexity
//general complexity: theta(1)
bool PriorityQueue::isEmpty() const {
	return this->head == nullptr;
}

//best case: theta(n) complexity
//average case: theta(n) complexity
//worst case: theta(n) complexity
//general complexity: theta(n)
PriorityQueue::~PriorityQueue() {
	while(this->head != nullptr){
        Node *temp = this->head;
        this->head = this->head->next;
        delete temp;
    }
};

