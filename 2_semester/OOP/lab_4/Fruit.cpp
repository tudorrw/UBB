#include "Fruit.h"

namespace domain{
    Fruit::Fruit(std::string Name, std::string Origin, std::string ExpirationDate, int Quantity, int Price)
            : name(Name), origin(Origin), expirationDate(ExpirationDate), quantity(Quantity), price(Price) {}

    std::string Fruit::get_name() const{
        return this->name;
    }

    std::string Fruit::get_type() const{
        return this->origin;
    }

    std::string Fruit::get_expiration_date() const {
        return this->expirationDate;
    }

    int Fruit::get_quantity() const {
        return this->quantity;
    }

    int Fruit::get_price() const{
        return this->price;
    }


    void Fruit::set_quantity(int newQuantity){
        this->quantity = newQuantity;
    }

    void Fruit::set_price(int newPrice) {
        this->price = newPrice;
    }
    std::string Fruit::toString() {
        return "Name:"+name+", Herkunft:"+origin+", Haltbarkeitsdatum:"+expirationDate+ ", Menge:"+std::to_string(quantity)+" kg, Preis:"+std::to_string(price)+" lei/kg";
    }
}


