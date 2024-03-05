import turtle 

t = turtle.Turtle()
t.color("red")
#spiral square
def square(length):
    for i in range(4):
        t.forward(length)
        t.left(90)

def spiral(length, x):
    while True:
        square(length)
        t.left(5)
        length += x
        if length >= 220:
            break

t.speed(0)
# for example: length = 50, x = 1
length = int(input("length = "))
x = int(input("x = "))
spiral(length,x)
turtle.done()
