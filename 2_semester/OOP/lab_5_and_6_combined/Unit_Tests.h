#ifndef L5_TEAMCCB_UNIT_TESTS_H
#define L5_TEAMCCB_UNIT_TESTS_H
#include "Controller.h"
#include "InMemoryRepository.h"
#include "CSVRepository.h"
#include <cassert>
#include <vector>
#include <memory>
namespace unit_tests{
    class Tests{
    private:
        /**
        * @brief Tests the addElectricScooter method in the repository.
        */
        static void test_add_electric_scooter();


        /**
         * @brief Tests the removeElectricScooter method in the repository.
         */
        static void test_remove_electric_scooter();


        /**
         * @brief Tests the updateElectricScooter method in the repository.
         */
        static void test_update_electric_scooter();


        /**
         * @brief Tests the changeElectricScooterState method in the repository.
         */
        static void test_change_state_to_reserved();


        /**
         * @brief Tests the change_state_to_in_use method from the repository.
        */
        static void test_change_state_to_in_use();


        /**
         * @brief Tests the add method from the controller.
         */
        static void test_add();


        /**
         * @brief Tests the add and remove methods from the controller.
         */
        static void test_remove();


        /**
         * @brief Tests the update method from the controller.
         */
        static void test_update();


        /**
         * @brief Tests the searchByLocation method from the controller.
         */
        static void test_search_by_location();


        /**
         * @brief Tests the filterByKilometers method from the controller.
         */
        static void test_filter_by_kilometers();


        /**
         * @brief Tests the sortByDate method from the controller.
         */
        static void test_sort_by_date();


        /**
         * @brief Tests the showParkedE_Scooters method from the controller.
         */
        static void test_show_parked_e_scooters();


        /**
         * @brief Tests the changeState method from the controller.
         */
        static void test_change_state();


        /**
         * @brief Tests the filterByAge method from the controller.
         */
        static void test_filter_by_age();

        /**
         * @brief Tests the reserveScooters method from the controller.
         */
        static void test_reserve();

        /**
        * @brief Tests the seeReservedScootersUser method from the controller.
        */
        static void test_see_reserved();

        /**
        * @brief Tests the load_from_file method to the csv_repository.
        */
        static void test_csv_load_from_file();

        /**
         * @brief Tests the convert_to_string method to the csv_repository.
         */
        static void test_csv_convert_to_string();

        /**
         * @brief Tests the convert_to_string method from the csv_repository.
         */
        static void test_csv_convert_from_string();

    public:


        /**
         * @brief Tests the methods from the repository.
         */
        static void test_repo();

        /**
        * @brief Tests the methods from the controller.
        */
        static void test_controller();



        /**
        * @brief Tests the methods from the csv_repository.
        */
        static void test_csv();
    };
}


#endif //L5_TEAMCCB_UNIT_TESTS_H
