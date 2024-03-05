import sqlite3

class Gericht:
    def __init__(self, id_number = -1, name = "", portiongrosse = -1):
        self.id_number = id_number
        self.name = name
        self.portiongrosse = portiongrosse
        self.connection = sqlite3.connect('food.db')
        self.cursor = self.connection.cursor()


    def load_food(self, id_number):

        self.cursor.execute("""SELECT * FROM gerichte
        WHERE id = {}""".format(id_number))
        
        result = self.cursor.fetchone()

        self.id_number = id_number
        self.name = result[1]
        self.portiongrosse = result[2]

    def __repr__(self):
        return '{}, {}, {}'.format(self.id_number, self.name, self.portiongrosse)

    def insert_food(self):
        self.cursor.execute("""INSERT INTO gerichte VALUES
        ({}, '{}', {})""".format(self.id_number, self.name, self.portiongrosse))
        self.connection.commit()
    

   
def main():
    # g_insert = Gericht(9, 'Chftele', 300)
    # g_insert.insert_food()
    
    g_load = Gericht()
    g_load.load_food(4)
    print(g_load)

    connection = sqlite3.connect('food.db')
    cursor = connection.cursor()
    
    cursor.execute("SELECT * FROM gerichte")
    results = cursor.fetchall()
    print(results)

    connection.commit()
    connection.close()
main()