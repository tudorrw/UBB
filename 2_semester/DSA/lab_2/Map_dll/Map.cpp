#include "Map.h"
#include "MapIterator.h"

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
Map::Map() {
	this->head = nullptr;
    this->tail = nullptr;
    this->map_size = 0;

}
//best case: theta(n) complexity; average case: theta(n) complexity; worst case: theta(n) complexity;
Map::~Map() {
    Node *currentNode = this->head, *next;
    while(currentNode != nullptr){
        next = currentNode->next;
        delete currentNode;
        currentNode = next;
    }
}
// Komplexität: Beste und durchschnittliche Fälle: Theta(1), schlechtester Fall: Theta(n)
// Wenn das Element bereits vorhanden ist, wird es aktualisiert. Sonst wird es am Anfang der Liste eingefügt.
TValue Map::add(TKey c, TValue v){
	//TODO - Implementation
    Node *newNode = new Node();
    newNode->key = c;
    newNode->value = v;
    if(isEmpty()) {
        this->head = newNode;
        this->tail = newNode;
    }
    else {
        Node *currentNode = this->head;
        while(currentNode != nullptr && currentNode->key != c){
            currentNode = currentNode->next;
        }
        if(currentNode != nullptr) {
            int oldVal = currentNode->value;
            currentNode->value = v;
            return oldVal;
        }
        else {
            //Am Anfang der Container einfugen
            newNode->next = this->head;
            this->head->prev = newNode;
            this->head = newNode;
        }
    }
    this->map_size++;
    return NULL_TVALUE;
}


// Komplexität: Beste Fall: Theta(1), durchschnittliche und schlechteste Fälle: Theta(n)
// Allgemeine Komplexität O(n): Man kann das gesuchte Element im ersten Knoten finden, oder es kann sein,
// dass man die ganze Liste durchlaufen muss.
TValue Map::search(TKey c) const{
    if(!isEmpty()){
        Node *currentNode = this->head;
        while(currentNode != nullptr && currentNode->key != c){
            currentNode = currentNode->next;
        }
        if(currentNode != nullptr){
            return currentNode->value;
        }
    }
    return NULL_TVALUE;
}
// Komplexität: Beste Fall: Theta(1), durchschnittliche und schlechteste Fälle: Theta(n)
// Allgemeine Komplexität O(n): Man kann das zu löschende Element im ersten Knoten finden, oder es kann sein,
// dass man die ganze Liste durchlaufen muss.
TValue Map::remove(TKey c){
	if(!isEmpty()) {
        Node *currentNode = this->head;
        while(currentNode != nullptr && currentNode->key != c) {
            currentNode = currentNode->next;
        }
        if(currentNode != nullptr) {
            int val = currentNode->value;
            if(currentNode->prev != nullptr) {
                currentNode->prev->next = currentNode->next;
            }
            else {
                this->head = currentNode->next;
            }
            if(currentNode->next != nullptr) {
                currentNode->next->prev = currentNode->prev;
            }
            else {
                this->tail = currentNode->prev;
            }
            delete currentNode;
            this->map_size--;
            return val;
        }
    }
	return NULL_TVALUE;
}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
int Map::size() const {
	return this->map_size;
}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
bool Map::isEmpty() const{
    return this->map_size == 0;
}

//best case: theta(1) complexity; average case: theta(1) complexity; worst case: theta(1) complexity;
MapIterator Map::iterator() const {
	return MapIterator(*this);
}



