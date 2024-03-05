from functools import reduce
class Identifizierbar:
    def __init__(self, id):
        self.id = id
        
class Gericht(Identifizierbar):
    
    def __init__(self, id, portiongrosse, preis):
        super().__init__(id)
        self.portiongrosse = portiongrosse
        self.preis = preis


class GekochterGericht(Gericht):
    def __init__ (self, id, portiongrosse, preis, zubereitungszeit):
        super().__init__(id, portiongrosse, preis)
        self.zubereitungszeit = zubereitungszeit


class Getrank(Gericht):
    def __init__(self, id, portiongrosse, preis, alkoholgehalt):
        super().__init__(id, portiongrosse, preis)
        self.alkoholgehalt = alkoholgehalt

class Kunde(Identifizierbar):
    def __init__(self, id, name, adresse):
        super().__init__(id)
        self.name = name
        self.adresse = adresse


class Bestellung(Identifizierbar):
    def __init__(self, id, kunden_id, gerichte_ids, getranke_ids, gesamtkosten):
        super().__init__(id)
        self.kunden_id = kunden_id
        self.gerichte_ids = gerichte_ids
        self.getranke_ids = getranke_ids
        self.gesamtkosten = gesamtkosten

    def berechnung_kosten(self, gerichte, getranke):        
        return reduce(lambda x,y: x + y.preis, gerichte + getranke, 0)
        
    def _rechnung_erstellen(self):
        artikel_str = self.gerichte_ids + '.' +self.getranke_ids
        artikel_str = artikel_str.replace('.', '\n')
        rechnung = f'Rechnung der Kunde mit id:{self.kunden_id}\nDie bestellungen Artikel\n{artikel_str}\nTotal:{self.gesamtkosten} EUR\n'
        return rechnung

    
    def rechnung_anzeigen(self):
        print(self._rechnung_erstellen())
