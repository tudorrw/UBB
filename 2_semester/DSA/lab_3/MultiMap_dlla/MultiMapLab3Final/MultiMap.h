#pragma once
#include<vector>
#include<utility>
//DO NOT INCLUDE MultiMapIterator

using namespace std;

//DO NOT CHANGE THIS PART
typedef int TKey;
typedef int TValue;
typedef std::pair<TKey, TValue> TElem;
#define NULL_TVALUE -111111
#define NULL_TELEM pair<int,int>(-111111, -111111)

//ein Knoten enthält die Daten und die Links zu dem vorigen und nächsten Knoten

struct ValueNode {
//value, previous and next
    TValue value;
    int prev_value;
    int next_value;
};

struct KeyNode {
//key, previous and next
    TKey key;
    int next_key;
    int prev_key;

//attributes for values DLLA
    int capacity_value;
    int head_value;
    int tail_value;
    ValueNode* value_nodes;
    int first_empty_value;
    int size_values;
};
class MultiMapIterator;

class MultiMap
{
	friend class MultiMapIterator;

private:

    //attributes for keys DLLA
    int head_key;
    int tail_key;
    int capacity_key;
    KeyNode* key_nodes;
    int first_empty_keys;
    int size_keys;
public:
	//constructor
	MultiMap();

	//adds a key value pair to the multimap
	void add(TKey c, TValue v);

	//removes a key value pair from the multimap
	//returns true if the pair was removed (if it was in the multimap) and false otherwise
	bool remove(TKey c, TValue v);

	//returns the vector of values associated to a key. If the key is not in the MultiMap, the vector is empty
	vector<TValue> search(TKey c) const;

	//returns the number of pairs from the multimap
	int size() const;

	//checks whether the multimap is empty
	bool isEmpty() const;

	//returns an iterator for the multimap
	MultiMapIterator iterator() const;

    void resizeUpKeys();

    void resizeUpValues(int currentKey);

    void print_multimap();
	//destructor
	~MultiMap();

    void resize_down_values(int key);

    void resize_down_keys();

    void reverse();


    void reverse_value_nodes(int key);
};

