#include <assert.h>
#include <exception>

#include "ShortTest.h"
#include "IndexedList.h"
#include "ListIterator.h"

void testAll() {
    IndexedList list = IndexedList();
    assert(list.isEmpty());
    list.addToEnd(1);
    assert(list.size() == 1);
    assert(list.size() == 1);
    list.addToPosition(0,2);
    assert(list.size() == 2);
    ListIterator it = list.iterator();
    assert(it.valid());
    it.next();
    assert(it.getCurrent() == 1);
    it.first();
    assert(it.getCurrent() == 2);
    assert(list.search(1) == 1);
    assert(list.setElement(1,3) == 1);
    assert(list.getElement(1) == 3);
    assert(list.remove(0) == 2);
    assert(list.size() == 1);

    IndexedList list1 = IndexedList();
    list1.addToEnd(4);
    list1.addToPosition(0, 5);
    list1.addToEnd(8);
    list1.addToPosition(1, 9);
    list1.addToEnd(4);
    list1.addToPosition(0, 7);
    list1.addToEnd(8);
    list1.addToPosition(4,10);
    assert(list1.size() == 8);
    list1.display();
    list1.removeFromKToKPosition(3);
    assert(list1.size() == 6);
    list1.display();
}
