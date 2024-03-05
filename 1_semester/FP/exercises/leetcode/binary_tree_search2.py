class TreeNode:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None
    
    def subtree(self, value):
        if value < self.value:
            if self.left:
                self.left.subtree(value)
            else:
                self.left = TreeNode(value)
        elif value > self.value:
            if self.right:
                self.right.subtree(value)
            else:
                self.right = TreeNode(value)
        else:
            return 


    def in_order_traversal(self):
        inorder_tree = []
        if self.left:
            inorder_tree += self.left.in_order_traversal()

        inorder_tree.append(self.value)

        if self.right:
            inorder_tree += self.right.in_order_traversal()

        return inorder_tree


    def pre_order_traversal(self):
        elements = []
        elements.append(self.value)
        if self.left:
            elements += self.left.pre_order_traversal()
        
        if self.right:
            elements += self.right.pre_order_traversal()

        return elements


    def post_order_traversal(self):
        elements = []
        if self.left:
            elements += self.left.post_order_traversal()
        
        if self.right:
            elements += self.right.post_order_traversal()
        elements.append(self.value)

        return elements



def built_Tree(elements):
    root = TreeNode(elements[0])

    for i in range(1, len(elements)):
        root.subtree(elements[i])
    
    return root



def main():
    root = [14, 8, 6, 7, 10, 9, 4, 20, 21, 6, 2, 18, 16]
    new_root = built_Tree(root)
    print(new_root.in_order_traversal())
    print(new_root.pre_order_traversal())
    print(new_root.post_order_traversal())

main()