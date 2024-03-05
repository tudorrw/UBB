from V_10_op import Operation

# def test_add():
#     i = 1, 2
#     o = 3
#     assert o == Operation.add(i[0], i[1])
#
# def test_sub():
#     i = 1, 2
#     o = -1
#     assert o == Operation.sub(i[0], i[1])
#
# test_add()

import unittest

class TestOper(unittest.TestCase):
    def test_add(self):
        self.assertEqual(3, Operation.add(1, 2))

    def test_sub(self):
        self.assertEqual(-1, Operation.sub(1,2))

if __name__ == '__main__':
    unittest.main()


