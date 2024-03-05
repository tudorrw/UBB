#ifndef L4_CIOBANU_TUDOR_FRUIT_H
#define L4_CIOBANU_TUDOR_FRUIT_H

#include <iostream>

namespace domain {

    class Fruit {
    private:
        std::string name;
        std::string origin;
        std::string expirationDate;
        int quantity;
        int price;

    public:
        /**
         * Konstruktor
         */
        Fruit(std::string Name, std::string Origin, std::string ExpirationDate, int Quantity, int Price);

        /**
         * Getters
         */
        std::string get_name() const;

        std::string get_type() const;

        std::string get_expiration_date() const;

        int get_quantity() const;

        int get_price() const;

        /**
         * SETTERS
         */
        void set_quantity(int newQuantity);

        void set_price(int newPrice);

        std::string toString();

    };
}
#endif //L4_CIOBANU_TUDOR_FRUIT_H
