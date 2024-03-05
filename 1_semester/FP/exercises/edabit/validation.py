class Validation:
    def __init__(self, term1, term2 = None):
        self.term1 = term1
        self.term2 = term2

    def lines_are_parallel(self):
        try:
            try:
                if len(self.term1) == 3 and len(self.term2) == 3:
                    if self.term1 == self.term2 or (-self.term1[0] / self.term1[1] == -self.term2[0] / self.term2[1]):
                        return "Lines are parallel"
                    else:
                        return "Lines are not parallel"
                else:
                    return "lists of the wrong size"
            except ZeroDivisionError:
                return "b cannot have the value 0"
        except TypeError:
            return "Elements must be integers"


    def face_interval(self):
        try:
            interval = max(self.term1) - min(self.term1)
            if interval in self.term1:
                return ":)"
            else:
                return ":("
        except TypeError:
            return ":/"


if __name__ == "__main__":
    first = Validation([0, 1, 5], [0, 1, 5])
    print(first.lines_are_parallel())

    second = Validation([5, 2, 8, 4, 9, 11])
    print(second.face_interval())