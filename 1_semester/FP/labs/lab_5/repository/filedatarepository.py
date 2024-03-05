from repository.datarepository import DataRepo
from modelle.entities import GekochterGericht, Getrank, Kunde, Bestellung

# alle folgende Klassen erben von die DataRepo Klasse
class CookedDishRepo(DataRepo):
    def __init__(self, datei):
        super().__init__(datei)

    def convert_to_string(self, objects):
        data_str = map(lambda x: f"{x.id},{x.portiongrosse},{x.preis},{x.zubereitungszeit}", objects)
        return '\n'.join(data_str)

    def convert_from_string(self, data_str):
        
        lines = data_str.split('\n')
        objects = []
        for object in lines:
            if object:
                id, portiongrosse, preis, zubereitungszeit = object.split(',')
                objects.append(GekochterGericht(id, int(portiongrosse), int(preis), int(zubereitungszeit)))
        return objects
    

class DrinkRepo(DataRepo):
    def __init__(self, datei):
        super().__init__(datei)

    def convert_to_string(self, objects):
        data_str = map(lambda x: f"{x.id},{x.portiongrosse},{x.preis},{x.alkoholgehalt}", objects)
        return '\n'.join(data_str)
    
    def convert_from_string(self, data_str):
        lines = data_str.split('\n')
        objects = []
        for object in lines:
            if object:
                id, portiongrosse, preis, alkoholgehalt = object.split(',')
                objects.append(Getrank(id, int(portiongrosse), int(preis), int(alkoholgehalt)))
        return objects
        

class CustomerRepo(DataRepo):
    def __init__(self, datei):
        super().__init__(datei)
    
    def convert_to_string(self, objects):
        data_str = map(lambda x: f"{x.id},{x.name},{x.adresse}", objects)
        return '\n'.join(data_str)

    def convert_from_string(self, data_str):
        lines = data_str.split('\n')
        objects = []
        for object in lines:
            if object:
                id, name, adresse = object.split(',')
                objects.append(Kunde(int(id), name, adresse))
        return objects 
        # if lines:
        #     objects_str = list(map(lambda x: x.split(','), lines))
        #     return list(map(lambda x: Kunde(int(x[0]), x[1], x[2]), objects_str))
        # return []


class OrderRepo(DataRepo):
    def __init__(self, datei):
        super().__init__(datei)

    def convert_to_string(self, objects):

        data_str = map(lambda x: f"{x.id},{x.kunden_id},{x.gerichte_ids},{x.getranke_ids},{x.gesamtkosten}", objects)        
        return '\n'.join(data_str)

    def convert_from_string(self, data_str):
        objects = []
        lines = data_str.split('\n')
        for object in lines:
            if object:
                id, kunden_id, gerichte_ids, getranke_ids, gesamtkosten = object.split(',')
                order = Bestellung(int(id), int(kunden_id), gerichte_ids, getranke_ids, gesamtkosten)
                objects.append(order)
        return objects




