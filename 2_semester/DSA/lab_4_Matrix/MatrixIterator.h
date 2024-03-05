#include "Matrix.h"

class MatrixIterator
{
	//DO NOT CHANGE THIS PART
	friend class Matrix;
	
private:
	const Matrix& matrix;
    int currentRow = 0;
    int currentColumn = 0;
	MatrixIterator(const Matrix& m);
public:
	void first();
	void next();
	TElem getCurrent() const;
	bool valid() const;
};
