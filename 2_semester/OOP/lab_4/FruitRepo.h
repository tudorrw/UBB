#ifndef L4_CIOBANU_TUDOR_FRUITREPO_H
#define L4_CIOBANU_TUDOR_FRUITREPO_H

#include <vector>
#include <memory>
#include <algorithm>
#include <numeric>

#include "Fruit.h"
namespace repository{

    class FruitRepo{
    private:
        std::vector<std::shared_ptr<domain::Fruit>> database;
    public:
        /**
         * Funktion die eine Fruchte in dem Produkte Lager hinzufügt
         * @param fruit: const (reference) shared pointer to a Fruit object
         */
        void addFruit(const std::shared_ptr<domain::Fruit>& fruit);

        /**
         * Funktion die eine Fruchte in dem Produkte Lager löscht. Wenn die Element existiert nicht, dann wird die Liste unverändert bleiben.
         * @param fruit: a shared pointer to a Fruit object
         */
        void removeFruit(std::shared_ptr<domain::Fruit> fruit);

        /**
         *  Funktion die die Liste von Elemente zurückgibt
         * @return database
         */
        std::vector<std::shared_ptr<domain::Fruit>> get_all();

    };
}
#endif //L4_CIOBANU_TUDOR_FRUITREPO_H
