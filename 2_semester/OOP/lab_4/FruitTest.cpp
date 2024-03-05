#include "FruitTest.h"

namespace tests {
    void FruitTest::add_controller() {
        std::shared_ptr<repository::FruitRepo> repoTest = std::make_shared<repository::FruitRepo>();
        controller::FruitController ctrlTest(repoTest);
        assert(ctrlTest.add("kirschen", "slovakia", "4/5/2023", 70, 8) == 1);
        assert(ctrlTest.add("kirschen", "slovakia", "4/5/2023", 130, 8) == 0);
        assert(ctrlTest.getSize() == 1);
        std::cout << "add_controller() tests passed" << std::endl;
    }

    void FruitTest::remove_controller() {
        std::shared_ptr<repository::FruitRepo> repoTest = std::make_shared<repository::FruitRepo>();
        controller::FruitController ctrlTest(repoTest);
        ctrlTest.add("aprikosen", "italien", "29/4/2023", 100, 5);
        ctrlTest.remove("aprikosen", "kroatien");
        assert(ctrlTest.getSize() == 1);
        ctrlTest.remove("aprikosen", "italien");
        assert(ctrlTest.getSize() == 0);
        std::cout << "remove_controller() tests passed" << std::endl;
    }

    void FruitTest::AllTests() {
        add_controller();
        remove_controller();
    }
}