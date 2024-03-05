#include "FruitRepo.h"
namespace repository{
    void FruitRepo::addFruit(const std::shared_ptr<domain::Fruit>& fruit) {
        database.push_back(fruit);
    }

    void FruitRepo::removeFruit(std::shared_ptr<domain::Fruit> fruit) {
        auto lambda = [&](const std::shared_ptr<domain::Fruit>& fruit_){return fruit_->get_name() == fruit->get_name() && fruit_->get_type() == fruit->get_type(); };
        auto it = std::find_if(database.begin(), database.end(), lambda);
        if(it != database.end()){
            database.erase(it);
        }
    }


    std::vector<std::shared_ptr<domain::Fruit>> FruitRepo::get_all(){
        return database;
    }
}


