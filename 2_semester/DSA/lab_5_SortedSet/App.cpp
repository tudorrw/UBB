#include "ShortTest.h"
#include "ExtendedTest.h"
#include "SortedSet.h"
#include "SortedSetIterator.h"
#include <iostream>

using namespace std;

int main() {
	testAll();
    test_iterator_previous();
    testAllExtended();

	cout << "Test end" << endl;
	system("pause");
}