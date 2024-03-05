#include "FruitUI.h"

using std::cout;
using std::cin;
namespace UI {

    FruitUI::FruitUI(controller::FruitController controller_) : controller(std::move(controller_)) {}

    std::string FruitUI::menu() {
        return "1. Fugen Sie eine Frucht hinzu\n"
               "2. Loschen Sie eine Frucht\n"
               "3. Schauen alle Produkte an\n"
               "4. Schauen alle Produkte der Menge kleiner als ein eingegeben Wert an\n"
               "5. Sortieren alle Produkte nach Haltbarkeitsdatum aufsteigend\n"
               "6. Exit\n";
    }

    void FruitUI::printFruits(const std::vector<std::shared_ptr<domain::Fruit>> &fruits) {
        for (const std::shared_ptr<domain::Fruit> &it: fruits) {
            cout << it->toString() << std::endl;
        }
    }

    void FruitUI::FruitApp() {
        cout << menu();
        bool quit = false;
        while (!quit) {
            cout << "Wahlen Sie eine Option: ";
            int option;
            cin >> option;
            switch (option) {
                case 1: {
                    std::string name, origin, expirationDate;
                    int quantity, price;
                    cout << "Geben Sie den Name der Frucht ein: ";
                    cin >> name;
                    cout << "Geben Sie die Herkunft der Frucht ein: ";
                    cin >> origin;
                    cout << "Geben Sie das Haltbarkeitsdatum der Frucht ein: ";
                    cin >> expirationDate;
                    cout << "Geben Sie die Menge der Frucht ein: ";
                    cin >> quantity;
                    cout << "Geben Sie den Preis der Frucht ein: ";
                    cin >> price;
                    controller.add(name, origin, expirationDate, quantity, price);
                    break;
                }
                case 2: {
                    std::string name, origin;
                    cout << "Geben Sie den Name der Frucht ein: ";
                    cin >> name;
                    cout << "Geben Sie die Herkunft der Frucht ein: ";
                    cin >> origin;
                    controller.remove(name, origin);
                    break;
                }
                case 3: {
                    cout << "Geben Sie eine Name ein: ";
                    std::string name;
                    cin.ignore();
                    std::getline(cin, name);
                    cout << "Bestimmte Fruchte: " << std::endl;
                    printFruits(controller.find(name));
                    break;
                }
                case 4: {
                    cout << "Geben Sie ein Wert ein: ";
                    int value;
                    cin >> value;
                    printFruits(controller.shortSupply(value));
                    break;
                }
                case 5:
                    printFruits(controller.sortByExpirationDate());
                    break;
                case 6:
                    cout << "Das Program wird beendet" << std::endl;
                    quit = true;
                    break;
                default:
                    std::cout << "Invalid Option! " << std::endl;
                    break;
            }
        }
    }
}