def print_matrix(matrix):
    n = len(matrix)
    i = j = p = 0
    while i < n and j < n:
        while j < n:
            print(matrix[i][j], end = ' ')
            j += 1
        i += 1
        j = p 
        while i < n:
            print(matrix[i][j], end = ' ')
            i += 1
        p += 1 
        i = j = p

class LargeNumber:
    def __init__(self, string):
        self.lst = list(map(int, string))
    
    def __repr__(self):
        return f'Number: {self.lst}'

    def __lt__(self, other):
        if len(self.lst) < len(other.lst):
            return True
        elif len(self.lst) > len(other.lst):
            return False
        else:
            for i in range(len(self.lst)):
                if self.lst[i] > other.lst[i]:
                    return False
                if self.lst[i] < other.lst[i]:
                    return True
        
class NumberBox:
    def __init__(self, number_lst):
        self.number_lst = number_lst
    
    def add_number(self, numbr):
        self.number_lst.append(numbr)

    def get_max(self):
        return max(self.number_lst)



if __name__ == '__main__':
    matrix = [[1, 2, 3],
              [4, 5, 6],
              [7, 8, 9]]
    print_matrix(matrix)

    ln1 = LargeNumber('4552446')
    ln2 = LargeNumber('9746235')
    ln3 = LargeNumber('58473')
    nb = NumberBox([])
    nb.add_number(ln1)
    nb.add_number(ln2)
    nb.add_number(ln3)
    print(nb.get_max())
    print(nb)

    

