class Solution(object):
    def sortPeople(self, names, heights):

        d = dict(zip(names, heights))
        sorted_d = {}
        sorted_keys = sorted(d, key=d.get, reverse=True)
        for w in sorted_keys:
            sorted_d[w] = d[w]
        return list(sorted_d.keys())

a = Solution()
names = ["Mary","John","Emma"]
heights = [180,165,170]
print(a.sortPeople(names, heights))