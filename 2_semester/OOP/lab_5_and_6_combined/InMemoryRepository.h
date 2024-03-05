#ifndef L5_TEAMCCB_INMEMORYREPOSITORY_H
#define L5_TEAMCCB_INMEMORYREPOSITORY_H

#include "Repository.h"

namespace repository{
    class InMemoryRepo : public ElectricScooterRepo{
    private:
        std::vector<std::shared_ptr<domain::ElectricScooter>> fleeting_scooters; /**< Vector to store electric scooters. */

        std::string test;
        /**
         * Inserting 10 scooters at the beggining
         */
        void insert_scooters();
    public:
        /**
         * constructor for tests
         */
        InMemoryRepo(const std::string &test);

        /**
         * constructor for the actual fleeting part
         */
        InMemoryRepo();
        /**
         * @brief Adds an electric scooter to the fleet.
         * @param e_scooter Shared pointer to an ElectricScooter object.
         */
        void addElectricScooter(std::shared_ptr<domain::ElectricScooter> e_scooter) override;

        /**
         * @brief Removes an electric scooter from the fleet. If the element does not exist, the list remains unchanged.
         * @param e_scooter Shared pointer to an ElectricScooter object.
         */
        void removeElectricScooter(std::shared_ptr<domain::ElectricScooter> e_scooter) override;

        /**
         * @brief Updates an electric scooter in the fleet. If the element does not exist, it remains unchanged.
         * @param e_scooter Shared pointer to an ElectricScooter object.
         * @param new_kilometers New value for kilometers traveled.
         * @param new_location New location for the electric scooter.
         * @param new_state New state for the electric scooter.
         */
        void updateElectricScooter(std::shared_ptr<domain::ElectricScooter> e_scooter, int new_kilometers, const std::string& new_location, int new_state) override;

        /**
         * @brief Changes the current state of an element from "parked" to "reserved".
         * @param e_scooter Shared pointer to an ElectricScooter object.
         */
        void changeStateToReserved(std::shared_ptr<domain::ElectricScooter> e_scooter) override;

        /**
         * @brief Changes the current state of an element from "parked" to "in use".
         * @param e_scooter Shared pointer to an ElectricScooter object.
         */
        void changeStateToInUse(std::shared_ptr<domain::ElectricScooter> e_scooter) override;

        /**
         * @brief Retrieves the list of electric scooters.
         * @return Vector containing shared pointers to ElectricScooter objects.
         */
        std::vector<std::shared_ptr<domain::ElectricScooter>> getAll() override;


    };
}

#endif //L5_TEAMCCB_INMEMORYREPOSITORY_H
