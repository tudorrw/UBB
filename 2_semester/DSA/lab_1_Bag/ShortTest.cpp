#include "ShortTest.h"
#include <assert.h>
#include "Bag.h"
#include "BagIterator.h"
#include <iostream>

void testAll() { 
	Bag b;
	assert(b.isEmpty() == true);
	assert(b.size() == 0); 
	b.add(5);
	b.add(1);
	b.add(10);
	b.add(7);
	b.add(1);
	b.add(11);
	b.add(-3);
	assert(b.size() == 7);
	assert(b.search(10) == true);
	assert(b.search(16) == false);
	assert(b.nrOccurrences(1) == 2);
	assert(b.nrOccurrences(7) == 1);
	assert(b.remove(1) == true);
	assert(b.remove(6) == false);
    assert(b.size() == 6);
	assert(b.nrOccurrences(1) == 1);
    BagIterator it = b.iterator();
    it.first();
    while (it.valid()) {
        TElem e = it.getCurrent();
        it.next();
    }

    Bag b2;
    b2.add(6);
    b2.add(11);
    b2.add(3);
    b2.add(6);
    b2.add(1);
    b2.add(-3);
    b2.add(-3);
    b2.add(32);
    b2.add(-5);


    Bag reuniteBag = b.reunion(b2);
    assert(reuniteBag.size() == 15);
    b.printBag();
    b2.printBag();
    reuniteBag.printBag();




}
