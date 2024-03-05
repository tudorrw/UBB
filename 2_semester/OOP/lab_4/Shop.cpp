#include "FruitUI.h"
#include "FruitTest.h"

int main(){

    std::shared_ptr<repository::FruitRepo> repo = std::make_shared<repository::FruitRepo>();
    controller::FruitController controller(repo);
    UI::FruitUI app(controller);

    controller.add("banana", "australia", "3/5/2023", 30, 4);
    controller.add("orange", "turkey", "23/4/2023", 50, 2);
    controller.add("kiwi", "uruguay", "30/4/2023", 20, 6);
    controller.add("banana", "bolivia", "10/4/2023", 18, 3);
    controller.add("banana", "australia", "3/5/2023", 90, 4);
    controller.add("apfel", "romania", "9/7/2023", 300, 3);
    controller.add("birne", "romania", "3/6/2023", 240, 4);
    controller.add("aprikosen", "serbia", "1/5/2023", 100, 7);
    controller.add("pflaumen", "bulgaria", "28/5/2023", 150, 5);
    controller.add("kirschen", "slovakia", "4/5/2023", 70, 9);

    app.FruitApp();
    tests::FruitTest test;
    test.AllTests();
    return 0;
}
