#include "Domain.h"
namespace domain{
    ElectricScooter::ElectricScooter(std::string id_, std::string model_, std::string commissioning_date_, int kilometers_, std::string last_location_, int state_)
            : id(id_), model(model_), commissioning_date(commissioning_date_), kilometers(kilometers_), last_location(last_location_), state(static_cast<State>(state_)){}

    std::string ElectricScooter::getId() const{
        return this->id;
    }

    std::string ElectricScooter::getModel() const {
        return this->model;
    }

    std::string ElectricScooter::getCommissioningDate() const {
        return this->commissioning_date;
    }

    int ElectricScooter::getKilometers() const {
        return this->kilometers;
    }

    std::string ElectricScooter::getLastLocation() const {
        return this->last_location;
    }

    domain::State ElectricScooter::getStateOfScooter() const {
        return this->state;
    }

    void ElectricScooter::setKilometers(int new_kilometers){
        this->kilometers = new_kilometers;
    }

    void ElectricScooter::setLastLocation(std::string new_location) {
        this->last_location = new_location;
    }

    void ElectricScooter::setState(int new_state) {
        this->state = static_cast<State>(new_state);
    }

    std::ostream & operator << (std::ostream & out, const ElectricScooter &e_scooter){
        out << e_scooter.getId() << "," << e_scooter.getModel() << "," << e_scooter.getCommissioningDate() << "," << e_scooter.getKilometers() << "," << e_scooter.getLastLocation() << "," << e_scooter.getStateOfScooter();
        return out;
    }
}