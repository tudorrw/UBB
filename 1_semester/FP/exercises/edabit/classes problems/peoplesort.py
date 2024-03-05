class Person:
    def __init__(self, firstname, lastname, age):
        self.firstname = firstname
        self.lastname = lastname
        self.age = age

    def __repr__(self):
        return f'{self.firstname} {self.lastname} {str(self.age)}' 


def people_sort(list, atributte):
    if atributte == 'firstname':
        return sorted(list, key = lambda x: x.firstname)
    elif atributte == 'lastname':
        return sorted(list, key = lambda x: x.lastname) 
    else :
        return sorted(list, key = lambda x: x.age)

def main():
    p1 = Person("Michael", "Smith", 40)
    p2 = Person("Alice", "Waters", 21)
    p3 = Person("Zoey", "Jones", 29)
    list = [p1, p2, p3]
    print(list)
    atributte = input()
    print(people_sort(list, atributte))
    
main()


