#ifndef L5_TEAMCCB_DOMAIN_H
#define L5_TEAMCCB_DOMAIN_H
#include <iostream>
#include <memory>

namespace domain {

    enum State { parked, reserved, in_use, in_maintenance, out_of_service };

    class ElectricScooter {
    private:
        std::string id;
        std::string model;
        std::string commissioning_date;
        int kilometers;
        std::string last_location;
        State state;
    public:
        /** Konstruktor
         * requires 6 attributes as parameters
         * @param id_, model_, commissioning_date_, kilometers_, last_location_, state_
         */
        ElectricScooter(std::string id_, std::string model_, std::string commissioning_date_, int kilometers_,
                        std::string last_location_, int state_);
        /**
        * Getter id
        * @return id Scooter
        */
        std::string getId() const;

        /**
        * Getter model
        * @return model Scooter
        */
        std::string getModel() const;

        /**
        * Getter commissioning_date
        * @return commissioning_date Scooter
        */
        std::string getCommissioningDate() const;

        /**
        * Getter kilometers
        * @return kilometers Scooter
        */
        int getKilometers() const;

        /**
        * Getter last_location
        * @return last_location Scooter
        */
        std::string getLastLocation() const;

        /**
        * Getter state
        * @return state Scooter
        */
        State getStateOfScooter() const;

        /**
         * Setter kilometers
         * @param new_kilometers Parameter,
         */
        void setKilometers(int new_kilometers);

        /**
         * Setter last_location
         * @param new_location Parameter
         */
        void setLastLocation(std::string new_location);

        /**
         * Setter state
         * @param new_state Parameter
         */
        void setState(int new_state);

        /**
         * Out operator
         */
        friend std::ostream &operator<<(std::ostream &out, const ElectricScooter &e_scooter);


    };
}

#endif //L5_TEAMCCB_DOMAIN_H
