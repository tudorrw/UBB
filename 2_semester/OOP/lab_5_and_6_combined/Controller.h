#ifndef L5_TEAMCCB_CONTROLLER_H
#define L5_TEAMCCB_CONTROLLER_H
#include <sstream>
#include "Repository.h"
using repository::ElectricScooterRepo;
using domain::ElectricScooter;

namespace controller {

    /**
     * Controller class for managing Electric Scooters.
     */
    class Controller {
    private:
        std::shared_ptr<ElectricScooterRepo> repo;
        std::vector<std::string> reservedIds;
        /**
         * Compares two dates given as strings and converts them to integers.
         * @param date1 The first date string.
         * @param date2 The second date string.
         * @return True if date1 is greater than or equal to date2, false otherwise.
         */
        static bool compareDates(const std::string &date1, const std::string &date2);

    public:
        /**
         * Konstruktor
         * @param repo_
         */
        Controller(std::shared_ptr<repository::ElectricScooterRepo> repo_);


        /**
         * Searches for an Electric Scooter with a specific ID in the repository's database.
         * @param id_ The ID to search for.
         * @return A shared pointer to the corresponding ElectricScooter object, or nullptr if not found.
         */
        std::shared_ptr<ElectricScooter> foundElement(const std::string &id_) const;

        /**
         * Adds an ElectricScooter object to the database if the ID is not already taken.
         * @param id_ The ID of the ElectricScooter.
         * @param model_ The model of the ElectricScooter.
         * @param commissioning_date_ The commissioning date of the ElectricScooter.
         * @param kilometers_ The number of kilometers of the ElectricScooter.
         * @param last_location_ The last known location of the ElectricScooter.
         * @param state_ The state of the ElectricScooter.
         * @return True if the element is added successfully, false otherwise.
         */
        bool add(const std::string& id_, const std::string& model_, const std::string& commissioning_date_,
                 int kilometers_, const std::string& last_location_, domain::State state_);

        /**
         * Removes an ElectricScooter object with a specific ID from the database.
         * @param id_ The ID of the ElectricScooter to remove.
         * @return True if the element is removed successfully, false otherwise.
         */
        bool remove(const std::string &id_);

        /**
         * Updates the kilometers, last location, and state of an ElectricScooter object with a specific ID.
         * @param id_ The ID of the ElectricScooter to update.
         * @param new_kilometers The new number of kilometers.
         * @param new_location The new last known location.
         * @param new_state The new state.
         * @return True if the element is updated successfully, false otherwise.
         */
        bool update(const std::string &id_, int new_kilometers, const std::string &new_location, int new_state);

        /**
         * Searches for all ElectricScooters in the database that were last parked at a specific location.
         * @param last_location The last known location to search for.
         * @return A vector containing all ElectricScooters matching the search criteria.
         */
        std::vector<std::shared_ptr<ElectricScooter>> searchByLocation(const std::string &last_location);

        /**
         * Filters all ElectricScooters in the database whose kilometers are below a specified value.
         * @param kilometers The kilometers threshold.
         * @param option The filtering option (1: greater than or equal to, 2: less than).
         * @return A vector containing the matching ElectricScooters.
         */
        std::vector<std::shared_ptr<ElectricScooter>> filterByKilometers(int kilometers, int option);

        /**
         * Sorts the list of ElectricScooters by their commissioning date.
         * @return A vector containing the sorted list.
         */
        std::vector<std::shared_ptr<ElectricScooter>> sortByDate();

        /**
         * Retrieves all ElectricScooters that are in the "parked" or "reserved" state.
         * @return A vector containing all parked ElectricScooters.
         */
        std::vector<std::shared_ptr<ElectricScooter>> showE_Scooters(int option);

        /**
         * Changes the state of an ElectricScooter in the database.
         * @param id The ID of the ElectricScooter to change the state.
         * @param state The state to change to.
         * @return True if the state change is successful, false otherwise.
         */
        bool changeState(const std::string &id, int state);

        /**
         * Filters all ElectricScooters in the database whose age is above or below a specified value.
         * @param age The age threshold.
         * @param option The filtering option (1: greater than or equal to, 2: less than).
         * @return A vector containing the matching ElectricScooters.
         */
        std::vector<std::shared_ptr<ElectricScooter>> filterByAge(int age, int option);

        /**
         * Retrieves the year of the ElectricScooter object.
         * @param electricScooter The ElectricScooter object.
         * @return The year of the ElectricScooter.
         */
        int getYear(const std::shared_ptr<ElectricScooter> &electricScooter);

        /**
         * Reserving scooters
         * @param scooterIds  The ids of the scooter that we want to reserve
         * @return true if it succeded
         */
        bool reserveScooters(const std::vector<std::string> &scooterIds);

        std::vector<std::shared_ptr<ElectricScooter>> seeReservedScootersUser();
    };
}
#endif //L5_TEAMCCB_CONTROLLER_H
