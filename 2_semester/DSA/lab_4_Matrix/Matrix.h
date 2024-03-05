#pragma once

//DO NOT CHANGE THIS PART
typedef int TElem;
#define NULL_TELEM 0
#include <utility>

class MatrixIterator;
struct Tupel{
    int row, col;
    TElem value;
};

class Matrix {

private:
    Tupel *hashTable;
    int hash_size;
    int hash_capacity;
    int rows, columns;

    //initializing the hash table giving standard values to all tupels
    void initializeHashTable();

    //first hash function
    int hashFunction1(int row, int col) const;

    //second hash function
    int hashFunction2(int row, int col) const;

    //double hashing is used as an index in the hash table to store, find, remove and update the elements
    //attempt variable is multiplied with the second hash function and continues the probing sequence
    int doubleHashing(int i, int j, int attempt) const;

    // checks if a number is prime or not
    bool prime(int number);

    // It is used to find the next prime number when resizing the hash table.
    // Prime numbers are used in resizing to ensure a good distribution of elements and reduce collisions.
    int firstPrime(int number);

    //resizes the has table to hold more elements and also to avoid collisions
    void resizeUp();



    friend class MatrixIterator;
public:
	//constructor
	Matrix(int nrLines, int nrCols);

	//returns the number of lines
	int nrLines() const;

	//returns the number of columns
	int nrColumns() const;

	//returns the element from line i and column j (indexing starts from 0)
	//throws exception if (i,j) is not a valid position in the Matrix
	TElem element(int i, int j) const;

	//modifies the value from line i and column j
	//returns the previous value from the position
	//throws exception if (i,j) is not a valid position in the Matrix
	TElem modify(int i, int j, TElem e);

    // destructor
	~Matrix();

    MatrixIterator iterator() const;
};
