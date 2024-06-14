#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <iostream>
#include <vector>
#include <exception>
using namespace std;

//best case: theta(1), average case: theta(1), worst case: theta(1)
//general time complexity: theta(1)
Node::Node() {
    this->key = 0;
    this->values = nullptr;
    this->size = 0;
    this->capacity = 0;
    this->left = -1;
    this->right = -1;
}
//best case: theta(1), average case: theta(1), worst case: theta(1)
//general time complexity: theta(1)
Node::Node(TKey k, TValue v) {
    this->key = k;
    this->capacity = 2;
    this->size = 1;
    this->values = new TValue[this->capacity];
    this->values[0] = v;
    //initialising te left and right child of the new node with -1 (means there are no children for the moment)
    this->left = -1;
    this->right = -1;
}

//best case: theta(1), average case: theta(n), worst case: theta(n)
//general time complexity: O(n)
void Node::addValue(TValue v) {
    if (this->size == this->capacity) {
        this->resizeValues();
    }
    this->values[this->size++] = v;
}

//best case: theta(n), average case: theta(n), worst case: theta(n)
//general time complexity: theta(n)
void Node::resizeValues() {
    this->capacity *= 2;
    TValue* newValues = new TValue[this->capacity];
    for (int i = 0; i < this->size; i++) {
        newValues[i] = this->values[i];
    }
    delete[] this->values;
    this->values = newValues;
}

//n - bst capacity
//best case: theta(n), average case: theta(n), worst case: theta(n)
//general time complexity: theta(n)
SortedMultiMap::SortedMultiMap(Relation r) {
    this->relation = r;
    this->bst_capacity = 6;
    this->elements = new Node[this->bst_capacity];
    for (int i = 0; i < this->bst_capacity - 1; i++) {
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
void SortedMultiMap::resize() {
    int old_capacity = this->bst_capacity;
    this->bst_capacity *= 2;
    //the current values of the bst array are copied in the temporary array
    Node* temp = this->elements;
    //initialise a new bst array where will resize and reallocate the elements
    this->elements = new Node[this->bst_capacity];
    for (int i = 0; i < old_capacity; i++) {
        this->elements[i] = temp[i];
    }
    for (int i = old_capacity; i < this->bst_capacity - 1; i++) {
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
int SortedMultiMap::insert_rec(int node, TKey key, TValue value) {
//    cout << "insert_rec: " << node << endl;
    if (node == -1) { //finds an empty position in the bst
        int newNode = this->first_empty;
        this->first_empty = this->elements[this->first_empty].left;
        this->elements[newNode] = Node(key, value);
        return newNode;
    }
    else if (this->elements[node].key == key) {
        this->elements[node].addValue(value);
    }
    else if (this->relation(key, this->elements[node].key)) {
        //insert the element recursively in the left subtree
        this->elements[node].left = this->insert_rec(this->elements[node].left, key, value);
    } else if (this->relation(this->elements[node].key, key)) {
        //insert the element recursively in the right subtree
        this->elements[node].right = this->insert_rec(this->elements[node].right, key, value);
    }
    return node;
}
//best case: theta(1) - the searched element is actually the root
//average case: theta(log(n)), if the bst is balanced the height is logarithmic in the number of elements
//worst case: theta(n) - for a completely unbalanced bst
//general time complexity: O(h) the height of bst
//the same time complexity as the search function, it uses the search function to see if the element is already in the bst
void SortedMultiMap::add(TKey c, TValue v) {
    if (this->bst_capacity == this->bst_size) {
        resize();
    }
    this->root = this->insert_rec(this->root, c, v);
    this->bst_size++;
}


//best case: theta(1) - the searched element is actually the root
//average case: theta(log(n)), depends on the height of the bst, which is logarithmic on average for a balanced tree
//worst case: theta(n) - the bst size, for a completely unbalanced bst
//general time complexity: O(h) the height of bst
vector<TValue> SortedMultiMap::search(TKey c) const {
    int currentNode = this->root;
    while (currentNode != -1) { //repeats the steps until the element is found or has reached a leaf node without finding the element
        if (c == this->elements[currentNode].key) {
            vector<TValue> result(this->elements[currentNode].values, this->elements[currentNode].values + this->elements[currentNode].size);
            return result;
        }
        //compares elem with the element on the current node
        if (relation(c, this->elements[currentNode].key)) {
            //depending on the relation, when the given elem is smaller/bigger than the element on the current index, it moves to the left/right child
            currentNode = this->elements[currentNode].left;
        } else {
            //otherwise, it moves to the right/left child
            currentNode = this->elements[currentNode].right;
        }
    }
    return vector<TValue>();
}

//best average worst case: theta(h) - h is the height of the subtree whose root is the right child of the deleted node
//the minimal value on the right subtree, replace the deleted value with the minimal value
int SortedMultiMap::minimum(int node) {
    int currentNode = node;
    while(this->elements[currentNode].left != -1){
        currentNode = this->elements[currentNode].left;
    }
    return currentNode; //retuns the index of the leftmost node
}


//best case: theta(1): when the given node is the root and has no children
//average case: theta(log(n)), depends on the height of the bst, which is logarithmic on average for a balanced tree
//worst case: theta(n), for a completely unbalanced tree
//general time complexity: O(n)
int SortedMultiMap::remove_rec(int node, TKey key, TValue value, bool &found) {
    if (node == -1) {
        return -1;
    }
    if (this->elements[node].key == key) {
        auto& values = this->elements[node].values;
        for (int i = 0; i < this->elements[node].size; ++i) {
            if (values[i] == value) {
                found = true;
                for (int j = i; j < this->elements[node].size - 1; ++j) {
                    values[j] = values[j + 1];
                }
                this->elements[node].size--;
                break;
            }
        }
        if (this->elements[node].size == 0) {
            if (this->elements[node].left == -1 && this->elements[node].right == -1) {
                int removedNode = node;
                this->elements[removedNode].left = this->first_empty;
                this->first_empty = removedNode;
                return -1;
            } else if (this->elements[node].left == -1) {
                int removedNode = node;
                int rightChild = this->elements[node].right;
                this->elements[removedNode].left = this->first_empty;
                this->first_empty = removedNode;
                return rightChild;
            } else if (this->elements[node].right == -1) {
                int removedNode = node;
                int leftChild = this->elements[node].left;
                this->elements[removedNode].left = this->first_empty;
                this->first_empty = removedNode;
                return leftChild;
            } else {
                int min = minimum(this->elements[node].right);
                this->elements[node].key = this->elements[min].key;
                delete[] this->elements[node].values;
                this->elements[node].values = new TValue[this->elements[min].capacity];
                this->elements[node].size = this->elements[min].size;
                for (int i = 0; i < this->elements[min].size; ++i) {
                    this->elements[node].values[i] = this->elements[min].values[i];
                }
                this->elements[node].right = this->remove_rec(this->elements[node].right, this->elements[min].key, value, found);
            }
        }
    } else if (this->relation(key, this->elements[node].key)) {
        this->elements[node].left = this->remove_rec(this->elements[node].left, key, value, found);
    } else {
        this->elements[node].right = this->remove_rec(this->elements[node].right, key, value, found);
    }
    return node;
}

//best case: theta(1): when the given node is the root and has no children
//average case: theta(log(n)), depends on the height of the bst, which is logarithmic on average for a balanced tree
//worst case: theta(n), for a completely unbalanced tree
//general time complexity: O(n)
bool SortedMultiMap::remove(TKey c, TValue v) {
    bool found = false;
    this->root = this->remove_rec(this->root, c, v, found);
    if (found) {
        this->bst_size--;
    }
    return found;
}

//best case: theta(1), average case: theta(1), worst case: theta(1)
//general time complexity: theta(1)
int SortedMultiMap::size() const {
	return this->bst_size;
}

//best case: theta(1), average case: theta(1), worst case: theta(1)
//general time complexity: theta(1)
bool SortedMultiMap::isEmpty() const {
	return this->bst_size == 0;
}

//best case: theta(1), average case: theta(1), worst case: theta(1)
//general time complexity: theta(1)
SMMIterator SortedMultiMap::iterator() const {
	return SMMIterator(*this);
}

//best case: theta(n), average case: theta(n), worst case: theta(n)
//general time complexity: theta(n)
SortedMultiMap::~SortedMultiMap() {
    for (int i = 0; i < this->bst_capacity; ++i) {
        if (this->elements[i].size > 0) {
            delete[] this->elements[i].values;
        }
    }
    delete[] this->elements;

}
