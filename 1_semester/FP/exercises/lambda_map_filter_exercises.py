# lambda exercises
def space_between():
    print('XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX')

r = lambda a: a + 15
print(r(10))
r = lambda x, y: x * y
print(r(12, 4))


space_between()


def function(n):
    return lambda x: x * n

result = function(2)
print('Double the number of 15:', result(15))


space_between()


list_tuples = [('English', 88), ('Science', 90), ('Maths', 97), ('Social sciences', 82)]
list_tuples.sort(key = lambda x: x[1])
print(list_tuples)


space_between()


original = [{'make': 'Nokia', 'model': 216, 'color': 'Black'}, {'make': 'Mi Max', 'model': '2', 'color': 'Gold'}, {'make': 'Samsung', 'model': 7, 'color': 'Blue'}]
new = sorted(original, key = lambda x: x['color'])
print(new)


space_between()


original = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
odd = list(filter(lambda element: element % 2, original))
even = list(filter(lambda element: not element % 2, original))
print(odd)
print(even)


space_between()


starts_with = lambda x: True if x.startswith('w') else False
print(starts_with('perre'))


space_between()


original = [-1, 2, -3, 5, 7, 8, 9, -10]
new = sorted(original, key = lambda x: 0 if x == 0 else -1/x)
print(new)


space_between()


import datetime

now = datetime.datetime.now()

year = lambda x: x.year
month = lambda x:x.month
day = lambda x:x.day
t = lambda x: x.time()

print(year(now))
print(month(now))
print(day(now))
print(t(now))


space_between()


arr1 = [1, 2, 3, 5, 7, 8, 9, 10]
arr2 = [1, 2, 4, 8, 9]

intersection = list(filter(lambda elem: elem in arr1, arr2))
print(intersection)


space_between()


from collections import Counter
arr = [1, 2, 3, 5, 7, 8, 9, 10]
odd = list(filter(lambda x: x % 2, arr))
even = list(filter(lambda x: not x % 2, arr))
print(len(odd))
print(len(even))


space_between()


original = [2, 4, -6, -9, 11, -12, 14, -5, 17]
positive = list(filter(lambda x: x > 0, original))
negative = list(filter(lambda x: x < 0, original))
print(sum(positive))
print(sum(negative))


space_between()


original = ['php', 'w3r', 'Python', 'abcd', 'Java', 'aaa']
new = list(filter(lambda x: (x == x[::-1]), original))
print(new)


space_between()


strings = ['Red', 'Green', 'Blue', 'White', 'Black']
rev_strings = list(map(lambda x: x[::-1], strings))
print(rev_strings)


space_between()
space_between()


#map functions 
arr = [1, 2, 3, 5, 7, 8, 9, 10]
arr1 = list(map(lambda x: x * 3, arr))
print(arr1)


space_between()


lst = [1, 4, 5] 
tupl = (1, 4, 5)

strg1= list(map(str, lst))
strg2 = list(map(str, tupl))
print(strg1)
print(strg2)


space_between()


strg = ['bLacK', 'Red', 'whitE', 'black']
lower = list(map(lambda x: x.lower(), strg))
upper = list(map(lambda x: x.upper(), strg))


space_between()

