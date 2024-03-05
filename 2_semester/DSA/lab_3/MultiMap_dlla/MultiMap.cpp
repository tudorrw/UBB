#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <exception>
#include <iostream>

using namespace std;

//best case: theta(n) complexity, where represents the given capacity
//average case: theta(n) complexity
//worst case: theta(n) complexity
//general complexity: theta(n)
MultiMap::MultiMap() {
    this->capacity_key = 6;
    this->size_keys = 0;
    this->key_nodes = new KeyNode[this->capacity_key];
    for(int i = 0; i < this->capacity_key - 1; i++){
        this->key_nodes[i].prev_key = i - 1;
        this->key_nodes[i].next_key = i + 1;
    }
    this->key_nodes[this->capacity_key - 1].next_key = -1;
    this->first_empty_keys = 0;
    this->head_key = -1;
    this->tail_key = -1;
}

//best case:theta(1)
//average case: O(n)
//worst case: theta(n), n number of keys, m initial capacity for value nodes
//total: O(n) n number of keys, m initial capacity for value nodes
void MultiMap::add(TKey c, TValue v) {

    // check if the key already exists in the multimap
    TKey currentKey = this->head_key;

    while(currentKey != -1 && this->key_nodes[currentKey].key != c){
        currentKey = this->key_nodes[currentKey].next_key;
    }
    // if the key doesn't exist, add a new key node
    if(currentKey == -1){
        // get a new key node from the free list or allocate memory
        int newKeyNode;
        //if there is at least an empty space in the multimap
        if(this->first_empty_keys != -1){
            newKeyNode = this->first_empty_keys;
            this->first_empty_keys = this->key_nodes[this->first_empty_keys].next_key;
            this->size_keys++;
        }
        else{
            //if the size is equal to the capacity we double the capacity resizing the key_nodes
            newKeyNode = this->size_keys;
            this->size_keys++;
            if(size_keys > capacity_key){
                resizeUpKeys();
            }
        }
        // a constructor for the DLLA of values for the key
        this->key_nodes[newKeyNode].key = c;
        this->key_nodes[newKeyNode].prev_key = this->tail_key;
        this->key_nodes[newKeyNode].next_key = -1;
        this->key_nodes[newKeyNode].capacity_value = 6;
        this->key_nodes[newKeyNode].head_value = -1;
        this->key_nodes[newKeyNode].tail_value = -1;
        this->key_nodes[newKeyNode].value_nodes = new ValueNode[this->key_nodes[newKeyNode].capacity_value];
        for (int i = 0; i < key_nodes[newKeyNode].capacity_value - 1; i++) {
            key_nodes[newKeyNode].value_nodes[i].next_value = i + 1;
        }
        this->key_nodes[newKeyNode].size_values = 0;
        this->key_nodes[newKeyNode].value_nodes[this->key_nodes[newKeyNode].capacity_value - 1].next_value = -1;
        this->key_nodes[newKeyNode].first_empty_value = 0;

        // link the new key node into the DLLA
        if(this->head_key == -1){
            this->head_key = newKeyNode;
        }
        else{
            this->key_nodes[this->tail_key].next_key = newKeyNode;
        }
        this->tail_key = newKeyNode;
        currentKey = this->tail_key;
    }

    // add the new value node to the values DLLA of the key node
    TValue previousValue = this->key_nodes[currentKey].tail_value;

    int newValueNode;
    if(this->key_nodes[currentKey].first_empty_value != -1){
        newValueNode = this->key_nodes[currentKey].first_empty_value;
        this->key_nodes[currentKey].first_empty_value = key_nodes[currentKey].value_nodes[newValueNode].next_value;
        this->key_nodes[currentKey].size_values++;
    }
    else{
        newValueNode = this->key_nodes[currentKey].size_values;
        this->key_nodes[currentKey].size_values++;
        if(this->key_nodes[currentKey].size_values > this->key_nodes[currentKey].capacity_value){
            resizeUpValues(currentKey);
        }
    }
    // initialize the new value node
    this->key_nodes[currentKey].value_nodes[newValueNode].value = v;
    this->key_nodes[currentKey].value_nodes[newValueNode].prev_value = previousValue;
    this->key_nodes[currentKey].value_nodes[newValueNode].next_value = -1;

    if(previousValue == -1){
        this->key_nodes[currentKey].head_value = newValueNode;
    }
    else{
        this->key_nodes[currentKey].value_nodes[previousValue].next_value = newValueNode;
    }

    this->key_nodes[currentKey].tail_value = newValueNode;
}

// best case: theta(n), where n represents the number of keys(and the capacity of the multimap)
// average case: theta(n)
// worst case: theta(n)
//general complexity: theta(n);
void MultiMap::resizeUpKeys() {
    this->capacity_key *= 2;
    //allocate new memory
    KeyNode *newKeyNodes = new KeyNode[this->capacity_key];
    for(int i = 0; i < this->size_keys - 1; i++){
        newKeyNodes[i] = this->key_nodes[i];
    }
    //
    for(int i = this->size_keys; i < this->capacity_key - 1; i++){
        newKeyNodes[i].prev_key = i - 1;
        newKeyNodes[i].next_key = i + 1;
    }
    newKeyNodes[this->capacity_key - 1].next_key = -1;
    //deallocate dlla keys
    delete [] this->key_nodes;
    this->key_nodes = newKeyNodes;
    this->first_empty_keys = this->size_keys;
}


// best case: theta(n), where n represents the number of values(and the capacity of the DLLa key)
// average case: theta(n)
// worst case: theta(n)
//general complexity: theta(n)
void MultiMap::resizeUpValues(int currentKey) {
    this->key_nodes[currentKey].capacity_value *= 2;
    ValueNode *newValueNodes = new ValueNode[this->key_nodes[currentKey].capacity_value];
    for(int i = 0; i < this->key_nodes[currentKey].size_values - 1; i++){
        newValueNodes[i] = this->key_nodes[currentKey].value_nodes[i];
    }
    delete[] this->key_nodes[currentKey].value_nodes;
    this->key_nodes[currentKey].value_nodes = newValueNodes;
}

//best case: theta(1) Complexity; when the key and value are found at the head of the resperctive linked list
//worst case: theta(n); key and value are at the tail of their respective linked list or not present in the multimap(n - number of elems in the multimap)
//average case: theta(n); depends on the distribution of the elements in the multimap
//general complexity: O(n)
bool MultiMap::remove(TKey c, TValue v) {
    TKey currentKey = this->head_key;
    // find the key node corresponding to the given key
    while(currentKey != -1 && this->key_nodes[currentKey].key != c){
        currentKey = this->key_nodes[currentKey].next_key;
    }
    // if the key doesn't exist, there is nothing to remove
    if(currentKey == -1){
        return false;
    }
    //find the value node with the given value, starting from the head of the list
    TValue currentValue = this->key_nodes[currentKey].head_value;
    TValue previousValue = -1;
    while(currentValue != -1 && this->key_nodes[currentKey].value_nodes[currentValue].value != v){
        previousValue = currentValue;
        currentValue = this->key_nodes[currentKey].value_nodes[currentValue].next_value;
    }
    //if the value wasn't found
    if(currentValue == -1){
        return false;
    }
    //link the previous node with the next node
    if(previousValue == -1){
        this->key_nodes[currentKey].head_value = this->key_nodes[currentKey].value_nodes[currentValue].next_value;
    }
    else{
        this->key_nodes[currentKey].value_nodes[previousValue].next_value = this->key_nodes[currentKey].value_nodes[currentValue].next_value;
    }

    //link the next node with the previous node
    if(this->key_nodes[currentKey].tail_value = currentValue){
        this->key_nodes[currentKey].tail_value = previousValue;
    }

    //give a new value to the first empty slot in the values array
    this->key_nodes[currentKey].value_nodes[currentValue].next_value = this->key_nodes[currentKey].first_empty_value;
    this->key_nodes[currentKey].first_empty_value = currentValue;
    //decrease the size of the array of values for the given key
    this->key_nodes[currentKey].size_values--;

    //in case the size of values array is empty
    if(this->key_nodes[currentKey].size_values == 0){
        if(currentKey == this->head_key){
            //head key will go to the next key
            this->head_key = this->key_nodes[currentKey].next_key;
        }
        else{
            //prev.next = current.next
            this->key_nodes[this->key_nodes[currentKey].prev_key].next_key = this->key_nodes[currentKey].next_key;
        }
        //in case the currentKey is at the end of the multimap
        if(currentKey == this->tail_key){
            //tail key will come with a position in the back
            this->tail_key = this->key_nodes[currentKey].prev_key;
        }
        else{
            //next.prev = current.prev
            this->key_nodes[this->key_nodes[currentKey].next_key].prev_key = this->key_nodes[currentKey].prev_key;
        }
        //dealocate the array of values of the specific key
        delete[] this->key_nodes[currentKey].value_nodes;
        this->key_nodes[currentKey].next_key = this->first_empty_keys;
        //the space is freed
        this->first_empty_keys = currentKey;
        //decrease the size of the array of values for the given key
        this->size_keys--;
    }
    return true;
}



//best case: theta(1) complexity
//
//worst case: O(n + m) where n represents the total number of keys and m represents the number of values for the searched key
//occurs when the searched key is the last key and the second while loop will iterate through all m values associated with the given key
//general complexity: O(n + m)
vector<TValue> MultiMap::search(TKey c) const {
    if(isEmpty()){
        return vector<TValue>();
    }

    //iterate through each key until will find a specific key given as parameter
    int currentKey = this->head_key;
    while(currentKey != -1 && this->key_nodes[currentKey].key != c){
        currentKey = this->key_nodes[currentKey].next_key;
    }
    //the searched key is not in multimap
    if(currentKey == -1){
        return vector<TValue>();
    }
    vector<TValue> values;
    int currentValue = this->key_nodes[currentKey].head_value;
    while(currentValue != -1){
        values.push_back(this->key_nodes[currentKey].value_nodes[currentValue].value);
        currentValue = this->key_nodes[currentKey].value_nodes[currentValue].next_value;
    }
    return values;
}

//best case, average case, worst case: theta(m) where m represents the total number of
//values which are present in the multimap
// general complexity: theta(m) complexity
int MultiMap::size() const {
    int count = 0;
    MultiMapIterator it = this->iterator();
    while(it.valid()){
        count++;
        it.next();
    }
	return count;
}

// best case: theta(1) complexity
// average case: theta(1) complexity
// worst case: theta(1) complexity
// general complexity: theta(1) complexity
bool MultiMap::isEmpty() const {
    return this->size_keys == 0;
}


MultiMapIterator MultiMap::iterator() const {
	return MultiMapIterator(*this);
}

// best case: theta(n), where n represents the number of keys(and the capacity of the multimap)
// average case: theta(n)
// worst case: theta(n)
//general complexity: theta(n);
void MultiMap::resizeDownKeys() {

    int new_capacity = capacity_key / 2;
    if (new_capacity < 2) {
        new_capacity = 2;
    }

    KeyNode* new_key_nodes = new KeyNode[new_capacity];

    for (int i = 0; i < new_capacity; i++) {
        new_key_nodes[i].next_key = i + 1;
        new_key_nodes[i].prev_key = i - 1;
    }

    int current_key = head_key;
    int index = 0;
    while (current_key != -1 && index < new_capacity) {
        new_key_nodes[index] = key_nodes[current_key];
        new_key_nodes[index].prev_key = index - 1;
        new_key_nodes[index].next_key = index + 1;
        current_key = key_nodes[current_key].next_key;
        index++;
    }
    new_key_nodes[0].prev_key = -1;
    new_key_nodes[new_capacity - 1].next_key = -1;

    delete[] key_nodes;
    key_nodes = new_key_nodes;
    capacity_key = new_capacity;
    first_empty_keys = size_keys;
    head_key = 0;
    if (size_keys == 0) {
        tail_key = -1;
        head_key = -1;
    } else {
        tail_key = size_keys - 1;
    }
}


//best case, average, theta(n*m)
void MultiMap::print_multimap() {
    int currentKey = this->head_key;
    while(currentKey != -1){
        std::cout << this->key_nodes[currentKey].key << " - ";
        int currentValue = this->key_nodes[currentKey].head_value;
        while(currentValue != -1) {
            std::cout << this->key_nodes[currentKey].value_nodes[currentValue].value << " ";
            currentValue = this->key_nodes[currentKey].value_nodes[currentValue].next_value;
        }
        currentKey = this->key_nodes[currentKey].next_key;
        std::cout << std::endl;
    }
}

void MultiMap::resizeDownValues(int key) {

    int new_capacity = key_nodes[key].capacity_value / 2;
    if (new_capacity < 2) {
        new_capacity = 2;
    }

    ValueNode* new_value_nodes = new ValueNode[new_capacity];

    for (int i = 0; i < new_capacity; i++) {
        new_value_nodes[i].next_value = i + 1;
        new_value_nodes[i].prev_value = i - 1;
    }

    int current_value = key_nodes[key].head_value;
    int index = 0;
    while (current_value != -1 && index < new_capacity) {
        new_value_nodes[index] = key_nodes[key].value_nodes[current_value];
        new_value_nodes[index].prev_value = index - 1;
        new_value_nodes[index].next_value = index + 1;
        current_value = key_nodes[key].value_nodes[current_value].next_value;
        index++;
    }
    new_value_nodes[0].prev_value = -1;
    new_value_nodes[new_capacity - 1].next_value = -1;

    delete[] key_nodes[key].value_nodes;
    key_nodes[key].value_nodes = new_value_nodes;
    key_nodes[key].capacity_value = new_capacity;
    key_nodes[key].first_empty_value = key_nodes[key].size_values;
    key_nodes[key].head_value = 0;

    if (key_nodes[key].size_values == 0) {
        key_nodes[key].tail_value = -1;
        key_nodes[key].head_value=-1;
    } else {
        tail_key = size_keys - 1;
    }
}

void MultiMap::reverse() {
    //pre: map is a valid map
    //post: reversed keys in the multimap
    TKey left = this->head_key;
    TKey right = this->tail_key;
    while(left<right){
        int aux = this->key_nodes[left].key;
        this->key_nodes[left].key = this->key_nodes[right].key;
        this->key_nodes[right].key = aux;

        ValueNode *auxnodes = this->key_nodes[left].value_nodes;
        this->key_nodes[left].value_nodes = this->key_nodes[right].value_nodes;
        this->key_nodes[right].value_nodes = auxnodes;

        //swap value capacity
        int aux_capacity=this->key_nodes[left].capacity_value;
        this->key_nodes[left].capacity_value=this->key_nodes[right].capacity_value;
        this->key_nodes[right].capacity_value=aux_capacity;

        //swap head value
        int aux_head_value=this->key_nodes[left].head_value;
        this->key_nodes[left].head_value=this->key_nodes[right].head_value;
        this->key_nodes[right].head_value=aux_head_value;

        //swap tail value
        int aux_tail_value=this->key_nodes[left].tail_value;
        this->key_nodes[left].tail_value=this->key_nodes[right].tail_value;
        this->key_nodes[right].tail_value=aux_tail_value;
        //swap first empty
        int first_empty_aux = this->key_nodes[left].first_empty_value;
        this->key_nodes[left].first_empty_value = this->key_nodes[right].first_empty_value;
        this->key_nodes[right].first_empty_value = first_empty_aux;

        //swap size
        int size_aux = this->key_nodes[left].size_values;
        this->key_nodes[left].size_values = this->key_nodes[right].size_values;
        this->key_nodes[right].size_values = size_aux;

        if(left != right) {
            reverse_value_nodes(left);
            reverse_value_nodes(right);
        }
        left = this->key_nodes[left].next_key;
        right = this->key_nodes[right].prev_key;
    }
}

void MultiMap::reverse_value_nodes(int key) {
    //pre: map is a valid map, key is a given key
    //post: reversed values in the key array
    int left=this->key_nodes[key].head_value;
    int right=this->key_nodes[key].tail_value;

    while(left<right) {
        //swap value;
        TValue aux = this->key_nodes[key].value_nodes[left].value;
        this->key_nodes[key].value_nodes[left].value = this->key_nodes[key].value_nodes[right].value;
        this->key_nodes[key].value_nodes[right].value = aux;

        left=this->key_nodes[key].value_nodes[left].next_value;
        right=this->key_nodes[key].value_nodes[right].next_value;
    }
}


//best case: theta(n) complexity
//average case: theta(n) complexity
//worst case: theta(n) complexity
MultiMap::~MultiMap() {
    int current=this->head_key;
    while(current!=-1)
    {
        delete[] this->key_nodes[current].value_nodes;
        current = this->key_nodes[current].next_key;
    }
    delete[] key_nodes;
    key_nodes = nullptr;
    capacity_key = 0;
    size_keys = 0;
    head_key = -1;
}

/**
* function reverse(dlla) is:
    //pre: map is a valid map
    //post: reversed keys in the multimap
    TKey left <- dlla.head_key
    TKey right <- dlla.tail_key
    while left < right execute:
        aux <- dlla.key_nodes[left].key
        dlla.key_nodes[left].key = dlla.key_nodes[right].key
        dlla.key_nodes[right].key = aux

        ValueNode auxnodes <- dlla.key_nodes[left].value_nodes
        dlla.key_nodes[left].value_nodes <- dlla.key_nodes[right].value_nodes
        dlla.key_nodes[right].value_nodes = auxnodes;

        //swap value capacity
        aux_capacity=this->key_nodes[left].capacity_value
        dlla.key_nodes[left].capacity_value <- dlla.key_nodes[right].capacity_value;
        dlla.key_nodes[right].capacity_value <- aux_capacity

        //swap head value
        aux_head_value <- dlla.key_nodes[left].head_value
        dlla.key_nodes[left].head_value <- dlla.key_nodes[right].head_value
        dlla.key_nodes[right].head_value <- aux_head_value;

        //swap tail value
        aux_tail_value <- dlla.key_nodes[left].tail_value;
        dlla.key_nodes[left].tail_value <- dlla.key_nodes[right].tail_value;
        dlla.key_nodes[right].tail_value <- aux_tail_value;
        //swap first empty
        first_empty_aux = dlla.key_nodes[left].first_empty_value;
        dlla.key_nodes[left].first_empty_value <- this->key_nodes[right].first_empty_value;
        dlla.key_nodes[right].first_empty_value <- first_empty_aux;

        //swap size
        size_aux <- dlla.key_nodes[left].size_values;
        dlla.key_nodes[left].size_values <- dlla.key_nodes[right].size_values;
        this->key_nodes[right].size_values <- size_aux;

        left <- dlla.key_nodes[left].next_key;
        right <- dlla.key_nodes[right].prev_key;
*/


/**
* function reverse_value_nodes(dlla_map, dlla_key, key) is:
    //pre: map is a valid map, key is a given key
    //post: reversed values in the key array
    left <- dlla.key_nodes[key].head_value
    right <- dlla.key_nodes[key].tail_value

    while left<right execute:
        //swap value;
        aux <- dlla_map.key_nodes[key].dlla_key.value_nodes[left].value
        dlla_map.key_nodes[key].dlla_key.value_nodes[left].value <- dlla_map.key_nodes[key].dlla_key.value_nodes[right].value
        dlla_map.key_nodes[key].dlla_key.value_nodes[right].value <- aux

        //iterate through key array
        left <- dlla_map.key_nodes[key].dlla_key.value_nodes[left].next_value
        right <- dlla_map.key_nodes[key].dlla_key.value_nodes[right].next_value

*/