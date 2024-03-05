class Ball:
    def __init__(self, radius):
        self.farbe = 'rot'
        self.radius = radius

    @property
    def color(self):
        return self.farbe
    
    @color.setter
    def color(self, x):
        self.color = x
    
    def __repr__(self):
        return f'Ball(farbe:{self.farbe}, radius = {self.radius})'

from operator import attrgetter
class Stack:
    def __init__(self, balls):
        self.balls = balls
    
    def pop(self):
        letzten_ball = self.balls[-1]
        self.balls = self.balls[:-1]
        return letzten_ball
    
    def push(self, ball):
        self.balls.append(ball)
    
    def __add__(self, other):
       added_list = self.balls + other.balls
       added_list.sort(key = attrgetter('farbe'), reverse = True)
       return Stack(added_list)

    def __str__(self):
        return f'Stack({self.balls})'



def main():
    ball1 = Ball(20)
    ball2 = Ball(30)
    ball1.farbe = 'gelb'
    ball3 = Ball(10)
    ball3.farbe = 'schwarz'
    ball4 = Ball(25)
    ball4.farbe = 'blau'
    stapel1 = Stack([ball1, ball2])
    stapel2 = Stack([ball3])
    stapel2.push(ball4)
    stapel3 = stapel1 + stapel2
    print(stapel3)
    print(stapel3.pop())
    print(stapel3)

main()
