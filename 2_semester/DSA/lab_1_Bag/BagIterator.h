#include "Bag.h"

class BagIterator
{
	//DO NOT CHANGE THIS PART
	friend class Bag;
	
private:
	const Bag& bag;
	BagIterator(const Bag& c);
    int currentIndex;
    int currentFreq;

public:
	void first();
	void next();
	TElem getCurrent() const;
	bool valid() const;
};
