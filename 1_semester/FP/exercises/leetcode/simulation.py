class Solution(object):
    def diStringMatch(self, s):
        perm = [0]
        left = right = 0
        for i in s:
            if i == 'I':
                right += 1
                perm.append(right)
            else:
                left -= 1
                perm.append(left)
            
        return [i]       

s = "DDI"
a = Solution()
print(a.diStringMatch(s))