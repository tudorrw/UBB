def check_prime(a):
    for i in range (2, a // 2 + 1):
        if not a % i:
            return 0
    return 1
def Differenz(l):
    length, maxlength, idx = 1, 1, -1
    for i in range(len(l)-1):
        n = abs(l[i] - l[i+1])
        if check_prime(n):
            length += 1
            if length == 2: idx = i
            if maxlength < length: maxlength, idxfin = length, idx
        else : 
            length = 1
    print(l[idxfin: idxfin + maxlength])
l = [12, 9, 67, 64, 53, 40, 23, 7]
Differenz(l)
