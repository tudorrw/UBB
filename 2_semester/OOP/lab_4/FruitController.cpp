#include "FruitController.h"


namespace controller {
    FruitController::FruitController(std::shared_ptr<repository::FruitRepo> repository_) : repository(
            std::move(repository_)) {}

    bool FruitController::add(const std::string &name_, const std::string &origin_, const std::string &expirationDate_,
                              int quantity_, int price_) {
        std::shared_ptr<domain::Fruit> fruit = std::make_shared<domain::Fruit>(name_, origin_, expirationDate_,
                                                                               quantity_, price_);
        auto database = repository->get_all();
        auto lambda = [&](const std::shared_ptr<domain::Fruit> &fruit_) {
            return fruit->get_name() == fruit_->get_name() && fruit->get_type() == fruit_->get_type();
        };
        auto it = std::find_if(database.begin(), database.end(), lambda);
        if (it != database.end()) {
            (*it)->set_quantity(fruit->get_quantity());
            return false;
        }
        repository->addFruit(fruit);
        return true;
    }

    bool FruitController::remove(const std::string &name_, const std::string &origin_) {
        auto database = repository->get_all();
        auto lambda = [name_, origin_](const std::shared_ptr<domain::Fruit> &fruit_) {
            return fruit_->get_name() == name_ && fruit_->get_type() == origin_;
        };
        auto it = std::find_if(database.begin(), database.end(), lambda);
        if (it != database.end()) {
            repository->removeFruit(*it);
            return true;
        }
        return false;
    }

    std::vector<std::shared_ptr<domain::Fruit>> FruitController::find(const std::string &String) const {

        auto database = repository->get_all();
        if (!String.length()) {
            return database;
        }

        std::vector<std::shared_ptr<domain::Fruit>> matches;
        for (auto &fruit: database) {
            //std::string::npos -- until the end of the string
            if (fruit->get_name().find(String) != std::string::npos) {
                matches.push_back(fruit);
            }
        }

        auto lambda = [](const std::shared_ptr<domain::Fruit> &fruit1, const std::shared_ptr<domain::Fruit> &fruit2) {
            return fruit1->get_name().length() < fruit2->get_name().length();
        };
        std::sort(matches.begin(), matches.end(), lambda);
        return matches;
    }


    std::vector<std::shared_ptr<domain::Fruit>> FruitController::shortSupply(const int value) const {
        std::vector<std::shared_ptr<domain::Fruit>> shortSupplyFruits;
        auto database = repository->get_all();
        auto lambda = [value](const std::shared_ptr<domain::Fruit> &fruit_) { return fruit_->get_quantity() < value; };
        //std::copy_if -- filter function that takes the lambda as predicate and inserts the result (vector) in shortSupplyFruits
        std::copy_if(database.begin(), database.end(), std::back_inserter(shortSupplyFruits), lambda);
        return shortSupplyFruits;
    }

    bool FruitController::compareDates(const std::string &date1, const std::string &date2) {
        std::stringstream ss1(date1), ss2(date2);
        int day1, month1, year1, day2, month2, year2;

        //separate the string in int
        ss1 >> day1;
        ss1.ignore(1, '/');
        ss1 >> month1;
        ss1.ignore(1, '/');
        ss1 >> year1;

        std::tm tm1 = {0};
        tm1.tm_year = year1 - 1900; // years since 1900
        tm1.tm_mon = month1 - 1; // 0-based index
        tm1.tm_mday = day1;

        std::time_t time1 = std::mktime(&tm1);

        ss2 >> day2;
        ss2.ignore(1, '/');
        ss2 >> month2;
        ss2.ignore(1, '/');
        ss2 >> year2;

        std::tm tm2 = {0};
        tm2.tm_year = year2 - 1900;
        tm2.tm_mon = month2 - 1;
        tm2.tm_mday = day2;

        std::time_t time2 = std::mktime(&tm2);

        return time1 > time2;
    }

    std::vector<std::shared_ptr<domain::Fruit>> FruitController::sortByExpirationDate() const {
        auto sortedFruits = repository->get_all();
        auto lambda = [](const std::shared_ptr<domain::Fruit> &fruit1, const std::shared_ptr<domain::Fruit> &fruit2) {
            return compareDates(fruit1->get_expiration_date(), fruit2->get_expiration_date());
        };
        std::sort(sortedFruits.begin(), sortedFruits.end(), lambda);
        return sortedFruits;
    }

    int FruitController::getSize() {
        auto database = repository->get_all();
        return database.size();
    }
}