class TreeNode:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None
    
    def insert(self, value):
        if value < self.value:
            if self.left is None:
                self.left = TreeNode(value)
            else:
                self.left.insert(value)
        else:
            if self.right is None:
                self.right = TreeNode(value)
            else:
                self.right.insert(value)

    def in_order_traversal(self):
        if self.left:
            self.left.in_order_traversal()
        print(self.value, end = ' ')
        if self.right:
            self.right.in_order_traversal()

    def pre_order_traversal(self):
        print(self.value, end = ' ')
        if self.left:
            self.left.pre_order_traversal()
        if self.right:
            self.right.pre_order_traversal()

    def post_order_traversal(self):
        if self.left:
            self.left.post_order_traversal()
        if self.right:
            self.right.post_order_traversal()
        print(self.value, end = ' ')
    
    def find(self, value):
        if value < self.value:
            if self.left is None:
                return False
            else:
                return self.left.find(value)
        elif value > self.value:
            if self.right is None:
                return False
            else:
                return self.right.find(value)
        else:
            return True

tree = TreeNode(14)
tree.insert(8)
tree.insert(6)
tree.insert(7)
tree.insert(10)
tree.insert(9)
tree.insert(4)
tree.insert(20)
tree.insert(21)
tree.insert(6)
tree.insert(2)
tree.insert(18)
tree.insert(16)
tree.in_order_traversal()
print('\n')
tree.pre_order_traversal()
print('\n')
tree.post_order_traversal()
print('\n')
print(tree.find(1))
