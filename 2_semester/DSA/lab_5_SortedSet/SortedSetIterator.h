#pragma once
#include "SortedSet.h"
//DO NOT CHANGE THIS PART

struct StackNode{ //SLLNode for the stack container
    int elem;
    StackNode *next;
};

class SortedSetIterator
{
	friend class SortedSet;
private:
	const SortedSet& multime;
	SortedSetIterator(const SortedSet& m);
    //in order traversal
    int currentNode;
    //using a stack
    StackNode *stackHead;

    void pushToStack(TComp elem);

    void popFromStack();

public:

	void first();
	void previous();
    void next();
	TElem getCurrent();
	bool valid() const;
};

