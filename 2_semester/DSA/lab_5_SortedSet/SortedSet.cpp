#include "SortedSet.h"
#include "SortedSetIterator.h"
#include <iostream>

//n - bst capacity
//best case: theta(n), average case: theta(n), worst case: theta(n)
//general time complexity: theta(n)
SortedSet::SortedSet(Relation r) {
    this->relation = r;
	this->bst_capacity = 2;

    this->elements = new Node[this->bst_capacity];

    for(int i = 0; i < this->bst_capacity - 1; i++){
        this->elements[i].left = i + 1;
    }
    this->elements[this->bst_capacity - 1].left = -1;

    this->bst_size = 0;
    this->root = -1;
    this->first_empty = 0;

}
//n - bst capacity
//best case: theta(n), average case: theta(n), worst case: theta(n)
//general time complexity: theta(n)
void SortedSet::resize() {
    int old_capacity = this->bst_capacity;
    this->bst_capacity *= 2;
    //the current values of the bst array are copied in the temporary array
    Node *temp = this->elements;
    //initialise a new bst array where will resize and reallocate the elements
    this->elements = new Node[this->bst_capacity];
    for(int i = 0; i < old_capacity; i++){
        this->elements[i] = temp[i];
    }
    for(int i = old_capacity; i < this->bst_capacity - 1; i++){
        this->elements[i].left = i + 1;
    }
    this->elements[this->bst_capacity - 1].left = -1;
    this->first_empty = old_capacity;
    delete[] temp;
}

//best case: theta(1) - creates the root
//average case: theta(log(n)), depends on the height of the bst, which is logarithmic on average for a balanced tree
//worst case: theta(n) - for a completely unbalanced bst
//general time complexity: O(h) the height of bst
int SortedSet::insert_rec(int node, TComp elem){
    if(node == -1){ //finds an empty position in the bst
        int newNode = this->first_empty;
        this->first_empty = this->elements[this->first_empty].left; //first empty goes to the next available position
        this->elements[newNode].info = elem; //set the value of the new node  to the given element
        //initialising te left and right child of the new node with -1 (means there are no children for the moment)
        this->elements[newNode].left = -1;
        this->elements[newNode].right = -1;
        return newNode; //index of the new node
    }
    else if(this->relation(elem, this->elements[node].info)){
        //insert the element recursively in the left subtree
        this->elements[node].left = this->insert_rec(this->elements[node].left, elem);
    }
    else if(this->relation(this->elements[node].info, elem)){
        //insert the element recursively in the right subtree
        this->elements[node].right = this->insert_rec(this->elements[node].right, elem);
    }
    return node; //index of the current node
}

//best case: theta(1) - the searched element is actually the root
//average case: theta(log(n)), if the bst is balanced the height is logarithmic in the number of elements
//worst case: theta(n) - for a completely unbalanced bst
//general time complexity: O(h) the height of bst
//the same time complexity as the search function, it uses the search function to see if the element is already in the bst
bool SortedSet::add(TComp elem) {

    if(search(elem)){ //if the element is already in the bst returns false
        return false;
    }

    if(this->bst_capacity == this->bst_size){ //resizes the array by doubling the capacity, if the size has reached its capacity
        resize();
    }

    this->root = this->insert_rec(this->root, elem);
    this->bst_size++;
    return true;
}

//best average worst case: theta(h) - h is the height of the subtree whose root is the right child of the deleted node
//the minimal value on the right subtree, replace the deleted value with the minimal value
int SortedSet::minimum(int node) {
    int currentNode = node;
    while(this->elements[currentNode].left != -1){
        currentNode = this->elements[currentNode].left;
    }
    return currentNode; //retuns the index of the leftmost node
}

//best case: theta(n): when the given node is the root and has no children
//average case: theta(log(n)), depends on the height of the bst, which is logarithmic on average for a balanced tree
//worst case: theta(n), for a completely unbalanced tree
//general time complexity: O(n)
int SortedSet::remove_rec(int node, TComp elem) {
    if(node == -1)
        return -1;

    if(this->elements[node].info == elem){
        int removedNode;
        if (this->elements[node].left == -1 && this->elements[node].right == -1) { //the deleted node is a leaf node
            removedNode = node;
            this->elements[removedNode].left = this->first_empty;
            this->first_empty = removedNode;
            return -1;
        } else if (this->elements[node].left == -1) { //the deleted node has only the right child
            removedNode = node;
            int rightChild = this->elements[node].right;
            this->elements[removedNode].left = this->first_empty;
            this->first_empty = removedNode;
            return rightChild;
        } else if (this->elements[node].right == -1) { //the deleted node has only the left child
            removedNode = node;
            int leftChild = this->elements[node].left;
            this->elements[removedNode].left = this->first_empty;
            this->first_empty = removedNode;
            return leftChild;
        }
        else{
            int min = minimum(this->elements[node].right); //find the minimum element in the right subtree
            this->elements[node].info = this->elements[min].info; //replace the info of the current node with the minimum value
            this->elements[node].right = this->remove_rec(this->elements[node].right, this->elements[min].info); //remove the minimum value from the right subtree
        }
    }
    else if(this->relation(elem, this->elements[node].info)){
        this->elements[node].left = this->remove_rec(this->elements[node].left, elem);
    }
    else{
        this->elements[node].right = this->remove_rec(this->elements[node].right, elem);
    }
    return node;
}

//best case: theta(n): when the given node is the root and has no children
//average case: theta(log(n)), depends on the height of the bst, which is logarithmic on average for a balanced tree
//worst case: theta(n), for a completely unbalanced tree
//general time complexity: O(n)
bool SortedSet::remove(TComp elem) {
    if(!search(elem)){ //if the element is already in the bst returns false
        return false;
    }
    this->root = this->remove_rec(this->root, elem);
	this->bst_size--;
    return true;
}
//best case: theta(1) - the searched element is actually the root
//average case: theta(log(n)), depends on the height of the bst, which is logarithmic on average for a balanced tree
//worst case: theta(n) - the bst size, for a completely unbalanced bst
//general time complexity: O(h) the height of bst
bool SortedSet::search(TComp elem) const {
	int currentNode = this->root; //the index of the first element
    while(currentNode != -1){ //repeats the steps until the element is found or has reached a leaf node without finding the element
        if(elem == this->elements[currentNode].info){
            return true;
        }
        //compares elem with the element on the current node
        if(relation(elem, this->elements[currentNode].info)){
            //depending on the relation, when the given elem is smaller/bigger than the element on the current index, it moves to the left/right child
            currentNode = this->elements[currentNode].left;
        } else {
            //otherwise, it moves to the right/left child
            currentNode = this->elements[currentNode].right;
        }
    }
    return false;
}

//best case: theta(1), average case: theta(1), worst case: theta(1)
//general time complexity: theta(1)
int SortedSet::size() const {
	return this->bst_size;
}


//best case: theta(1), average case: theta(1), worst case: theta(1)
//general time complexity: theta(1)
bool SortedSet::isEmpty() const {
    return this->bst_size == 0;
}

//best case: theta(1), average case: theta(1), worst case: theta(1)
//general time complexity: theta(1)
SortedSetIterator SortedSet::iterator() const {
	return SortedSetIterator(*this);
}

//best case: theta(1), average case: theta(1), worst case: theta(1)
//general time complexity: theta(1)
SortedSet::~SortedSet() {
	delete []this->elements; //deallocate the memory allocated for the dynamic array
}

//n - bst capacity
//best case: theta(n), average case: theta(n), worst case: theta(n)
//general time complexity: theta(n)
void SortedSet::printSet() {
    for(int i = 0; i < this->bst_size; i++){
        std::cout << "info: " << this->elements[i].info << ", left: " << this->elements[i].left << ", right: " << this->elements[i].right << std::endl;

    }
}

