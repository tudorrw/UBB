#include "Controller.h"

namespace controller {

    Controller::Controller(std::shared_ptr<repository::ElectricScooterRepo> repo_) : repo(repo_) {}


    std::shared_ptr<ElectricScooter> Controller::foundElement(const std::string &id_) const {
        auto database = repo->getAll();
        auto lambda = [id_](const std::shared_ptr<ElectricScooter> &e_scooter_) {
            return id_ == e_scooter_->getId();
        };
        auto it = std::find_if(database.begin(), database.end(), lambda);
        // if an electric scooter with the searched id is found, it will return a pointer to the smart pointer of the corresponding object
        // otherwise will return nullptr
        if (it != database.end()) {
            return *it;
        }
        return nullptr;
    }


    bool Controller::add(const std::string &id_, const std::string &model_, const std::string &commissioning_date_,
                         int kilometers_, const std::string &last_location_, domain::State state_) {
        if (foundElement(id_)) {
            return false;
        }
        //gives a shared pointer object as a parameter to the repo add method
        repo->addElectricScooter(
                std::make_shared<ElectricScooter>(id_, model_, commissioning_date_, kilometers_, last_location_,
                                                  state_));
        return true;
    }


    bool Controller::remove(const std::string &id_) {
        std::shared_ptr<ElectricScooter> e_scooter = foundElement(id_);
        if (!e_scooter) {
            return false;
        }
        repo->removeElectricScooter(e_scooter);
        return true;
    }


    bool Controller::update(const std::string &id_, int new_kilometers, const std::string &new_location,
                            int new_state) {
        std::shared_ptr<ElectricScooter> e_scooter = foundElement(id_);
        if (!e_scooter) {
            return false;
        }
        repo->updateElectricScooter(e_scooter, new_kilometers, new_location, new_state);
        return true;
    }


    std::vector<std::shared_ptr<ElectricScooter>>
    Controller::searchByLocation(const std::string &last_location) {

        auto database = repo->getAll();
        if (!last_location.length()) {
            return database;
        }
        std::vector<std::shared_ptr<ElectricScooter>> matches;
        //std::string::npos -- until the end of the string
        auto lambda = [last_location](const std::shared_ptr<ElectricScooter> &e) {
            return e->getLastLocation().find(last_location) != std::string::npos;
        };
        //std::copy_if - iterates over all electric scooters in the database and adds only those accepted by the lambda expression in the 'matches' vector
        std::copy_if(database.begin(), database.end(), std::back_inserter(matches), lambda);
        return matches;
    }

    std::vector<std::shared_ptr<ElectricScooter>> Controller::filterByAge(int age, int option) {
        std::vector<std::shared_ptr<ElectricScooter>> matches;
        auto database = repo->getAll();
        auto lambda = [age, option, this](const std::shared_ptr<ElectricScooter> &e) {
            if (option == 1) {
                return 2023 - getYear(e) >= age;
            } else if (option == 2) {
                return 2023 - getYear(e) < age;
            }
            return false;
        };
        std::copy_if(database.begin(), database.end(), std::back_inserter(matches), lambda);
        return matches;
    }

    std::vector<std::shared_ptr<ElectricScooter>> Controller::filterByKilometers(int kilometers, int option) {
        std::vector<std::shared_ptr<ElectricScooter>> matches;
        auto database = repo->getAll();

        auto lambda = [kilometers, option](const std::shared_ptr<ElectricScooter> &e) {
            if (option == 1) {
                return e->getKilometers() >= kilometers;
            } else if (option == 2) {
                return e->getKilometers() < kilometers;
            }
            return false;
        };


        std::copy_if(database.begin(), database.end(), std::back_inserter(matches), lambda);
        return matches;
    }

    bool Controller::compareDates(const std::string &date1, const std::string &date2) {
        std::stringstream ss1(date1), ss2(date2);
        int day1, month1, year1, day2, month2, year2;

        //separate the string in int
        ss1 >> year1;
        ss1.ignore(1, '-');
        ss1 >> month1;
        ss1.ignore(1, '-');
        ss1 >> day1;

        ss2 >> year2;
        ss2.ignore(1, '-');
        ss2 >> month2;
        ss2.ignore(1, '-');
        ss2 >> day2;

        if (year1 != year2) {
            return year1 < year2;
        } else if (month1 != month2) {
            return month1 < month2;
        } else {
            return day1 < day2;
        }
    }

    std::vector<std::shared_ptr<ElectricScooter>> Controller::sortByDate() {
        auto database = repo->getAll();
        auto lambda = [](const std::shared_ptr<ElectricScooter> &e1,
                         const std::shared_ptr<ElectricScooter> &e2) {
            return compareDates(e1->getCommissioningDate(), e2->getCommissioningDate());
        };
        std::sort(database.begin(), database.end(), lambda);
        return database;
    }

    std::vector<std::shared_ptr<ElectricScooter>> Controller::showE_Scooters(int option) {
        std::vector<std::shared_ptr<ElectricScooter>> available_e_scooters;
        auto database = repo->getAll();
        auto lambda = [option](const std::shared_ptr<ElectricScooter> &e) {
            if (option == 0)
                return e->getStateOfScooter() == 0;
            else if (option == 1)
                return e->getStateOfScooter() == 1;
        };
        std::copy_if(database.begin(), database.end(), std::back_inserter(available_e_scooters), lambda);

        return available_e_scooters;
    }

    bool Controller::changeState(const std::string &id, int state) {
        std::shared_ptr<ElectricScooter> e_scooter = foundElement(id);
        if (!e_scooter) {
            return false;
        }

        if (state == 1) {
            repo->changeStateToReserved(e_scooter);
            return true;
        } else if (state == 2) {
            repo->changeStateToInUse(e_scooter);
            return true;
        }
        return false;
    }


    int Controller::getYear(const std::shared_ptr<ElectricScooter> &e) {
        std::stringstream ss1(e->getCommissioningDate());
        int day, month, year;

        //separate the string in int
        ss1 >> year;
        ss1.ignore(1, '-');
        ss1 >> month;
        ss1.ignore(1, '-');
        ss1 >> day;

        return year;
    }

    bool Controller::reserveScooters(const std::vector<std::string> &scooterIds) {
        bool allReserved = true;

        for (const std::string &id: scooterIds) {
            bool reserved = changeState(id, 1); // Change state to reserved (state = 1)
            reservedIds.emplace_back(id);
            if (!reserved) {
                allReserved = false;
            }
        }

        return allReserved;
    }

    std::vector<std::shared_ptr<ElectricScooter>> Controller::seeReservedScootersUser() {
        //return this->reservedIds;
        auto database = repo->getAll();
        std::vector<std::shared_ptr<ElectricScooter>> matches;
        for (const std::string &id: this->reservedIds) {
            auto lambda = [&id](const std::shared_ptr<ElectricScooter> &e) {
                return e->getId() == id;
            };
            //std::copy_if - iterates over all electric scooters in the database and adds only those accepted by the lambda expression in the 'matches' vector
            std::copy_if(database.begin(), database.end(), std::back_inserter(matches), lambda);
        }
        return matches;
    }

}