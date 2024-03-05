class Employee:
    def __init__(self, firstname, lastname):
        self.firstname = firstname
        self.lastname = lastname

        self.fullname = f'{self.firstname} {self.lastname}'
        self.email = f'{self.firstname.lower()}.{self.lastname.lower()}@company.com'

    
def main_emp():
    emp_1 = Employee("John", "Smith")
    emp_2 = Employee("Mary",  "Sue")
    emp_3 = Employee("Antony", "Walker")
    print(emp_1.fullname)
    print(emp_2.email)
    print(emp_3.firstname)
# main_emp()

class ones_threes_nines():
    def __init__(self, number):
        self.number = number
        self.ones = number // 1
        self.threes = number // 3
        self.nines= number // 9
        
def main_otn():
    n1 = ones_threes_nines(5)
    print(n1.nines)
main_otn()