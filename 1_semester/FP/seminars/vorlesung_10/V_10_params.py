def sum (a, b):
    return a + b

def f (**kwargs):
    print(kwargs)

def main():
    print(sum(b=2, a=1))
    print(sum(1, 2))

    f(a=1, b=2)
    d = {'A':2, 'B':10}
    f(**d)
main()