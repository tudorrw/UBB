#include "Matrix.h"
#include "MatrixIterator.h"
#include <exception>
#include <iostream>

using namespace std;

//best case: theta(1)
//average case: theta(1)
//worst case: theta(1)
//general complexity: theta(1)
Matrix::Matrix(int nrLines, int nrCols) {
    this->rows = nrLines;
    this->columns = nrCols;
    this->hash_capacity = 5;
    this->hash_size = 0;
    this->hashTable = new Tupel[this->hash_capacity];
    initializeHashTable();
}


//best case: theta(1)
//average case: theta(1)
//worst case: theta(1)
//general complexity: theta(1)
int Matrix::hashFunction1(int row, int col) const {
    return (row + col) % this->hash_capacity;
}

//best case: theta(1)
//average case: theta(1)
//worst case: theta(1)
//general complexity: theta(1)
int Matrix::hashFunction2(int row, int col) const {
    return 1 + (row + col) % (this->hash_capacity - 1);
}

//best case: theta(1)
//average case: theta(1)
//worst case: theta(1)
//general complexity: theta(1)
int Matrix::doubleHashing(int i, int j, int attempt) const{
    int h1 = hashFunction1(i, j);
    int h2 = hashFunction2(i, j);
    return (h1 + attempt * h2) % this->hash_capacity;
}

//best case: theta(1) - if the number respects one of the first two if statements
//average case: theta(sqrt(n)) but approximately (sqrt(n) - 3) / 2, cause it increments d by 2 in each iteration
//worst case: theta(sqrt(n))
//general complexity: O(sqrt(n))
bool Matrix::prime(int number) {
    //condition for the first prime number
    if(number == 2){
        return true;
    }
    //if the number is even or below 2 return false
    if(number % 2 == 0 || number < 2){
        return false;
    }
    //main for loop to check if the number is prime or not
    for(int d = 3; d * d <= number; d += 2){
        if(number % d == 0){
            return false;
        }
    }
    return true;

}
// - n is number, m the number of non prime numbers between the n and first prime number greater than n
//best case: theta(1) - if the first checked number is prime
//average case: theta(sqrt(n))
//worst case: theta(m * sqrt(n))
//general complexity: O(m * sqrt(n))
int Matrix::firstPrime(int number) {
    number++;
    while(!prime(number)){ //checks is a number is prime and stops if it's prime, if not, it goes to the next number
        number++;
    }
    return number; //returns the first prime number
}

//best case: theta(n)
//average case: theta(n)
//worst case: theta(n)
//general complexity: theta(n) - n means hash capacity
void Matrix::initializeHashTable() {
    for(int i = 0; i < this->hash_capacity; i++){
        this->hashTable[i] = Tupel{-1, -1, NULL_TELEM}; //default values for the attributes of tupels
    }
}
// n - old capacity
// best case: theta(n)
// average case: theta(n)
// worst case: theta(n) //it copies the elements form the old table to the new table by iterating over the old table
// general complexity: theta(n)
void Matrix::resizeUp(){
    int old_capacity = this->hash_capacity;
    // The new hash table capacity is computed by doubling the current capacity and finding the next prime number.
    this->hash_capacity = firstPrime(this->hash_capacity * 2);
    //copy the previous hash table in a temporary one
    Tupel *temp = this->hashTable;
    //initialise a new hash table where we will resize and reallocate the elements using the double hashing method
    this->hashTable = new Tupel[this->hash_capacity];
    initializeHashTable();
    this->hash_size = 0;

    //take elements from the original hash table and allocate them in the new resized hash table
    for(int i = 0; i < old_capacity; i++){
        if(temp[i].value != NULL_TELEM){
            int row = temp[i].row;
            int col = temp[i].col;
            TElem value = temp[i].value;
            //we are using the modify function by adding the elements
            modify(row, col, value);
        }
    }
    //deallocate the temporary hash table to free the memory
    delete[] temp;
}

// n - hash capacity
// best case: theta(1) occurs when the row i and column j are out of range or the element being accessed
// is found at the initial hash position, the first attempt
//average case: theta(n) : the element being accessed is either found within the probing sequence or not found in the hash table
//worst case: theta(n): elements being accessed doesn't exist in the hash table and athe probing sequence exhausts all slots
//general complexity: O(n)
TElem Matrix::element(int i, int j) const {
    //throws exception if line i or column j are out of matrix range
	if(i < 0 || i >= this->rows || j < 0 || j >= this->columns) {
        throw std::exception();
    }

    int attempt = 0;
    while(attempt < this->hash_capacity){
        int hash = doubleHashing(i, j, attempt);
        //the row and column of the tuple on the 'hash' index correspond to the given parameters
        if(this->hashTable[hash].row == i && this->hashTable[hash].col == j){
            return this->hashTable[hash].value; //the value of the tuple on position hash is returned
        }
        else if(this->hashTable[hash].row == -1)
            break; // Found an empty slot, element is not present in the matrix
        attempt++;  //increment the attempt variable to contiune the probing sequence
    }
    // If the element is not found, it returns NULL_TELEM.
	return NULL_TELEM;
}

// n - hash capacity
// best case: theta(1) occurs when the row i and column j are out of range or the element being accessed
// is found at the initial hash position, the first attempt; the tupel is updated or marked as deleted
//average case: theta(n), the element being modified is either found within the porbing sequnece or not found in the hash table
//worst case: theta(n + m) m - resizing call operation
//general complexity: O(n + m)
TElem Matrix::modify(int i, int j, TElem e) {
    //throws exception if line i or column j are out of matrix range
    if (i < 0 || i >= rows || j < 0 || j >= columns)
        throw std::out_of_range("Invalid indices!");

    int attempt = 0;
    while (attempt < hash_capacity) {
        int hash = doubleHashing(i, j, attempt);
        //the element with hash index was found
        if (hashTable[hash].row == i && hashTable[hash].col == j) {
            TElem old_value = hashTable[hash].value;
            hashTable[hash].value = e; // change the value of the element
            return old_value; // returns the old value of the element
    // If the element is not present at that position, it is added to the matrix.
        } else if (hashTable[hash].row == -1) {
            hashTable[hash] = Tupel{i, j, e};
            hash_size++;

            // if the occupancy factor exceeds the threshold, the hash table is resized
            if (static_cast<double>(this->hash_size) / this->hash_capacity > 0.75)
                resizeUp(); //

            return NULL_TELEM;
        }
        attempt++;
    }

    // If the loop finishes without finding an empty slot, resize the hash table
    resizeUp();

    // Call modify again to insert the element in the resized hash table, rehash
    return modify(i, j, e);
}

//best case: theta(1)
//average case: theta(1)
//worst case: theta(1)
//general complexity: theta(1)
int Matrix::nrLines() const {
    return this->rows;
}

//best case: theta(1)
//average case: theta(1)
//worst case: theta(1)
//general complexity: theta(1)
int Matrix::nrColumns() const {
    return this->columns;
}

//best case: theta(1)
//average case: theta(1)
//worst case: theta(1)
//general complexity: theta(1)
Matrix::~Matrix() {
	delete[] this->hashTable;
}

MatrixIterator Matrix::iterator() const {
    return MatrixIterator(*this);
}


