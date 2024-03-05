#include "CSVRepository.h"

namespace repository {

    CSVRepo::CSVRepo(const std::string &file_) : csv_file(file_) {}

    void CSVRepo::saveToFile(const std::string &data) {
        std::ofstream file(csv_file);
        file<<"Identifier,Modell,Inbetriebnahmedatum,Kilometer,Standort,Status"<<std::endl;
        file << data << std::endl;
    }

    std::vector<std::shared_ptr<domain::ElectricScooter>> CSVRepo::loadFromFile(){

        std::fstream file(csv_file);


        std::vector<std::shared_ptr<domain::ElectricScooter>> e_scooters;
        std::string line;
        std::getline(file, line);
        while (std::getline(file, line)) {
            auto scooter = convert_from_string(line);
            e_scooters.push_back(scooter);
        }
        return e_scooters;
    }

    std::string CSVRepo::convert_to_string(const std::vector<std::shared_ptr<domain::ElectricScooter>>& e_scooters) {
        std::string data;
        for(int i = 0; i < e_scooters.size(); i++){
            data += e_scooters[i]->getId() + "," + e_scooters[i]->getModel() + "," + e_scooters[i]->getCommissioningDate() + "," +std::to_string(e_scooters[i]->getKilometers())
                   + "," + e_scooters[i]->getLastLocation() + ","+ std::to_string(e_scooters[i]->getStateOfScooter());
            if(i != e_scooters.size() - 1){
                data += '\n';
            }
        }
        return data;
    }

    std::shared_ptr<domain::ElectricScooter> CSVRepo::convert_from_string(const std::string& line){
        std::istringstream iss(line);
        std::string id, model, commissioningDate, kilometersStr, lastLocation, stateStr;

        std::getline(iss, id, ',');
        std::getline(iss, model, ',');
        std::getline(iss, commissioningDate, ',');
        std::getline(iss, kilometersStr, ',');
        std::getline(iss, lastLocation, ',');
        std::getline(iss, stateStr);
        int kilometers = std::stoi(kilometersStr);
        int state = std::stoi(stateStr);

        return std::make_shared<domain::ElectricScooter>(id, model, commissioningDate, kilometers, lastLocation, state);
    }

    void CSVRepo::addElectricScooter(std::shared_ptr<domain::ElectricScooter> e_scooter){
        auto e_scooters = loadFromFile();
        e_scooters.push_back(e_scooter);
        saveToFile(convert_to_string(e_scooters));
    }

    void CSVRepo::removeElectricScooter(std::shared_ptr<domain::ElectricScooter> e_scooter){
        auto e_scooters = loadFromFile();
        e_scooters.erase(std::remove_if(e_scooters.begin(),e_scooters.end(),[&e_scooter](const std::shared_ptr<domain::ElectricScooter>& scooter)
                {return e_scooter->getId() == scooter->getId(); }),e_scooters.end());
        saveToFile(convert_to_string(e_scooters));
    }

    void CSVRepo::updateElectricScooter(std::shared_ptr<domain::ElectricScooter> e_scooter, int new_kilometers, const std::string& new_location, int new_state){
        auto e_scooters = loadFromFile();
        auto it = std::find_if(e_scooters.begin(), e_scooters.end(), [&e_scooter](const std::shared_ptr<domain::ElectricScooter>& scooter){return e_scooter->getId() == scooter->getId();});
        if(new_kilometers > (*it)->getKilometers()){
            (*it)->setKilometers(new_kilometers);
        }
        //check if the given location is empty
        if(!new_location.empty()){
            (*it)->setLastLocation(new_location);
        }
        (*it)->setState(new_state);
        saveToFile(convert_to_string(e_scooters));
    }

    void CSVRepo::changeStateToReserved(std::shared_ptr<domain::ElectricScooter> e_scooter) {
        auto e_scooters = loadFromFile();
        auto it = std::find_if(e_scooters.begin(), e_scooters.end(), [&e_scooter](const std::shared_ptr<domain::ElectricScooter>& scooter){return e_scooter->getId() == scooter->getId();});
        (*it)->setState(domain::reserved);
        saveToFile(convert_to_string(e_scooters));

    }

    void CSVRepo::changeStateToInUse(std::shared_ptr<domain::ElectricScooter> e_scooter){
        auto e_scooters = loadFromFile();
        auto it = std::find_if(e_scooters.begin(), e_scooters.end(), [&e_scooter](const std::shared_ptr<domain::ElectricScooter>& scooter){return e_scooter->getId() == scooter->getId();});
        (*it)->setState(domain::in_use);
        saveToFile(convert_to_string(e_scooters));

    }

    std::vector<std::shared_ptr<domain::ElectricScooter>> CSVRepo::getAll(){
        return loadFromFile();
    }
}