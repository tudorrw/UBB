import sqlite3
from append_to_data import Auto

connection = sqlite3.connect('cars_table.db')

cursor = connection.cursor()
# create table
# table = ("""CREATE TABLE autos(
#             marke TEXT, 
#             baujahr INTEGER,
#             farbe TEXT
#         )""")
# cursor.execute(table)



# insert values in table

# auto_details = "INSERT INTO autos VALUES('BMW x6', 2010, 'schwarz')"
# c.execute(auto_details)

# using classes
car1 = Auto('Dacia', 1999, 'bleu')
car2 = Auto('Rolls Roice', 2007, 'schwarz')
car3 =Auto('McLaren', 2020, 'wiess')
# cursor.execute("INSERT INTO autos VALUES(?, ?, ?)", (car1.marke, car1.baujahr, car1.farbe))
# connection.commit()
# cursor.execute("INSERT INTO autos VALUES(:marke, :baujahr, :farbe)", {'marke':car2.marke, 'baujahr':car2.baujahr, 'farbe':car2.farbe})
# connection.commit()
# cursor.execute("INSERT INTO autos VALUES(:marke, :baujahr, :farbe)", {'marke':car3.marke, 'baujahr':car3.baujahr, 'farbe':car3.farbe})
# connection.commit()



# update a car from table
# update_car_details = "UPDATE autos SET marke = 'Mercedes Benz' WHERE baujahr = 2002"
# c.execute(update_car_details)

# delete a car from table
# delete_car_details = "DELETE FROM autos WHERE marke IS 'Skoda'"
# c.execute(delete_car_details)


cursor.execute("SELECT * FROM autos WHERE baujahr = :baujahr", {'baujahr':1999})
print(cursor.fetchall()) 

connection.commit()

connection.close()