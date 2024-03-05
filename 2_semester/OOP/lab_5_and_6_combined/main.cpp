#include "UI.h"
#include "Unit_Tests.h"

using namespace std;

const int FLEETING_FORMAT = 1;
const int PERSISTENT_FORMAT = 2;

void exceptionHandlingFunction(const string &str_value, int &int_value){
    try{
        if(!str_value.empty()){
            int_value = stoi(str_value);
        }
    } catch (invalid_argument &e){
        int_value = -1;
    }
}


int getFormatChoice() {
    string inputString;
    cin >> inputString;
    int formatChoice;
    exceptionHandlingFunction(inputString, formatChoice);
    return formatChoice;
}

std::shared_ptr<repository::ElectricScooterRepo> createRepository(int formatChoice) {
    if (formatChoice == FLEETING_FORMAT) {
        return std::make_shared<repository::InMemoryRepo>();
    } else if (formatChoice == PERSISTENT_FORMAT) {
        return std::make_shared<repository::CSVRepo>("scooters.csv");
    }
    return nullptr; // Return appropriate error handling or throw an exception
}

int main() {
    unit_tests::Tests::test_repo();
    unit_tests::Tests::test_controller();
    unit_tests::Tests::test_csv();

    cout << "Choose a format: " << endl;
    cout << FLEETING_FORMAT << ". fleeting" << endl;
    cout << PERSISTENT_FORMAT << ". persistent" << endl;
    cout << "Enter an option: ";

    int formatChoice = getFormatChoice();
    std::shared_ptr<repository::ElectricScooterRepo> repo = createRepository(formatChoice);

    if (!repo) {
        cout << "Invalid type of format!" << endl;
        return 0;
    }

    controller::Controller controller(repo);
    ui::ElectricScooterUI app(controller);
    app.runApp();
    return 0;
}