#ifndef L4_CIOBANU_TUDOR_FRUITCONTROLLER_H
#define L4_CIOBANU_TUDOR_FRUITCONTROLLER_H

#include <sstream>
#include <ctime>
#include "FruitRepo.h"

namespace controller {

    class FruitController {
    private:
        std::shared_ptr<repository::FruitRepo> repository;

        /**
         * Funktion die zwei Haltbarkeitsdaten vergleicht
         * @param date1: const reference to the expiry date string
         * @param date2: const reference to the expiry date string
         * @return: gibt die großere Haltbarkeitsdatum zurück
         */
        static bool compareDates(const std::string &date1, const std::string &date2);

    public:
        /**
         * Konstruktor
         * @param repository_
         */
        FruitController(std::shared_ptr<repository::FruitRepo> repository_);

        /**
         * Diese Funktion, die als Parameter die Attribute eines Produkt bekommt, erstellt ein Produkt von
         * Typ Fruit und fügt die Element in die Liste von Produkte hinzu und gibt true zurück. Falls die
         * Name und die Herkunft erscheinen in einem der Elemente in der ursprünglichen Liste, wird nur die
         * Menge verändert und die Funktion wird false zurückgeben.
         * @param Name, Origin, ExpirationDate, Quantity, Price
         * @return true oder false
         */
        bool add(const std::string &Name, const std::string &Origin, const std::string &ExpirationDate, int Quantity,
                 int Price);

        /**
         * Diese Funktion, die als Parameter die Name und Herkunft eines Produkt bekommt, löscht ein Produkt von
         * Typ Fruit und gibt true zurück. Falls die Name und die Herkunft erscheinen nicht in einem der Elemente in der ursprünglichen Liste,
             * wird die Funktion false zurückgeben.
         * @param Name, Origin
         * @return true oder false
         */
        bool remove(const std::string &Name, const std::string &Origin);

        /**
         * Funktion die alle verfügbaren Produkte auflistet, die eine bestimmte Zeichenkette enthalten, sortiert nach
         * Namen. Falls der String leer ist, werden alle verfügbaren Produkte zurückgeben
         * @param String: const reference to a string
         * gibt eine bestimmte Liste von Element zurück
         */
        std::vector<std::shared_ptr<domain::Fruit>> find(const std::string &String) const;

        /**
         * Funktion die eine Liste mit Produkte, die knapp sind erstellt
         * (Menge kleiner als ein vom Benutzer angegebener Wert)
         * @param value: int vom Benutzer angegeben
         * @return die bestimmte Liste
         */
        std::vector<std::shared_ptr<domain::Fruit>> shortSupply(int value) const;

        /**
         * Funktion die die Liste von Elemente nach Haltbarkeit aufsteigend sortiert
         */
        std::vector<std::shared_ptr<domain::Fruit>> sortByExpirationDate() const;

        /**
         * @return die Länge die Liste von Produkte
         */
        int getSize();
    };
}
#endif //L4_CIOBANU_TUDOR_FRUITCONTROLLER_H
