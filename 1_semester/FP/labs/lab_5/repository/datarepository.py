class DataRepo:

    def __init__(self, datei):
        self.datei = datei

    # speichert die Liste von Ojekten aus einer Datei
    def save(self, objects):
        data_str = self.convert_to_string(objects)
        self.write_to_file(data_str)
        
    # lieste eine Liste von Objekten aus einer Datei
    def load(self):
        # behandelt die Error, wenn es keine csv Datei existiert und gibt eine leere Liste zuruck bis die Datei erzeugt ist
        try:
            data_str = self.read_file()
            objects = self.convert_from_string(data_str)
            return objects
        except FileNotFoundError:
            return []

    # liest den Inhalt einer Datei und gibt ihn zuruck
    def read_file(self):
        with open(self.datei, 'r') as f:
            objects = f.read()
        return objects
        

    # schreibt einen String in eine Datei und uberschreibt die Datei   
    def write_to_file(self, data_str):
        with open(self.datei, 'w') as f:
            f.write(data_str)

    # konvertiert eine Liste von Objekten in einem String
    def convert_to_string(self):
        pass

    # konvertiert einen String in eine Liste von Objekten    
    def convert_from_string(self):
        pass