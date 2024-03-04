#pragma once
#include "IndexedList.h"


class ListIterator{
    //DO NOT CHANGE THIS PART
	friend class IndexedList;
private:
    //TODO - Representation
	const IndexedList& list;
    Node *currentElement;
    ListIterator(const IndexedList& list);

public:

    /**
     * pre: currentElement ist ein SLLiterator
     * post: currentElement verweist auf das Listenkopf der IndexedList
     */
    void first();

    /**
     * pre: currentElement ist ein SLLIterator
     * post: currentElement ist ein SLLIterator, das aktuelle Element aus currentElement verweist
     * auf das nächste Element
     * throws: Exception falls currentElement nicht gültig ist
     */
    void next();

    /**
     * pre: currentElement is ein SLLIterator
     * post: wahr falls currentElement gültig ist, falsch ansonsten
     */
    bool valid() const;

    /**
     * pre: currentElement ist ein SLLIterator
     * post: e ist ein TElem, e ist das aktuelle Element aus currentElement
     * throws: Exception falls currentElement nicht gültig ist
     */
    TElem getCurrent() const;

};

