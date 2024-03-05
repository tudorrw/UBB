#include "UI.h"
#include <sstream>
using namespace std;

namespace ui{

    ElectricScooterUI::ElectricScooterUI(controller::Controller controller_) : controller(controller_){}

    string ElectricScooterUI::menuForManager(){
        return "1. Add an electric scooter\n"
               "2. Delete an electric scooter by its ID\n"
               "3. Edit an electric scooter by its ID\n"
               "4. Search for electric scooters by location\n"
               "5. Filter electric scooters by kilometers\n"
               "6. Filter electric scooters by age\n"
               "7. Sort electric scooters by ascending age\n"
               "8. Exit\n";
    }

    string ElectricScooterUI::menuForCustomer() {
        return "1. Search for electric scooters by location\n"
               "2. Filter electric scooters by kilometers\n"
               "3. Filter electric scooters by age\n"
               "4. Reserve or use an electric scooter\n"
               "5. See reserved electric scooters\n"
               "6. Reserve one or more electric scooters\n"
               "7. See your reserved electric scooters\n"
               "8. Exit\n";
    }

    string ElectricScooterUI::userSelection(){
        return "Choose a role\n"
               "1. Manager\n"
               "2. Customer\n"
               "3. Exit\n";

    }

    void ElectricScooterUI::runApp() {

        bool quit = false;
        while(!quit){
            cout << userSelection();
            cout << "Choose an option: ";
            int app_option;
            string str_option;
            cin >> str_option;

            exceptionHandlingFunction(str_option, app_option);
            switch(app_option){
                case 1:
                    runForManager();
                    break;
                case 2:
                    runForCustomer();
                    break;
                case 3:
                    cout << "The program has ended!" << endl;
                    quit = true;
                    break;
                default:
                    cout << "Invalid Option! " << endl;
                    break;
            }
        }
    }

    void ElectricScooterUI::runForManager() {
        bool quit = false;
        while(!quit){

            cout << menuForManager();
            cout << "Choose an option: ";

            int option_manager;
            string str_option;
            cin >> str_option;

            exceptionHandlingFunction(str_option, option_manager);

            switch(option_manager) {
                case 1:
                    addE_Scooter();
                    break;
                case 2:
                    removeE_Scooter();
                    break;
                case 3:
                    updateE_Scooter();
                    break;
                case 4:
                    searchE_ScootersByLocation();
                    break;
                case 5:
                    filterE_ScootersByKilometers();
                    break;
                case 6:
                    filterE_ScootersByAge();
                    break;
                case 7:
                    printListOfE_Scooters(this->controller.sortByDate());
                    break;
                case 8:
                    quit = true;
                    break;
                default:
                    cout << "Invalid Option! " << endl;
                    break;
            }
        }
    }

    void ElectricScooterUI::runForCustomer() {
        bool quit = false;
        while(!quit){

            cout << menuForCustomer();
            cout << "Choose an option: ";

            int customer_option;
            string str_option;
            cin >> str_option;

            exceptionHandlingFunction(str_option, customer_option);

            switch(customer_option){
                case 1:
                    searchE_ScootersByLocation();
                    break;
                case 2:
                    filterE_ScootersByKilometers();
                    break;
                case 3:
                    filterE_ScootersByAge();
                    break;
                case 4:
                    changeStateOfE_Scooter();
                    break;
                case 5:
                    printListOfE_Scooters(this->controller.showE_Scooters(1));
                    break;
                case 6:
                    reserveEScooter();
                    break;
                case 7:
                    seeReservedScooters();
                    break;
                case 8:
                    quit = true;
                    break;
                default:
                    cout << "Invalid Option! " << endl;
                    break;
            }
        }
    }

    bool ElectricScooterUI::containOnlyLetters(const string &id){
        auto it = find_if(id.begin(), id.end(), [](const char &c) {return !isalpha(c); });
        return it == id.end();
    }

    void ElectricScooterUI::addE_Scooter() {
        string id, model, last_location, str_state;
        string year, month, day;
        int test_year, test_month, test_day, kilometers, state;
        string str_kilometers;

        cin.ignore();

        cout << "Enter an ID for the electric scooter:";
        getline(cin, id);
        if(id.length()!=3 || !containOnlyLetters(id)) {
            cout << "Invalid id" << endl;
            return;
        }
        cout << "Enter the model of the electric scooter:";
        getline(cin, model);

        cout << "Enter the commissioning date of the electric scooter:\n";
        cout << "Year: ";
        getline(cin, year);
        exceptionHandlingFunction(year, test_year);
        if(test_year < 2020 || test_year > 2023){
            cout << "Invalid year given!" << endl;
            return;
        }
        cout << "Month: ";
        getline(cin, month);
        exceptionHandlingFunction(month, test_month);
        if(test_month < 1 || test_month > 12){
            cout << "Invalid month given!" << endl;
            return;
        }
        cout << "Day: ";
        getline(cin, day);
        exceptionHandlingFunction(day, test_day);
        if(test_day < 1 || test_day > 31){
            cout << "Invalid day given!" << endl;
            return;
        }
        string commissioning_date = to_string(test_year) + "-" + to_string(test_month) + "-" + to_string(test_day);
        cout << "Enter the kilometer of the electric scooter:";
        getline(cin, str_kilometers);
        exceptionHandlingFunction(str_kilometers, kilometers);
        if(kilometers < 0){
            cout << "Invalid number of kilometers given!" << endl;
            return;
        }
        cout << "Enter the last location of the electric scooter:";
        getline(cin, last_location);
        cout << "Type the state of the new scooter (1.parked, 2.reserved, 3.in use, 4.in maintenance, 5.out of service):";
        getline(cin, str_state);
        exceptionHandlingFunction(str_state, state);
        if(state == -1){
            cout << "Invalid state given!" << endl;
            return;
        }
        if(this->controller.add(id, model, commissioning_date, kilometers, last_location,static_cast<domain::State>(state - 1))){
            cout << "The electric scooter was successfully added!" << endl;
        }
        else{
            cout << "The electric scooter exists in the fleet!" << endl;
        }
    }

    void ElectricScooterUI::removeE_Scooter() {
        string id;
        cin.ignore();
        cout << "Enter an ID for the electric scooter:";
        getline(cin, id);
        if(id.length()!=3 || !containOnlyLetters(id)) {
            cout << "Invalid id" << endl;
            return;
        }
        if(this->controller.remove(id)){
            cout << "The electric scooter was successfully removed!" << endl;
        }
        else{
            cout << "The electric scooter doesn't exist in the fleet!" << endl;
        }
    }

    void ElectricScooterUI::updateE_Scooter() {
        string id, str_new_kilometers, new_location, str_state;
        int new_kilometers, state;
        cin.ignore();
        cout << "Enter an ID for the electric scooter:";
        getline(cin, id);
        if(id.length()!=3 || !containOnlyLetters(id)) {
            cout << "Invalid id" << endl;
            return;
        }
        cout << "Enter the new kilometer of the electric scooter:";

        getline(cin, str_new_kilometers);
        exceptionHandlingFunction(str_new_kilometers, new_kilometers);
        if(new_kilometers == -1){
            cout << "Invalid kilometers given!" << endl;
            return;
        }
        cout << "Enter the new last location of the electric scooter:";

        getline(cin, new_location);
        cout << "Type the state of the new scooter (1.parked, 2.reserved, 3.in use, 4.in maintenance, 5.out of service):";
        getline(cin, str_state);
        exceptionHandlingFunction(str_state, state);
        if(state < 1 || state > 5){
            cout << "Invalid state given!" << endl;
            return;
        }

        if(this->controller.update(id, new_kilometers, new_location, static_cast<domain::State>(state - 1))){
            cout << "The electric scooter was processed successfully!" << endl;
        }
        else{
            cout << "The electric scooter doesn't exist in the fleet!" << endl;
        }
    }

    void ElectricScooterUI::printListOfE_Scooters(const vector<shared_ptr<domain::ElectricScooter>>& list) {
        cout << "Bestimmte E-Scooters:" << endl;
        for(auto it : list){
            cout << *it << endl;
        }
    }

    void ElectricScooterUI::searchE_ScootersByLocation(){
        string last_location;
        cout << "Enter a location to search for electric scooters:";
        cin.ignore();
        getline(cin, last_location);
        vector<shared_ptr<domain::ElectricScooter>> matches = this->controller.searchByLocation(last_location);
        printListOfE_Scooters(matches);
    }



    void ElectricScooterUI::filterE_ScootersByKilometers(){
        string str_kilometers;
        int kilometers;
        cin.ignore();
        cout << "Enter a value for kilometers: ";
        getline(cin, str_kilometers);
        exceptionHandlingFunction(str_kilometers, kilometers);
        if(kilometers == -1) {
            cout << "Invalid kilometers given!" << endl;
            return;
        }

        string str_option;
        int int_option;
        cout << "Give an option(1 for higher, 2 for lower): ";
        getline(cin, str_option);
        exceptionHandlingFunction(str_option, int_option);
        if(int_option == -1){
            cout << "Invalid option!" << endl;
            return;
        }
        vector<shared_ptr<domain::ElectricScooter>> matches = this->controller.filterByKilometers(kilometers, int_option);
        printListOfE_Scooters(matches);
    }

    void ElectricScooterUI::filterE_ScootersByAge(){
        int int_age;
        string str_age;
        cout << "Enter a value for age: ";
        cin.ignore();
        getline(cin, str_age);

        exceptionHandlingFunction(str_age, int_age);
        if(int_age == -1){
            cout << "Invalid age given! " << endl;
            return;
        }
        string str_option;
        int int_option;
        cout << "Give an option(1 for higher, 2 for lower): ";
        getline(cin, str_option);
        exceptionHandlingFunction(str_option, int_option);
        if(int_option == -1){
            cout << "Invalid option!" << endl;
            return;
        }
        vector<shared_ptr<domain::ElectricScooter>> matches = this->controller.filterByAge(int_age, int_option);
        printListOfE_Scooters(matches);
    }


    void ElectricScooterUI::changeStateOfE_Scooter(){
        cout << "Available electric scooters" << endl;
        printListOfE_Scooters(this->controller.showE_Scooters(0));
        string id;
        cin.ignore();
        cout << "Enter the ID of the electric scooter:";
        getline(cin, id);
        if(id.length()!=3 || !containOnlyLetters(id)) {
            cout<<"Invalid id!";
            return;
        }
        string str_state;
        int new_state;
        cout << "Change the state of the electric scooters (1. reserve, 2. use):";
        getline(cin, str_state);
        exceptionHandlingFunction(str_state, new_state);

        if(!this->controller.changeState(id, new_state)){
            cout << "Invalid state or id!" << endl;
        }
    }


    void ElectricScooterUI::exceptionHandlingFunction(const string &str_value, int &int_value){
        try{
            if(!str_value.empty()){
                int_value = stoi(str_value);
            }
        } catch (invalid_argument &e){
            int_value = -1;
        }
    }


// Function to split a string based on a delimiter
    vector<string> split(const string& str, char delimiter) {
        vector<string> tokens;
        stringstream ss(str);
        string token;
        while (getline(ss, token, delimiter)) {
            tokens.push_back(token);
        }
        return tokens;
    }

    void ElectricScooterUI::reserveEScooter() {
        // Retrieve and print the list of available scooters
        vector<shared_ptr<domain::ElectricScooter>> availableScooters = this->controller.showE_Scooters(0);
        cout << "Available Electric Scooters:" << endl;
        printListOfE_Scooters(availableScooters);

        string ids;
        cin.ignore();
        cout << "Enter the IDs of the electric scooters you want to reserve (separated by commas): ";
        getline(cin, ids);

        vector<string> idList = split(ids, ',');

        bool allIdsValid = true;
        for (const string& id : idList) {
            if (id.length() != 3 || !containOnlyLetters(id)) {
                allIdsValid = false;
                cout << "Invalid ID: " << id << endl;
            }
        }

        if (!allIdsValid) {
            cout << "One or more IDs are invalid!" << endl;
            return;
        }

        vector<string> reservedIds;
        for (const string& id : idList) {
            if (this->controller.reserveScooters({id})) {
                reservedIds.push_back(id);
            }
        }

        if (!reservedIds.empty()) {
            cout << "The following electric scooters have been successfully reserved:" << endl;
            for (const string& id : reservedIds) {
                cout << "ID: " << id << endl;
            }
        } else {
            cout << "Failed to reserve any electric scooters. They may be already reserved or not found." << endl;
        }
    }

    void ElectricScooterUI::seeReservedScooters() {
        std::vector<std::shared_ptr<ElectricScooter>> reserved=this->controller.seeReservedScootersUser();
        cout<<"Your reserved scooters: "<<endl;
        for (const auto& it : reserved)
        {
            cout<<*it<<endl;
        }

    }

}