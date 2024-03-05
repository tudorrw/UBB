#ifndef L5_TEAMCCB_UI_H
#define L5_TEAMCCB_UI_H

#include <string>
#include <iostream>
#include "Controller.h"

namespace ui {

    /**
     * User interface class for Electric Scooter management.
     */
    class ElectricScooterUI {
    private:
        controller::Controller controller;
        /**
         * Returns the menu for the manager.
         * @return The menu as a string.
         */
        static std::string menuForManager();

        /**
         * Returns the menu for the customer.
         * @return The menu as a string.
         */
        static std::string menuForCustomer();

        /**
         * Returns the menu for user selection.
         * @return The menu as a string.
         */
        static std::string userSelection();

        /**
         * Starts the application for the manager.
         */
        void runForManager();

        /**
         * Starts the application for the customer.
         */
        void runForCustomer();

        /**
         * Prints the list of Electric Scooters.
         * @param list The list of Electric Scooters.
         */
        static void printListOfE_Scooters(const std::vector<std::shared_ptr<domain::ElectricScooter>>& list);

        /**
         * Adds an Electric Scooter.
         */
        void addE_Scooter();

        /**
         * Removes an Electric Scooter.
         */
        void removeE_Scooter();

        /**
         * Updates the information of an Electric Scooter.
         */
        void updateE_Scooter();

        /**
         * Searches for Electric Scooters by location.
         */
        void searchE_ScootersByLocation();

        /**
         * Filters Electric Scooters with kilometers greater than the specified value.
         */
        void filterE_ScootersByKilometers();

        /**
         * Filters Electric Scooters with age less than the specified value.
         */
        void filterE_ScootersByAge();

        /**
         * Changes the state of an Electric Scooter.
         */
        void changeStateOfE_Scooter();

        /**
         * Exception handling function for converting string to int.
         * @param str_value The string value to convert.
         * @param int_value The resulting integer value.
         */
        static void exceptionHandlingFunction(const std::string &str_value, int &int_value);

        /**
         * Checks if a string contains only letters.
         * @param id The string to check.
         * @return True if the string contains only letters, false otherwise.
         */
        static bool containOnlyLetters(const std::string &id);

        /**
         * Show the reserved scooters for the current user
         */
        void seeReservedScooters();

        /**
        * Reserves electric scooters based on user input.
        */
        void reserveEScooter();

    public:

        /**
         * Konstructor
         * @param controller_ Controller fur die App
         */
        explicit ElectricScooterUI(controller::Controller controller_);

        /**
         * Starts the application.
         */
        void runApp();

    };
}
#endif //L5_TEAMCCB_UI_H
