#include "InMemoryRepository.h"
namespace repository{


    InMemoryRepo::InMemoryRepo(const std::string &message_) : test(message_){}

    InMemoryRepo::InMemoryRepo() {
        insert_scooters();
    }

    void InMemoryRepo::insert_scooters() {
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("AAX", "Harley", "2023-02-03", 90, "Str. Horea 18", domain::in_use));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("BCZ", "Beeper", "2022-10-04", 4500, "Str. Bucium 17", domain::reserved));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("DEF", "Scooty", "2022-08-02", 1200, "Str. Avram Iancu 7", domain::parked));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("GHI", "Eco-Rider", "2021-12-05", 2400, "Str. Mihai Eminescu 14", domain::in_use));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("JKL", "Razor", "2021-11-06", 3500, "Str. Ion Creanga 11", domain::reserved));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("MNO", "X-Treme", "2022-01-05", 1800, "Str. Stefan cel Mare 22", domain::parked));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("PQR", "City Express", "2021-09-03", 5500, "Str. Gheorghe Doja 5", domain::in_maintenance));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("STU", "Vespa Elettrica", "2022-08-12", 700, "Str. Aviatorilor 19", domain::out_of_service));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("VWX", "E-Scooter Pro", "2022-10-09", 100, "Str. Horia 6", domain::parked));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("YZA", "Eco-Glide", "2021-06-11", 4200, "Str. Iuliu Maniu 27", domain::in_use));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("BCD", "E-Bird", "2022-07-07", 300, "Str. Universitatii 3", domain::reserved));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("EFG", "Green Mover", "2021-12-04", 2400, "Str. Avram Iancu 9", domain::parked));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("HIJ", "Red Runner", "2022-02-02", 1500, "Str. Mihai Viteazu 15", domain::in_maintenance));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("KLM", "Blaze", "2022-11-05", 50, "Str. Ion Creanga 22", domain::out_of_service));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("NOP", "E-Scoot", "2023-03-06", 120, "Str. Stefan cel Mare 8", domain::parked));
        fleeting_scooters.push_back(std::make_shared<domain::ElectricScooter>("QRS", "E-Rider", "2021-10-01", 4200, "Str. Gheorghe Doja 7", domain::in_use));
    }
    void InMemoryRepo::addElectricScooter(std::shared_ptr<domain::ElectricScooter> e_scooter) {
        fleeting_scooters.push_back(e_scooter);
    }
//erase - removes an electric scooter from the repository and uses a lambda to check whether the id of the electric scooter matches the id of the electric scooter passed as a parameter
    void InMemoryRepo::removeElectricScooter(std::shared_ptr<domain::ElectricScooter> e_scooter) {
        fleeting_scooters.erase(std::remove_if(fleeting_scooters.begin(), fleeting_scooters.end(), [e_scooter](
                std::shared_ptr<domain::ElectricScooter> e){ return e->getId() == e_scooter->getId(); }));
    }

    void InMemoryRepo::updateElectricScooter(std::shared_ptr<domain::ElectricScooter> e_scooter, int new_kilometers, const std::string &new_location, int new_state){
        //die neue eingegeben Kilometers können nicht geändert werden, wenn sie kleiner als die ursprünglichen Kilometers sind
        if(new_kilometers > e_scooter->getKilometers()){
            e_scooter->setKilometers(new_kilometers);
        }
        //überprüfen, ob die neue eingegebene Standort nicht leer ist
        if(!new_location.empty()){
            e_scooter->setLastLocation(new_location);
        }
        e_scooter->setState(new_state);
    }

   void InMemoryRepo::changeStateToReserved(std::shared_ptr<domain::ElectricScooter> e_scooter){
        e_scooter->setState(domain::reserved);
    }

    void InMemoryRepo::changeStateToInUse(std::shared_ptr<domain::ElectricScooter> e_scooter){
        e_scooter->setState(domain::in_use);
    }

    std::vector<std::shared_ptr<domain::ElectricScooter>> InMemoryRepo::getAll() {
        return this->fleeting_scooters;
    }
}