import sqlite3

conn = sqlite3.connect('student.db')

c = conn.cursor()
# table = """CREATE TABLE students(
#             fname TEXT,
#             lname TEXT,
#             age INTEGER
#         )"""
# c.execute(table)

# c.execute("INSERT INTO students VALUES('Rico', 'Vasquez', 23)")
c.execute("INSERT INTO students VALUES('Rico', 'Johnson', 19)")


print(c.fetchall())

conn.commit() 

conn.close()
