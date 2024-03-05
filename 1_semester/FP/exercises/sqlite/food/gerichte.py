import sqlite3

connection = sqlite3.connect('food.db')

cursor = connection.cursor()
table = """
    CREATE TABLE gerichte(
        id INTEGER PRIMARY KEY,
        name TEXT,
        portiongrosse TEXT    
    )"""
cursor.execute(table)
add_food = """
INSERT INTO gerichte VALUES
(1, 'Spaghetti', 350),
(2, 'Pizza', 500),
(3, 'Foccacia', 200),
(4, 'Rice', 420)
"""
cursor.execute(add_food)
cursor.execute("SELECT * FROM gerichte WHERE id = 4")
result = cursor.fetchone()
print(result)

connection.commit()
connection.close()