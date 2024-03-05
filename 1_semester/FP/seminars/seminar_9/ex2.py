class Student:
    def __init__(self, name):
        object.__setattr__(self, 'name', name)

    def __setattr_(self, name, value):
        raise ValueError()
    
    def __delattr__(self, name):
        raise ValueError()


def main():
    bob = Student('bob')
    print(bob.name)
    bob.name = 'dob'

main()