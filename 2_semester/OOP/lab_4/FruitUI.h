#ifndef L4_CIOBANU_TUDOR_FRUITUI_H
#define L4_CIOBANU_TUDOR_FRUITUI_H

#include "FruitController.h"
namespace UI {

    class FruitUI {
    private:
        controller::FruitController controller;

        static std::string menu();

        /**
         * Listenobjekte werden angezeigt, damit die Fruit methode: toString() verwendet wird
         * @param fruits: const reference to a vector of shared pointers
         */
        static void printFruits(const std::vector<std::shared_ptr<domain::Fruit>> &fruits);

    public:
        /**
         * Konstruktor
         * @param controller_
         */
        FruitUI(controller::FruitController controller_);

        /**
         * Die Anwendung
         */
        void FruitApp();


    };
}
#endif //L4_CIOBANU_TUDOR_FRUITUI_H
