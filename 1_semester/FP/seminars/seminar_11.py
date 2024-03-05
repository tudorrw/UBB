# interclasare
def insert(left, right):
    l = []
    i = j = 0
    while i <len(left) and j < len(right):
        if left[i] < right[j]:
            l.append(left[i])
            i += 1
        else:
            l.append(right[j])
            j += 1
    
    l += right[j:]
    l += left[i:]
    return l

def sort10(l):
    if len(l) < 1:
        return l
    if not l[0]:
        return [0] + sort10(l[1:])
    return sort10(l[1:]) + [1]

def find(l, s, e): # list, start, end
    if s > e:
        return e + 1
    if s != l[s]:
        return s
    mid = (s + e)//2
    if mid == l[mid]:
        return find(l, mid + 1, e)
    return find(l, s, mid)

def triple(l, ts):
    for i, a in enumerate(l):
        j = i + 1
        k = len(l) - 1
        while j < k:
            s = a + l[j] + l[k]
            if s == ts:
                return a, l[j], l[k]
            else:
                if s > ts:
                    k -= 1
                else:
                    j += 1
    return None
    
def anzahl1(lst):
    if lst[0] != 1 and lst[-1] != 1:
        return anzahl1(lst[1:-1])
    if lst[0] != 1:
        return anzahl1(lst[1:])
    if lst[-1] != 1:
        return anzahl1(lst[:-1])
    return len(lst)
    
def letzte_vorkommen(lst,elem):
    if elem not in lst:
        return None
    if lst[-1] == elem:
        return len(lst)
    return letzte_vorkommen(lst[:-1], elem)
    
def get_column(i, n, m):
    if len(m) != n * n:
        raise ValueError("m does not represent an NxN matrix")
    return list(map(str, m[i:len(m):n]))



def main():
    print(insert([1, 3, 4, 8, 9], [2, 5, 7]))
    print(sort10([1, 0, 0, 1, 1]))
    lst = [1, 2, 3, 4]
    print(find(lst, 0, len(lst) - 1))
    print(triple([1, 2, 4, 7, 9], 10))
    print(anzahl1([0, 0, 1, 1, 1, 1, 3, 4, 5, 7]))
    matrix = "566ggb8ac.x6q[o*"
    print(len(matrix))
    print(get_column(2, 4, matrix))
    print(letzte_vorkommen([1, 3, 4, 5, 4 ,3, 5], 3))
main()