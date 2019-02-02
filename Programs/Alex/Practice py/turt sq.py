import turtle


turt = turtle.Turtle()

def square(length, angle):


 turt.forward(length)
 turt.left(angle)
 turt.forward(length)
 turt.left(angle)
 turt.forward(length)
 turt.left(angle)
 turt.forward(length)

for v in range(3):
 square(100, 90)



