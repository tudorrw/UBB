#ifndef L5_TEAMCCB_CSVREPOSITORY_H
#define L5_TEAMCCB_CSVREPOSITORY_H
#include <fstream>
#include <sstream>
#include "Repository.h"

namespace repository{

    class CSVRepo : public ElectricScooterRepo{
    private:
        std::string csv_file;
    public:
        /**
         * @brief Constructs a CSVRepo object with the specified CSV file name.
         * @param file_ The name of the CSV file.
         */
        CSVRepo(const std::string &file_);
        /**
        * @brief Adds an ElectricScooter object to the repository and saves it to the CSV file.
        * @param e_scooter The ElectricScooter object to add.
        */
        void addElectricScooter(std::shared_ptr<domain::ElectricScooter> scooter) override;

        /**
         * @brief Removes an ElectricScooter object from the repository and updates the CSV file.
         * @param e_scooter The ElectricScooter object to remove.
         */
        void removeElectricScooter(std::shared_ptr<domain::ElectricScooter> scooter) override;

        /**
         * @brief Loads ElectricScooter objects from the CSV file.
         * @return A vector of shared pointers to the loaded ElectricScooter objects.
         */
        void updateElectricScooter(std::shared_ptr<domain::ElectricScooter> scooter, int new_kilometers, const std::string& new_location, int new_state) override;

        /**
         * @brief Retrieves all ElectricScooter objects from the repository.
         * @return A vector of shared pointers to the ElectricScooter objects.
         */
        std::vector<std::shared_ptr<domain::ElectricScooter>> getAll() override;

        /**
         * @brief Changes the state of an ElectricScooter object to "Reserved" and updates the CSV file.
         * @param e_scooter The ElectricScooter object to update.
         */
        void changeStateToReserved(std::shared_ptr<domain::ElectricScooter> e_scooter) override;

        /**
         * @brief Changes the state of an ElectricScooter object to "In Use" and updates the CSV file.
         * @param e_scooter The ElectricScooter object to update.
         */
        void changeStateToInUse(std::shared_ptr<domain::ElectricScooter> e_scooter) override;

        /**
         * @brief Saves the string representation of ElectricScooter objects to the CSV file.
         * @param data The string representation of ElectricScooter objects in CSV format.
         */
        void saveToFile(const std::string &data);

        /**
        * @brief Loads ElectricScooter objects from the CSV file.
        * @return A vector of shared pointers to the loaded ElectricScooter objects.
        */
        std::vector<std::shared_ptr<domain::ElectricScooter>> loadFromFile();

        /**
        * @brief Converts a string representation of an ElectricScooter in CSV format to an ElectricScooter object.
        * @param line The string representation of the ElectricScooter in CSV format.
        * @return A shared pointer to the ElectricScooter object.
        */
        static std::string convert_to_string(const std::vector<std::shared_ptr<domain::ElectricScooter>>& e_scooters);

        /**
        * @brief Converts a string representation of an ElectricScooter in CSV format to an ElectricScooter object.
        * @param line The string representation of the ElectricScooter in CSV format.
        * @return A shared pointer to the ElectricScooter object.
        */
        static std::shared_ptr<domain::ElectricScooter> convert_from_string(const std::string& line);

    };
}

#endif //L5_TEAMCCB_CSVREPOSITORY_H
