class Solution(object):
    def truncateSentence(self, s, k):
        liste = s.split(' ')
        return ' '.join(liste[0:k])
s = "Hello how are you Contestant"
k = 4
a = Solution()
print(a.truncateSentence(s, k))