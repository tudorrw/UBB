#include "Unit_Tests.h"
using repository::InMemoryRepo;
using domain::ElectricScooter;


namespace unit_tests{
    void Tests::test_add_electric_scooter() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");

        std::shared_ptr<ElectricScooter> scooter1 = std::make_shared<ElectricScooter>("abc", "Model X", "2022-01-01", 100, "Location A", domain::in_use);
        repo->addElectricScooter(scooter1);
        assert(repo->getAll().size() == 1);

        std::shared_ptr<ElectricScooter> scooter2 = std::make_shared<ElectricScooter>("def", "Model Y", "2022-02-01", 200, "Location B", domain::parked);
        repo->addElectricScooter(scooter2);
        assert(repo->getAll().size() == 2);

        std::shared_ptr<ElectricScooter> scooter3 = std::make_shared<ElectricScooter>("ghi", "Model Z", "2022-03-01", 300, "Location C", domain::reserved);
        repo->addElectricScooter(scooter3);
        assert(repo->getAll().size() == 3);
    }
    void Tests::test_remove_electric_scooter() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");

        std::shared_ptr<ElectricScooter> scooter1 = std::make_shared<ElectricScooter>("abc", "Model X", "2022-01-01", 100, "Location A", domain::in_use);
        std::shared_ptr<ElectricScooter> scooter2 = std::make_shared<ElectricScooter>("def", "Model Y", "2022-02-01", 200, "Location B", domain::parked);
        std::shared_ptr<ElectricScooter> scooter3 = std::make_shared<ElectricScooter>("ghi", "Model Z", "2022-03-01", 300, "Location C", domain::reserved);

        repo->addElectricScooter(scooter1);
        repo->addElectricScooter(scooter2);
        repo->addElectricScooter(scooter3);

        assert(repo->getAll().size() == 3);

        repo->removeElectricScooter(scooter2);
        assert(repo->getAll().size() == 2);

        repo->removeElectricScooter(scooter1);
        assert(repo->getAll().size() == 1);

        repo->removeElectricScooter(scooter3);
        assert(repo->getAll().empty());
    }

    void Tests::test_update_electric_scooter() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");

        std::shared_ptr<ElectricScooter> scooter = std::make_shared<ElectricScooter>("abc", "Model X", "2022-01-01", 100, "Location A", domain::in_use);
        repo->addElectricScooter(scooter);

        repo->updateElectricScooter(scooter, 200, "New Location", domain::parked);
        assert(scooter->getKilometers() == 200);
        assert(scooter->getLastLocation() == "New Location");
        assert(scooter->getStateOfScooter() == domain::parked);
    }

    void Tests::test_change_state_to_reserved() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");

        std::shared_ptr<ElectricScooter> scooter = std::make_shared<ElectricScooter>("abc", "Model X", "2022-01-01", 100, "Location A", domain::in_use);
        repo->addElectricScooter(scooter);

        repo->changeStateToReserved(scooter);

        assert(scooter->getStateOfScooter() == domain::reserved);
    }

    void Tests::test_change_state_to_in_use() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");

        std::shared_ptr<ElectricScooter> scooter = std::make_shared<ElectricScooter>("abc", "Model X", "2022-01-01", 100, "Location A", domain::reserved);
        repo->addElectricScooter(scooter);

        repo->changeStateToInUse(scooter);

        assert(scooter->getStateOfScooter() == domain::in_use);
    }

    void Tests::test_repo(){
        test_add_electric_scooter();
        test_remove_electric_scooter();
        test_update_electric_scooter();
        test_change_state_to_reserved();
        test_change_state_to_in_use();
    }

    void Tests::test_add() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller(repo);


        // Add a new electric scooter
        bool result = controller.add("abc", "Model X", "2022-01-01", 100, "Location A", domain::in_use);
        assert(result == true);

        // Try adding the same electric scooter again
        result = controller.add("abc", "Model Y", "2022-02-01", 200, "Location B", domain::reserved);
        assert(result == false);
    }

    void Tests::test_remove() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller(repo);

        // Add an electric scooter to the repository
        controller.add("abc", "Model X", "2022-01-01", 100, "Location A", domain::in_use);

        // Remove the electric scooter
        bool result = controller.remove("abc");
        assert(result == true);

        // Try removing the same electric scooter again
        result = controller.remove("abc");
        assert(result == false);
    }

    void Tests::test_update() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller(repo);

        // Add an electric scooter to the repository
        controller.add("abc", "Model X", "2022-01-01", 100, "Location A", domain::in_use);

        // Update the electric scooter
        bool result = controller.update("abc", 200, "Location B", 1);
        assert(result == true);

        // Try updating a non-existing electric scooter
        result = controller.update("def", 300, "Location C", 2);
        assert(result == false);
    }

    void Tests::test_search_by_location() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller(repo);

        // Add some electric scooters to the repository
        controller.add("abc", "Model X", "2022-01-01", 100, "Location A", domain::in_use);
        controller.add("def", "Model Y", "2022-02-01", 200, "Location B", domain::reserved);
        controller.add("ghi", "Model Z", "2022-03-01", 300, "Location C", domain::parked);

        // Search for electric scooters by location
        std::vector<std::shared_ptr<ElectricScooter>> results = controller.searchByLocation("Location B");

        assert(results.size() == 1);
        assert(results[0]->getId() == "def");

        // Search for electric scooters with empty location
        results = controller.searchByLocation("");

        assert(results.size() == 3);
    }

    void Tests::test_filter_by_kilometers() {
        std::shared_ptr<repository::ElectricScooterRepo> repo1 = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller1(repo1);
        // Add some electric scooters to the repository
        controller1.add("abc", "Model X", "2022-01-01", 100, "Location A", domain::in_use);
        controller1.add("def", "Model Y", "2022-02-01", 200, "Location B", domain::reserved);
        controller1.add("ghi", "Model Z", "2022-03-01", 300, "Location C", domain::parked);

        // Filter electric scooters by lower kilometers
        std::vector<std::shared_ptr<ElectricScooter>> results1 = controller1.filterByKilometers(250, 2);

        assert(results1.size() == 2);
        assert(results1[0]->getId() == "abc");
        assert(results1[1]->getId() == "def");

        std::shared_ptr<repository::ElectricScooterRepo> repo2 = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller2(repo2);

        // Add some electric scooters to the repository
        controller2.add("abc", "Model X", "2022-01-01", 100, "Location A", domain::in_use);
        controller2.add("def", "Model Y", "2022-02-01", 200, "Location B", domain::reserved);
        controller2.add("ghi", "Model Z", "2022-03-01", 300, "Location C", domain::parked);

        // Filter electric scooters by higher kilometers
        std::vector<std::shared_ptr<ElectricScooter>> results2 = controller2.filterByKilometers(150, 1);

        assert(results2.size() == 2);
        assert(results2[0]->getId() == "def");
        assert(results2[1]->getId() == "ghi");
    }

    void Tests::test_sort_by_date() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller(repo);

        // Add some electric scooters to the repository
        controller.add("abc", "Model X", "2023-01-01", 100, "Location A", domain::in_use);
        controller.add("def", "Model Y", "2021-03-01", 200, "Location B", domain::reserved);
        controller.add("ghi", "Model Z", "2022-02-01", 300, "Location C", domain::parked);

        // Sort electric scooters by commissioning date
        std::vector<std::shared_ptr<ElectricScooter>> results = controller.sortByDate();

        assert(results.size() == 3);
        assert(results[0]->getId() == "def");
        assert(results[1]->getId() == "ghi");
        assert(results[2]->getId() == "abc");
    }

    void Tests::test_show_parked_e_scooters() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller(repo);

        // Add some electric scooters to the repository
        controller.add("abc", "Model X", "2022-01-01", 100, "Location A", domain::in_use);
        controller.add("def", "Model Y", "2022-02-01", 200, "Location B", domain::reserved);
        controller.add("ghi", "Model Z", "2022-03-01", 300, "Location C", domain::parked);

        // Get the list of parked electric scooters
        std::vector<std::shared_ptr<ElectricScooter>> results = controller.showE_Scooters(0);


        assert(results.size() == 1);
        assert(results[0]->getId() == "ghi");
    }

    void Tests::test_change_state() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller(repo);

        // Add an electric scooter to the repository
        controller.add("abc", "Model X", "2022-01-01", 100, "Location A", domain::in_use);

        // Change the state of the electric scooter
        bool result = controller.changeState("abc", 1);

        assert(result == true);
        assert(controller.foundElement("abc")->getStateOfScooter() == domain::reserved);
    }

    void Tests::test_filter_by_age() {
        std::shared_ptr<repository::ElectricScooterRepo> repo1 = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller1(repo1);

        // Add some electric scooters to the repository
        controller1.add("abc", "Model X", "2019-01-01", 100, "Location A", domain::in_use);
        controller1.add("def", "Model Y", "2020-02-01", 200, "Location B", domain::reserved);
        controller1.add("ghi", "Model Z", "2021-03-01", 300, "Location C", domain::parked);

        // Filter electric scooters by lower age
        std::vector<std::shared_ptr<ElectricScooter>> results1 = controller1.filterByAge(3, 2);

        assert(results1.size() == 1);
        assert(results1[0]->getId() == "ghi");

        std::shared_ptr<repository::ElectricScooterRepo> repo2 = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller2(repo2);

        // Add some electric scooters to the repository
        controller2.add("abc", "Model X", "2019-01-01", 100, "Location A", domain::in_use);
        controller2.add("def", "Model Y", "2020-02-01", 200, "Location B", domain::reserved);
        controller2.add("ghi", "Model Z", "2022-03-01", 300, "Location C", domain::parked);

        // Filter electric scooters by higher age
        std::vector<std::shared_ptr<ElectricScooter>> results2 = controller2.filterByAge(2,1);
        assert(results2.size() == 2);
        assert(results2[0]->getId() == "abc");
        assert(results2[1]->getId() == "def");
    }

    void Tests::test_reserve() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller1(repo);

        // Add some electric scooters to the repository
        controller1.add("abc", "Model X", "2019-01-01", 100, "Location A", domain::in_use);
        controller1.add("def", "Model Y", "2020-02-01", 200, "Location B", domain::parked);
        controller1.add("ghi", "Model Z", "2021-03-01", 300, "Location C", domain::parked);

        std::vector<std::string> ids;
        ids.emplace_back("abc");
        ids.emplace_back("def");

        controller1.reserveScooters(ids);

        assert(controller1.foundElement("abc")->getStateOfScooter() == domain::reserved);
        assert(controller1.foundElement("def")->getStateOfScooter() == domain::reserved);
        assert(controller1.foundElement("ghi")->getStateOfScooter() != domain::reserved);
    }

    void Tests::test_see_reserved() {
        std::shared_ptr<repository::ElectricScooterRepo> repo = std::make_shared<InMemoryRepo>("test");
        controller::Controller controller1(repo);

        // Add some electric scooters to the repository
        controller1.add("abc", "Model X", "2019-01-01", 100, "Location A", domain::in_use);
        controller1.add("def", "Model Y", "2020-02-01", 200, "Location B", domain::parked);
        controller1.add("ghi", "Model Z", "2021-03-01", 300, "Location C", domain::parked);

        std::vector<std::string> ids;
        ids.emplace_back("abc");
        ids.emplace_back("def");

        controller1.reserveScooters(ids);

        std::vector<std::shared_ptr<ElectricScooter>> reserved=controller1.seeReservedScootersUser();

        assert(reserved[0]->getStateOfScooter() == domain::reserved);
        assert(reserved[1]->getStateOfScooter() == domain::reserved);
        assert(reserved.size()==2);
    }

    void Tests::test_csv_load_from_file() {
        std::shared_ptr<repository::CSVRepo> repo = std::make_shared<repository::CSVRepo>("test.csv");

        repo->loadFromFile();

        std::vector<std::shared_ptr<domain::ElectricScooter>> data=repo->getAll();

        assert(data[0]->getId()=="ERT");
        assert(data[1]->getId()=="EPC");
        assert(data[2]->getId()=="DFC");
    }

    void Tests::test_csv_convert_to_string() {
        std::shared_ptr<repository::CSVRepo> repo = std::make_shared<repository::CSVRepo>("test.csv");
        repo->loadFromFile();

        std::vector<std::shared_ptr<domain::ElectricScooter>> data=repo->getAll();
        assert(repo->convert_to_string(data)=="ERT,Varc,2021-3-5,1000,Str. Dorobantilor 7,3\nEPC,E-Scoot,2023-4-5,10,Str. Mangalia 6,4\nDFC,Scooty,2023-1-12,51,Str. Albini 3,2");

    }

    void Tests::test_csv_convert_from_string() {
        std::shared_ptr<repository::CSVRepo> repo = std::make_shared<repository::CSVRepo>("test.csv");

        std::shared_ptr<domain::ElectricScooter> scooter;

        scooter=repo->convert_from_string("ERT,Varc,2021-3-5,1000,Str. Dorobantilor 7,3");

        assert(scooter->getId()=="ERT");
        assert(scooter->getModel()=="Varc");
        assert(scooter->getCommissioningDate()=="2021-3-5");


    }

    void Tests::test_csv() {
        test_csv_load_from_file();
        test_csv_convert_to_string();
        test_csv_convert_from_string();
        std::cout << "Tests finished" << std::endl;
    }

    void Tests::test_controller(){
        test_add();
        test_remove();
        test_update();
        test_search_by_location();
        test_filter_by_kilometers();
        test_sort_by_date();
        test_show_parked_e_scooters();
        test_change_state();
        test_filter_by_age();
        test_reserve();
        test_see_reserved();

    }



}