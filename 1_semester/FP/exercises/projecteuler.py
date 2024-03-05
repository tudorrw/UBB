from math import sqrt, log
def prime(a):
    if not a or a == 1: return False
    for d in range(2, (int)(sqrt(a)) + 1):
        if not a % d:
            return False
    return True

def prime_10001(n):
    number, i = 3, 1
    while(i < n):
        if prime(number):
            i += 1
        number += 2
    return number - 2


def smallest_multiple(n):
    res = 1
    for i in range(1, n):
        if prime(i):
            res *= i ** (int)(log(n, i))
    return res

def smallest_multiple2(n, i):
    if i == 1:
        return 1
    return i ** (int)(log(n, i)) * smallest_multiple2(n, i - 1) if prime(i) else smallest_multiple2(n, i - 1)


def self_power(n):
    res = 0
    for i in range(1, n + 1):
        res = (res + i ** i) % 10000000000
    return res



print(prime(9))
print(smallest_multiple(20))
print(smallest_multiple2(10, 10))
print(self_power(1000))
print(prime_10001(10001))