#include <exception>
#include <iostream>
#include "IndexedList.h"
#include "ListIterator.h"

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
IndexedList::IndexedList() {
	this->head = nullptr;
    this->listSize = 0;

}


//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
int IndexedList::size() const {
	return this->listSize;
}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
bool IndexedList::isEmpty() const {
    return this->listSize == 0;
}
//best case: theta(1) complexity: falls die gegebene Position ungültig ist
//worst case: theta(n) complexity: falls die gegebene Position, die letzte Position ist
//allgemeine Komplexität: O(n)
TElem IndexedList::getElement(int pos) const {
    //TODO - Implementation
    if(pos < 0 || pos > this->listSize - 1){
        throw std::exception();
    }
    Node *currentNode = this->head;
    int position = 0;
    while(position < pos && currentNode != nullptr) {
        currentNode = currentNode->next;
        position++;
    }
    if(currentNode == nullptr){
        throw std::exception();
    }
    return currentNode->info;
}
//best case: theta(1) complexity: falls die gegebene Position ungültig ist
//worst case: theta(n) complexity: falls die gegebene Position, die letzte Position ist
//allgemeine Komplexität: O(n)
TElem IndexedList::setElement(int pos, TElem e) {
    //TODO - Implementation
    if(pos < 0 || pos > this->listSize - 1){
        throw std::exception();
    }
    Node *currentNode = this->head;
    int position = 0;
    while(position < pos && currentNode != nullptr){
        currentNode = currentNode->next;
        position++;
    }

    int value = currentNode->info;
    currentNode->info = e;

	return value;
}



//Best Case: theta(1) complexity: falls die List leer ist
//Worst Case: theta(n) complexity: fall die List nicht leer ist, es muss durch jedes Element durchlaufen, bis es zum Ende ankommt
void IndexedList::addToEnd(TElem e) {
    //TODO - Implementation
    Node *newNode = new Node(); //einen neuen SLLNode allokieren
    newNode->info = e;
    if(this->head == nullptr){
        this->head = newNode;
    }
    else{
        Node *currentNode = this->head;
        int position = 0;
        while(position < this->listSize - 1 && currentNode != nullptr){
            currentNode = currentNode->next;
            position++;
        }
        if(currentNode != nullptr){
            newNode->next = currentNode->next;
            currentNode->next = newNode;
        }
    }
    this->listSize++;
}
//best case: theta(1) complexity; falls die gegebene Position ungültig oder die erste Position in der Liste ist
//average case: theta(n) complexity; falls die gegebene Position irgendeine Wert von 0 zur Länge der Liste - 1 darstellt
//worst case: theta(n) complexity; falls die gegebene Position die letzte Position ist
//allgemeine Komplexität: O(n)
void IndexedList::addToPosition(int pos, TElem e) {
    //TODO - Implementation
    if(pos < 0 || pos > this->listSize - 1){
        throw std::exception();
    }
    else if(pos == 0){
        Node *insertedNode = new Node();
        insertedNode->info = e;
        insertedNode->next = this->head;
        this->head = insertedNode;
    }
    else{
        Node *currentNode = this->head;
        int position = 0;
        while(position < pos && currentNode != nullptr){
            currentNode = currentNode->next;
            position++;
        }
        if(currentNode != nullptr){
            Node *insertedNode = new Node();
            insertedNode->info = e;
            insertedNode->next = currentNode->next;
            currentNode->next = insertedNode;
        }
    }
    this->listSize++;
}

//best case: theta(1) complexity; falls die gegebene Position ungültig oder die erste Position in der Liste ist
//average case: theta(n) complexity; falls die gegebene Position irgendeine Wert von 0 zur Länge der Liste - 1 darstellt
//worst case: theta(n) complexity; falls die gegebene Position die letzte Position ist
//allgemeine Komplexität: O(n)
TElem IndexedList::remove(int pos) {
    //TODO - Implementation
    if(pos < 0 || pos > this->listSize - 1){
        throw std::exception();
    }
    Node *currentNode = this->head;
    Node *prevNode = nullptr;
    int position = 0;
    while(currentNode != nullptr && position < pos){
        prevNode = currentNode;
        currentNode = currentNode->next;
        position++;
    }
    int value = currentNode->info;
    if(currentNode != nullptr && prevNode == nullptr){ //dann löscht man den Listenkopf
        this->head = this->head->next;
    }
    else if(currentNode != nullptr){
        prevNode->next = currentNode->next;
        currentNode->next = nullptr;
    }
    delete currentNode;
    this->listSize--;
    return value;
}
//worst case: theta(1) Complexity, best case: theta(n) Complexity, average case: theta(n) Complexity  -> O(n) Complexity
//Allgemeine Komplexität O(n): – man kann das gesuchte Element im ersten Knoten finden oder, es kann sein
//dass man die ganze Liste durchlaufen muss
int IndexedList::search(TElem e) const{
    //TODO - Implementation
    Node *currentNode = this->head;
    int position = 0;
    while(currentNode != nullptr && currentNode->info != e){
        currentNode = currentNode->next;
        position++;
    }
    if(currentNode == nullptr){
        return -1;
    }
    return position;
}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
ListIterator IndexedList::iterator() const {
    return ListIterator(*this);        
}

void IndexedList::display() const {
    if(this->head == nullptr){
        return;
    }
    Node *currentNode = this->head;
    while(currentNode != nullptr){
        std::cout << currentNode->info << " ";
        currentNode = currentNode->next;
    }
    std::cout << std::endl;
}

//best case: theta(1) falls k ungültig ist
//worst case: theta(n) falls k den Wert zwischen 1 und listSize - 1 hat

void IndexedList::removeFromKToKPosition(int k){
    if(k < 1 || k > this->listSize){
        throw std::exception();
    }
    else if(k == 1){
        Node *currentNode = this->head;
        while(currentNode != nullptr){
            Node *nextNode = currentNode->next;
            delete currentNode;
            currentNode = nextNode;
            this->listSize--;
        }
    }
    else{
        int position = 0;
        Node *prevNode;
        Node *currentNode = this->head;
        while(currentNode != nullptr) {
            prevNode = currentNode;
            currentNode = currentNode->next;
            position++;
            if((position + 1) % k == 0 && currentNode != nullptr){
                Node *newNode = currentNode;
                prevNode->next = currentNode->next;
                currentNode = prevNode->next;
                delete newNode;
                position++;
                this->listSize--;
            }
        }
    }
}
/** pseudocode
* function removeFromKToKPositions(sll, k) is:
	//pre: sll ist ein SLL, k ist ein Intervall

	if k < 1 or k > sll.size() then
		@error, invalid position

    else if k = 1 then
        currentNode = sll.head
        while currentNode != NIL execute
            nextNode <- [currentNode].next
            deleteElement <- currentNode
            currentNode <- nextNode
            listSize <- listSize - 1
    else
        position <- 0
        prevNode <- NIL
        currentNode <-sll.head

        while position < sll.size()) execute
                prevNode <- currentNode;
                currentNode <- [currentNode].next;
            position <- position + 1
            if (position + 1) mod k = 0 and currentNode != NIL
                newnode <- currentNode;
                [prevNode].next <- [currentNode].next
                currentNode <-[prevNode].next
                deleteElement <- newNode
                position <- position + 1
            end-if
        end-while
    end-if
end-function
*/




//best case: theta(n) complexity; average case: theta(n) complexity; worst case: theta(n) complexity;
IndexedList::~IndexedList() {
    Node *currentNode = this->head;
    while(currentNode != nullptr){
        Node *nextNode = currentNode->next;
        delete currentNode;
        currentNode = nextNode;
    }
}

