from repository.filedatarepository import CookedDishRepo, DrinkRepo, CustomerRepo, OrderRepo
from controller.controller import MenuController, CustomerController, OrderController, Option


class Console:
    # Textnachricht die Optionen zuruckgeben
    def display_menu(self):
        return '''
        Menu:
        1 - Bestellungen verwalten
        2 - Speisekarte verwalten
        3 - Kunde verwalten
        4 - Menu schliessen
        '''

    def run_app(self):

        cookeddish_repo = CookedDishRepo('cookeddish.csv')
        drink_repo = DrinkRepo('drink.csv')
        customer_repo = CustomerRepo('customer.csv')
        order_repo = OrderRepo('order.csv')

        menu_controller = MenuController(cookeddish_repo, drink_repo)
        customer_controller = CustomerController(customer_repo)
        order_controller = OrderController(cookeddish_repo, drink_repo, customer_repo, order_repo)

        
        while True:
            print(self.display_menu())
            option = Option().get_option(1, 4)
            match option:
                case 1:
                    pass
                    order_controller.manage_orders()
                case 2:
                    menu_controller.manage_menu()
                case 3:
                    customer_controller.manage_customers()
                case 4:
                    break
                case _: 
                    print('Invalid option')

