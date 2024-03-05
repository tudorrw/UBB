class Student:
    def __init__(self, fname, lname, age):
        self.__fname = fname
        self.lname = lname
        self.age  = age
    
    @property
    def email(self):
        return '{}{}@studubb.com'.format(self.lname, self.__fname).lower()


    def __eq__(self, other):
        # try:
        #     return self.age == other.age
        # except AttributeError:
        #     if isinstance(other, Student):
        #         return self.age == other.age
        #     return False
        if isinstance(other, Student):
            return self.age == other.age
        return False

    def __repr__(self):
        return 'Student {} {} has {} years old.'.format(self.__fname, self.lname, self.age)
    
    

def main():

    student1 = Student('Rico', 'Vasquez', 23)
    student2 = Student('Stacey', 'Johnson', 19)
    
    list_of_stundents = [student1, student2]
    print(list_of_stundents)
    
    #with __eq__
    print(student1 == student2)
    print(student2 == 19)
    
    #property of students: getters
    student1._Student__fname = 'Xavi' #wenn man eine private Atributt verandern
    print(student1.email)

main()