from dataclasses import dataclass

@dataclass
class Room:
    nummer : str
    sp : int

    def __str__(self):
        return f'Raum({self.nummer}, {self.sp})'
    
@dataclass
class Gebaude:
    raume : Room
    
    def print_raume(self):
        for raum in self.raume:
            print(raum)

    def add_room(self, raum):
        self.raume.append(raum)

    def filter_room(self, sp):
        self.raume = filter(lambda x: x.sp > sp, self.raume)
    

    def update_room(self, raum, sp):
        # self.raume = list(map(lambda room: Room(room.nummer, sp) if room == raum else room, self.raume))
        self.raume = [Room(room.nummer, sp) if room == raum else room for room in self.raume]
        
    def delete_room(self, raum):
        self.raume.remove(raum)

    def sort_rooms(self):
        self.raume = sorted(self.raume, key = lambda raum: raum.sp)

def main():
    r1 = Room('Maria 2/III', 20)
    r2 = Room('Iorga 2/I', 100)
    r3 = Room('Cornelius 9/A', 70)
    
    g = Gebaude([r1, r2, r3])
    r4 = Room('Richard I/2', 120)
    r5 = Room('Gabriela 5/IV', 23)
    g.add_room(r4)
    g.add_room(r5)
    g.sort_rooms()
    g.filter_room(22)
    g.update_room(r5, 100)
    g.delete_room(r5)
    g.sort_rooms()
    g.print_raume()
main()
    
