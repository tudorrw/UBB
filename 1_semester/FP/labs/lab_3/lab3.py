import turtle  
t = turtle.Turtle()
#1
def Rechteck():
    # erste rechteck
    t.hideturtle()
    t.penup()
    t.left(90)
    t.forward(50)
    t.right(90)
    t.pendown()
    t.forward(100)
    t.right(90)
    t.forward(100)
    t.right(90)
    t.forward(200)
    t.right(90)
    t.forward(100)
    t.right(90)
    t.forward(100)
    # zweite rechteck
    t.penup()
    t.right(90)
    t.forward(37.5)
    t.left(90)
    t.pendown()
    t.forward(25)
    t.right(90)
    t.forward(25)
    t.right(90)
    t.forward(50)
    t.right(90)
    t.forward(25)
    t.right(90)
    t.forward(25)
    t.right(90)

#2
def curve():
    for i in range (200):
        t.right(1)
        t.forward(1)

def Herz():
    t.left(135)
    t.forward(131)
    curve()
    # t.setposition(0,0)
    t.left(130)
    curve()
    t.forward(130)
    t.hideturtle()

#3
# um mir zu sagen in welche richtung will ich die Pfeil bewegen
# sodass sie de beide Schildkrote gleichzeit zu bewegen
def move(length):
    for i in range(length):
        h1.forward(1)
        h2.forward(1)

def direction_left(angle):
    h1.left(angle)
    h2.left(angle) 

def direction_right(angle):
    h1.right(angle)
    h2.right(angle)

def quadrat(length):
    for i in range(4):
        direction_right(90)
        move(length)

def Hausen():
    direction_left(90)
    move(200)
    direction_right(45)
    move(200)
    direction_right(90)
    move(200)
    direction_right(135)
    move(283)
    direction_left(180)
    move(283)
    direction_right(90)
    move(200)
    direction_right(90)
    move(283)
    direction_left(180)
    #tur
    move(121)
    direction_left(90)
    move(60)
    direction_right(90)
    move(41)
    direction_right(90)
    # turknauf
    move(32)
    quadrat(4)
    move(32)
    #set position for fenster
    h1.hideturtle()           
    h1.penup()                
    h1.goto(-17,150)       
    h1.showturtle()           
    h1.pendown()
    h2.hideturtle()           
    h2.penup()                
    h2.goto(583, 150)       
    h2.showturtle()           
    h2.pendown()
     #fenster
    quadrat(50)
    quadrat(25)
    direction_right(90)
    move(50)
    quadrat(25)
    direction_right(90)
    move(50)
    quadrat(25)

ubung = int(input("Wahlen Sie einen Zeichnung zwischen zwei Rechtecke, einen Herz und zwei Hauser: "))

match ubung:
    case 1:
        Rechteck()
    case 2:
        Herz()
    case 3:
        t.hideturtle()
        h1 = turtle.Turtle()
        h1.hideturtle()           
        h1.penup()                
        h1.goto(-300,0)       
        h1.showturtle()           
        h1.pendown()   

        h2 = turtle.Turtle()
        h2.hideturtle()           
        h2.penup()                
        h2.goto(300, 0)       
        h2.showturtle()           
        h2.pendown()  

        Hausen()
    case _:
        print('Danke dass Sie meine Anwendung verwenden')
turtle.done()


