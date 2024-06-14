#pragma once

#include "SortedMultiMap.h"
struct StackNode {
    int elem;
    StackNode *next;
};

class SMMIterator{
	friend class SortedMultiMap;
private:
	//DO NOT CHANGE THIS PART
	const SortedMultiMap& map;
	SMMIterator(const SortedMultiMap& map);

    int currentNode;
    int currentValueIndex;
    StackNode *stackHead;

    void pushToStack(int elem);
    void popFromStack();

public:
	void first();
	void next();
	bool valid() const;
   	TElem getCurrent() const;
};

