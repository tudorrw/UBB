#ifndef L5_TEAMCCB_REPOSITORY_H
#define L5_TEAMCCB_REPOSITORY_H

#include <vector>
#include <memory>
#include <algorithm>
#include "Domain.h"

namespace repository {
    /**
     * @brief Repository class for managing electric scooters.
     */
    class ElectricScooterRepo {
    public:
        /**
         * @brief Adds an electric scooter to the fleet.
         * @param e_scooter Shared pointer to an ElectricScooter object.
         */
        virtual void addElectricScooter(std::shared_ptr<domain::ElectricScooter> e_scooter) = 0;

        /**
         * @brief Removes an electric scooter from the fleet. If the element does not exist, the list remains unchanged.
         * @param e_scooter Shared pointer to an ElectricScooter object.
         */
        virtual void removeElectricScooter(std::shared_ptr<domain::ElectricScooter> e_scooter) = 0;

        /**
         * @brief Updates an electric scooter in the fleet. If the element does not exist, it remains unchanged.
         * @param e_scooter Shared pointer to an ElectricScooter object.
         * @param new_kilometers New value for kilometers traveled.
         * @param new_location New location for the electric scooter.
         * @param new_state New state for the electric scooter.
         */
        virtual void updateElectricScooter(std::shared_ptr<domain::ElectricScooter> e_scooter, int new_kilometers, const std::string& new_location, int new_state) = 0;

        /**
         * @brief Changes the current state of an element from "parked" to "reserved".
         * @param e_scooter Shared pointer to an ElectricScooter object.
         */
        virtual void changeStateToReserved(std::shared_ptr<domain::ElectricScooter> e_scooter) = 0;

        /**
         * @brief Changes the current state of an element from "parked" to "in use".
         * @param e_scooter Shared pointer to an ElectricScooter object.
         */
        virtual void changeStateToInUse(std::shared_ptr<domain::ElectricScooter> e_scooter) = 0;

        /**
         * @brief Retrieves the list of electric scooters.
         * @return Vector containing shared pointers to ElectricScooter objects.
         */
        virtual std::vector<std::shared_ptr<domain::ElectricScooter>> getAll() = 0;
    };

}
#endif //L5_TEAMCCB_REPOSITORY_H
