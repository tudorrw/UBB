# einige Klassen von entities Datei die innerhalb den Package Modelle gefundet wird 
from modelle.entities import GekochterGericht, Getrank, Kunde, Bestellung

# die Methode get_option der Klasse Option wird in die folgenden Klassen als ein Entscheidung fur die Restaurantpersonal verwendet
class Option:
    # gibt das Ergebniss zwishen die Parameter to_, from_ zuruck anderenfalls None
    # behadelt die ValueError wenn die Eingabewert nicht zwischen die Parameter to_, from_ erscheinen oder zu andere Instazen gehort
    def get_option(self, to_, from_):
        option = input('option: ')
        try:
            option = int(option)
            if option < to_ or option > from_:
                raise ValueError 
            return option
        except ValueError:
            return None

# Die Klasse verwandelt die SpeiseKarte und die Speisen
# enthaltet 4 Methode unter anderen die die CRUD - Operationen darstellen
class MenuController:
    #  2 Atributte die die Repository fur Gerichten und Getranken ersetzen
    #  Zum Aufrufen der Lade- unde Seichermethoden die CookedDishRepo und DrinkRepo Klassen
    def __init__(self, repo_dishes, repo_drinks):
        self.repo_dishes = repo_dishes
        self.repo_drinks = repo_drinks

    # Textnachricht die Optionen zuruckgeben
    def display_menu(self):
        return '''
            1 - Speise hinzufugen
            2 - Speise anzeigen
            3 - Speise aktualisieren
            4 - Speise loschen
            5 - Exit
            '''

    #  die Hauptige methode die alle Crud Operationen enthalten
    def manage_menu(self):
        while True:
            print(self.display_menu())
            # option ist gleich mit die Methode der Option Klasse
            option = Option().get_option(1, 5)
            match option:
                case 1:
                    # Gerichte/Getranke Hinzufugen
                    self.add_dish()
                case 2:
                    # Gerichte/Getranke Anzeigen
                    self.show_menu()
                case 3:
                    # Gerichte/Getranke Aktualisieren
                    self.update_dish()
                case 4:
                    # Gerichte/Getranke Loschen
                    self.delete_dish()
                case 5:
                    # zum Hauptmenu zuruckkehren
                    break
                case _:
                    # hier wird die ValueError behandelt
                    print('Invalid option')
   

    def add_dish(self):
        dish_type = input('Geben Sie einen Art von Speise (1.Gericht oder 2.Getrank): ')
        match dish_type:
            case '1':    
                # gibt man die Atributte der GekochterGericht Klasse ein
                id = input('das Id des Gerichts: ')
                portiongrosse = int(input('die Portiongröße des Gerichts: '))
                preis = int(input('den Preis des Gerichts: '))
                zubereitungszeit = int(input('die Zubereitungszeit des Gerichts: '))
                
                # in der Variable cooked dish ist ein Objekt erschafft
                cooked_dish = GekochterGericht(id, portiongrosse, preis, zubereitungszeit)
                # liest die Liste von Objekten aus einer Datei, damit kann man das newe Objekt in der Datei speichern 
                dishes_list = self.repo_dishes.load()
                #fugt man das Objekt in der Liste von Objekten hinzu 
                # die Liste wird in der Datei gespeichert
                dishes_list.append(cooked_dish)
                self.repo_dishes.save(dishes_list)
        
            case '2':
                id = input('das Id des Getranks: ')
                portiongrosse = int(input('die Portiongröße des Getranks: '))
                preis = int(input('der Preis des Gerichts: '))
                alkoholgehalt = int(input('der Alkoholgehalt des Getranks: '))
                
                getrank = Getrank(id, portiongrosse, preis, alkoholgehalt)    
                getranke = self.repo_drinks.load()
                getranke.append(getrank)
                self.repo_drinks.save(getranke)

            case _:
                print('Invalid option')


    def show_menu(self):

        dishes = self.repo_dishes.load()
        print('Gerichte:')
        # zeigt man die Gerichten an
        # verwendet man enumerate() zu iterieren 
        for i, dish in enumerate(dishes):
            print(f'{i + 1}: {dish.id}, {dish.portiongrosse}g, {dish.preis} EUR, {dish.zubereitungszeit} minuten')
        
        drinks = self.repo_drinks.load()
        print("Getranke:")
        # zeigt man die Getranken an
        for i, drink in enumerate(drinks):
            print(f'{i + 1}: {drink.id}, {drink.portiongrosse}g, {drink.preis} EUR, {drink.alkoholgehalt} Alkolhol')


    def update_dish(self):

        self.show_menu()
        dish_type = input('Geben Sie einen Art von Speise (1.Gericht oder 2.Getrank): ')
        match dish_type:
            case '1':
                # liest man die Index die Index des Gerichts die Sie aktualisieren mochten
                idx = input('Geben Sie die index des Gerichts die Sie aktualisieren mochten: ')
                try:

                    idx = int(idx)
                    dishes_list = self.repo_dishes.load()
                    if idx < 1 or idx > len(dishes_list):
                        raise ValueError
                    # wenn din Eingabewert gleich oder kleiner die Lange der Liste und grosser als 0, kann man die Atributte andern
                    # anderenfalls wird die ValueError behandelt
                    gericht = dishes_list[idx - 1]
                    gericht.id = input('das newe Id des Gerichts: ')
                    gericht.portiongrosse = int(input('die newe Portiongröße des Gerichts: '))
                    gericht.preis = int(input('der newe Preis des Gerichts: '))
                    gericht.zubereitungszeit = int(input('die newe Zubereitungszeit des Gerichts: '))
                    # wird die Liste mit dem aktualisierten Objekt zuruck gespeichert
                    self.repo_dishes.save(dishes_list)
                except ValueError:
                    print('Es gibt keinen Gericht mit diesem Index!')
            case '2':
                idx = input('Geben Sie die Index des Getranks die Sie aktualisieren mochten: ')
                try:
                    idx = int(idx)
                    drinks_list = self.repo_drinks.load()
                    if idx < 1 or idx > len(drinks_list):
                        raise ValueError
                    getrank = drinks_list[idx - 1]
                    getrank.id = input('das Id des Getranks: ')
                    getrank.portiongrosse = int(input('die Portiongröße des Getranks: '))
                    getrank.preis = int(input('den Preis des Gerichts: '))
                    getrank.alkoholgehalt = int(input('den Alkoholgehalt des Getranks: '))
                    self.repo_drinks.save(drinks_list)
                except ValueError:
                    print('Es gibt keinen Getrank mit diesem Index!')
            case _:
                print('Invalid option!')


    def delete_dish(self):
        dish_type = input('Geben Sie einen Art von Speise (1.Gerichte oder 2.Getranke): ')
        self.show_menu()
        match dish_type:
            case '1':
                # wenn din Eingabewert gleich oder kleiner die Lange der Liste und grosser als 0, 
                # kann man die Objekt mit der jeweilige Index loschen
                # andernfalls wird die ValueError belandelt
                idx = input('Geben Sie die Index des Gerichts die Sie loschen mochten: ')
                try:
                    idx = int(idx)
                    dishes_list = self.repo_dishes.load()
                    if idx < 1 or idx > len(dishes_list):
                        raise ValueError
                    del dishes_list[idx - 1]
                    # wird die Liste von Objekte ohne die Object mit der gewahlten Index in einer Datei gespeichert
                    self.repo_dishes.save(dishes_list)
                except ValueError:
                    print('Es gibt keinen Gericht mit diesem Index!')
            case '2':
                idx = input('Geben Sie die Index des Getranks die Sie loschen mochten: ')
                try:
                    idx  = int(idx)
                    drinks_list = self.repo_drinks.load()
                    if idx < 1 or idx > len(drinks_list):
                        raise ValueError
                    del drinks_list[idx - 1]
                    self.repo_drinks.save(drinks_list)
                except ValueError:
                    print('Es gibt keinen Getrank mit diesem Index!')
            case _:
                print('Invalid option!')


# Die Klasse verwandelt die Kunden
# enthaltet 4 Methode unter anderen die die CRUD - Operationen darstellen
class CustomerController:
    #  Atributte die die Repository fur Kunden ersetzen
    #  Zum Aufrufen der Lade- unde Seichermethoden der CustomerRepo Klasse 
    def __init__(self, repo_cust):
        self.repo_cust = repo_cust
    
    # Textnachricht die Optionen zuruckgeben
    def display_customers(self):
        return '''
            1 - Kunde hinzufugen
            2 - Kunde anzeigen
            3 - Kunde aktualisieren
            4 - Kunde loschen
            5 - Exit
        '''
    #  die Hauptmethode die alle Crud Operationen enthalten
    def manage_customers(self):
        while True:
            print(self.display_customers())
            # option ist gleich mit die Methode der Option Klasse
            option = Option().get_option(1, 6)
            match option:
                case 1:
                    # Kunde hinzufugen
                    self.add_customer()
                case 2:
                    # Kunde anzeigen
                    self.show_customers()
                case 3:
                    # Kunde aktualisieren
                    self.update_customer()
                case 4:
                    # Kunde loschen
                    self.delete_customer()
                case 5:
                    # zum Hauptmenu zuruckkehren
                    break
                case _:
                    # wird die ValueError behandelt
                    print('Invalid option!')


    def add_customer(self):
        customers_list = self.repo_cust.load()
        # id gleich mit der Index der letzen Element aus der Liste, anderenfalls gleich mit 1 wenn es keine Elemente gibt
        id = customers_list[-1].id + 1 if customers_list else 1
        name = input("der Name des Kunden: ")
        adresse = input("die Adresse des Kunden: ")
        # das Objekt ershaffen 
        customer = Kunde(id, name, adresse)
        customers_list.append(customer)
        self.repo_cust.save(customers_list)
        # gibt auch das Id zuruck
        # wird die Ausgabewert als Atributte in der Bestellung Klasse 
        return id
    

    def show_customers(self):
        customers_list = self.repo_cust.load()
        print('Kunde:')
        # zeigt man die Kunden an
        # verwendet man enumerate() zu iterieren 
        for cust in customers_list:
            print(f'{cust.id}: {cust.name}, {cust.adresse}')


    def update_customer(self):
        self.show_customers()
         # liest man die Index des Kundes die Sie aktualisieren mochten
        idx = input('Geben Sie das Id des Kunden die Sie aktualisieren mochten: ')
        try:
            idx = int(idx)
            customers_list = self.repo_cust.load()
            if idx < 1 or idx > len(customers_list):
                raise ValueError
            # wenn din Eingabewert gleich oder kleiner die Lange der Liste und grosser als 0, kann man die Atributte andern
            # anderenfalls wird die ValueError behandelt
            customer = customers_list[idx-1]
            customer.name = input('Der newe Name des Kunden: ')
            customer.adresse = input('Die newe Adresse des Kunden: ')
            # wird die Liste mit dem aktualisierten Objekt zuruck gespeichert
            self.repo_cust.save(customers_list)
        except ValueError:
            print('Es gibt keinen Kunde mit diesem Index!')


    def delete_customer(self):
        self.show_customers()
        # wenn din Eingabewert gleich oder kleiner die Lange der Liste und grosser als 0, 
        # kann man die Objekt mit der jeweilige Index loschen
        # andernfalls wird die ValueError belandelt
        idx = input('Geben Sie das Id des Kunden die Sie loschen mochten: ')
        try:
            idx = int(idx)
            customers_list = self.repo_cust.load()
            if idx < 1 or idx > len(customers_list):
                raise ValueError
            customers_list = list(filter(lambda x: (x.id != idx), customers_list))
            # wird die Liste von Objekte ohne die Object mit der gewahlten Index in einer Datei gespeichert
            self.repo_cust.save(customers_list)
        except ValueError:
            print('Es gibt keinen Kunde mit diesem Index!')

    
# erbt von MenuController und CustomerController
class OrderController(MenuController, CustomerController):
    # erbt die Atributten von der 2 fruheren Klassen
    # hat auch eine Atrributte die die Repository fur Bestellungen ersetzen
    def __init__(self, repo_dishes, repo_drinks, repo_cust, repo_orders):
        MenuController.__init__(self, repo_dishes, repo_drinks)
        CustomerController.__init__(self, repo_cust)
        self.repo_orders = repo_orders

    # Textnachricht die Optionen zuruckgeben
    def display_order_req(self):
        return '''
            1 - Bestellung registrieren
            2 - Bestellung anzeigen
            3 - Exit
        '''   


    def manage_orders(self):
        while True:
            # zeigt die Texnachricht an
            print(self.display_order_req())
            # option ist gleich mit die Methode der Option Klasse 
            option = Option().get_option(1, 3)
            match option:
                case 1:
                    self.place_order()
                case 2:
                    self.display_invoices()
                case 3:
                    break    
                case _:
                    print('Invalid option!')


    #  die Suche nach dem Kunde
    def search_customers(self):

        customers_list = self.repo_cust.load()
        # leere Liste, mit der wir in die Schleife eintreten können
        search_results = []
        # id - die Ausgabewert
        # als Atributte in der Bestellung Klasse verwendent
        id = None

        while len(search_results) != 1:
            search_customer = input("Geben Sie die Name oder die Adresse des Kunden: ").lower()
            # wenn die Eingabewert in dem Name oder der Adresse erscheint, ist die Kunde in der Liste gespeichert
            search_results = [customer for customer in customers_list if not customer.id or search_customer in customer.name.lower() or search_customer in customer.adresse.lower()]
            for customer in search_results:
                print(f'{customer.id}, {customer.name}, {customer.adresse}')
                
                id = customer.id
            # wenn keine Ergebnisse existiert, verlassen wir de Schliefe
            if not len(search_results):
                print('Kein Kunde gefunden! ')
                break
            
            # wenn mehrere Ergebnisse haben, kann man mahrmals auf Kunden Namens oder Adresse suchen bis es nur ein Ergebniss haben
            if len(search_results) > 1:
                continue_search = input('Wollen sie noch fortsetzen? (1 fur Ja, alles anderes fur Nein): ')
                match continue_search:
                    case '1':
                        pass
                    case _:
                        break

        # wenn wir nur ein Ergebnis haben
        # haben wir den Moglichkeit zu entsheiden, wenn es das gewnschte Ergebnis ist 
        if len(search_results) == 1:                    
            choice = input('Ist das das richtige Ergebniss (1 fur Ja, alles anderes fur Nein): ')
            match choice:
                case '1':
                    # wird das Id des gefunden Kundes zuruckgibt
                    return id
                case _:
                    pass    

        print('Versuchen Sie einen neuen Kunde hinzufugen oder suchen Sie noch einmal.') 
        return None

    # gibt die Kunden id zuruck
    # hat 2 zwei Methoden durch die die Antwort erhalten werden kann: die Such oder Hinzufugen Methode
    def give_customer_id(self):
        customer_id = None
        while True:
            option = input('Suchen Sie einen Kunde(1) oder fugen Sie einen neuen Kunde hinzu(2): ')
            match option:
                case '1':
                    customer_id = self.search_customers()
                    if type(customer_id) is int:
                        break 
                case '2':
                    customer_id = self.add_customer()
                    break 
                case _:     
                    print('Invalid option')

        return customer_id

    # aus der Speisekarte auswahlen
    def select_dishes(self):
        self.show_menu()

        dishes_id = []
        dishes = self.repo_dishes.load()
        if not dishes:
            print('Es gibt keine Gerichte im Speisekarte!')
        else:
            while True:
                option = input('Wahlen Sie die Gerichte aus. Geben Sie die Index den Gerichten(0 fur Schliessen): ')
                try:
                    option = int(option)
                    if option < 0 or option > len(dishes):
                        raise  ValueError
                    if not option:
                        break 
 
                    dishes_id.append(dishes[option - 1].id) 
                except ValueError:
                    print('Invalid option!')


        drinks_id = []
        drinks_list = self.repo_drinks.load()
        if not drinks_list:
            print('Es gibt keine Getranke im Speisekarte!')
        else:
            while True:
                option = input('Wahlen Sie die Gentranke aus. Geben Sie die Index den Getranken(0 fur Schliessen): ')
                try:
                    option = int(option)
                    if option < 0 or option > len(drinks_list):
                        raise ValueError
                    if not option:
                        break
                    drinks_id.append(drinks_list[option - 1].id)
                except ValueError:
                    print('Invalid option!')
        # wandelt dishes_id in einen String um
        # Elementen der Liste sind mit einen Punkt getrennt
        dishes_id = '.'.join(dishes_id)
        drinks_id = '.'.join(drinks_id)
        return dishes_id, drinks_id

    def place_order(self):
        orders_list = self.repo_orders.load()
        dishes_list = self.repo_dishes.load()
        drinks_list = self.repo_drinks.load()

        id = orders_list[-1].id + 1 if orders_list else 1
        customer_id = self.give_customer_id()
        dishes_ids, drinks_ids = self.select_dishes()

        order = Bestellung(id, customer_id, dishes_ids, drinks_ids, 0)
        gerichte = [gericht for gericht in dishes_list if gericht.id in dishes_ids]
        getranke = [getrank for getrank in drinks_list if getrank.id in drinks_ids]
        gesamtkosten = order.berechnung_kosten(gerichte, getranke)

        order = Bestellung(id, customer_id, dishes_ids, drinks_ids, gesamtkosten)
        orders_list.append(order)
        self.repo_orders.save(orders_list)

    #  alle Rechnungen anzeigen
    def display_invoices(self):
        orders = self.repo_orders.load()
        list(map(lambda x: x.rechnung_anzeigen(), orders))


