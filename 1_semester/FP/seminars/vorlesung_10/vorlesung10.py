from operator import attrgetter

class Student:
    def __init__(self, name, age):
        self.name = name
        self.age = age
    
    def study(self):
        print(f'{self.name} is studying')
    
    def __eq__(self, other):
        return self.name == other.name and self.age == other.age
    
    def __lt__(self, other):
        return self.age < other.age
    
    def __add__(self, other):
        return Student(self.name, self.age + other)

    def __repr__(self):
        return f'Student(name = {self.name}, age = {self.age})'

def main():

    bob = Student('bob', 20)
    print(bob + 10)
    print(bob > bob + 10)

    students = [bob + 5, bob, bob + 3, bob + 2]
    
    students.sort(key = attrgetter('age'), reverse = True)
    # students.sort(key = lambda student: student.name, reverse = True)

    print(students)

main()