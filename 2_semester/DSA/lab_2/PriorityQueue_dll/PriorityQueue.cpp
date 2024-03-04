
#include "PriorityQueue.h"
#include <exception>
using namespace std;


PriorityQueue::PriorityQueue(Relation r) {
	this->head = nullptr;
    this->tail = nullptr;
    this->relation = r;
}


void PriorityQueue::push(TElem e, TPriority p) {
    Node* newNode = new Node();
    newNode->data = Element (e, p);
    if (isEmpty()) {
        this->head = newNode;
        this->tail = newNode;
    }
    else {
        Node* current = this->head;
        while (current != nullptr && relation(current->data.second, p)) {
            current = current->next;
        }
        if (current == this->head) {
            newNode->next = this->head;
            this->head->prev = newNode;
            this->head = newNode;
        }
        else if (current == nullptr) {
            newNode->prev = this->tail;
            this->tail->next = newNode;
            this->tail = newNode;
        }
        else {
            newNode->prev = current->prev;
            newNode->next = current;
            current->prev->next = newNode;
            current->prev = newNode;
        }
    }
}

//throws exception if the queue is empty
Element PriorityQueue::top() const {
	if(isEmpty()){
        throw std::exception();
    }
	return this->head->data;
}

//throws exception if the queue is empty
Element PriorityQueue::pop() {
	if(isEmpty()){
        throw std::exception();
    }
    Element elem = this->head->data;
    Node *temp = this->head;
    if(this->head == this->tail){
        this->head = nullptr;
        this->tail = nullptr;
    }
    else{
        this->head = this->head->next;
        this->head->prev = nullptr;
    }

	delete temp;
    return elem;
}

bool PriorityQueue::isEmpty() const {
	return this->head == nullptr && this->tail == nullptr;
}


PriorityQueue::~PriorityQueue() {
	while(this->head != nullptr){
        Node *temp = this->head;
        this->head = this->head->next;
        delete temp;
    }
};

