class TreeNode:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None

class Solution(TreeNode):


    def inorderTraversal(self, root):
        if not root:
            return []
        else:
            return self.inorderTraversal(root.left) + [root.value] + self.inorderTraversal(root.right)
    
def main():
    n1 = TreeNode(1)
    n2 = TreeNode(2)
    n3 = TreeNode(3)

    n1.right = n2
    n2.left = n3
    a = Solution().inorderTraversal(n1)
    print(a)
main()

