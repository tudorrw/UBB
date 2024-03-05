#include <exception>
#include "MatrixIterator.h"
#include "Matrix.h"

using namespace std;


MatrixIterator::MatrixIterator(const Matrix& m): matrix(m), currentColumn(0), currentRow(0) {}

void MatrixIterator::first() {
    this->currentColumn = 0;
    this->currentRow = 0;
}


void MatrixIterator::next() {
    if(!valid()){
        throw std::exception();
    }
    currentRow++;
    if(currentRow >= matrix.nrLines()){
        currentRow = 0;
        currentColumn++;
    }
}


bool MatrixIterator::valid() const {
    return currentColumn < matrix.nrColumns();
}



TElem MatrixIterator::getCurrent() const{
    if(!valid()){
        throw std::exception();
    }
    return matrix.element(currentRow, currentColumn);

}
