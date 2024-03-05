import math
class Solution:
    def isPowerOfFour(self, n):
        if not n: return False
        if n == 1: return True
        if n % 4 == 0: return self.isPowOfFour(n / 4)
        return False

    def isPowerOfFour2(self, n):
        return math.log(n, 4) == int(math.log(n, 4)) if n > 0 else False
    
    def reverse_list(self, lst):
        if not lst:
            return []
        return [lst[-1]] + self.reverse_list(lst[:-1])

    def countGoodNumbers(self, n):
        if not n - 1:
            return 5
        if (n - 1) % 2:
            return (4 * self.countGoodNumbers(n - 1))%(10**9 + 7) 
        else :
            return (5 * self.countGoodNumbers(n - 1))%(10**9 + 7) 

    def countGoodNumbers2(self, n):
        mod = 10 ** 9 + 7
        return (pow(4, n // 2, mod) * pow(5, n//2 + n%2, mod))% mod

    def lastRemaining(self, n):
        return 
def main():
    a = Solution()
    print(a.isPowerOfFour(15))
    print(a.isPowerOfFour2(15))
    print(a.reverse_list([2, 3, 4]))
    print(a.countGoodNumbers(5))
    print(a.countGoodNumbers2(5))
main()
        
