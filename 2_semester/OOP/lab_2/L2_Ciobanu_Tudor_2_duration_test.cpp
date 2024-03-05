#include "L2_Ciobanu_Tudor_2_duration.h"
#include <iostream>
#include <cassert>

using namespace std;

void add_duration_test(const Duration& dur1, const Duration& dur2){
    Duration sum = dur1.add(dur2);
    assert(sum.get_value() == 49.9 && sum.get_unit() == "h");
    cout << "add duration test passed" << endl;
    Duration operator_sum = dur1 + dur2;
    assert(operator_sum.get_value() == 49.9 && operator_sum.get_unit() == "h");
    cout << "add duration test with operator passed" << endl;
}

void subtract_duration_test(const Duration& dur1, const Duration& dur2) {
    Duration diff = dur1.subtract(dur2);
    assert(abs(diff.get_value()) - 18.7 < 0.0001 && diff.get_unit() == "h");
    cout << "subtract duration test passed" << endl;
    Duration operator_diff = dur1 - dur2;
    assert(abs(diff.get_value()) - 18.7 < 0.0001 && diff.get_unit() == "h");
    cout << "subtract duration test with operator passed" << endl;
}

void scale_duration_test(Duration dur1, double val){
    dur1.scale(val);
    assert(dur1.get_value() - 45.24 < 0.0001 && dur1.get_unit() == "h");
    cout << "scale duration test passed" << endl;
}

void scale_duration_test_2(Duration& dur1, double val){
    dur1 *= val;
    assert(dur1.get_value() - 45.24 < 0.0001 && dur1.get_unit() == "h");
    cout << "scale duration test with operator passed" << endl;
}

void divide_duration_test(Duration dur2, double val){
    dur2.divide(val);
    assert(dur2.get_value() - 11.8276 < 0.0001 && dur2.get_unit() == "h");
    cout << "divide duration test passed" << endl;
}

void divide_duration_test_2(Duration& dur2, double val){
    dur2.divide(val);
    assert(dur2.get_value() - 11.8276 < 0.0001 && dur2.get_unit() == "h");
    cout << "divide duration test with operator passed" << endl;
}

void text_duration_test(const Duration& dur1){
    assert(dur1.text() == "45.240000 h");
    cout << "text duration test passed" << endl;
}

void compare_duration_test(const Duration& dur1, const Duration& dur2, const Duration& dur3){
    assert(dur1.compare(dur2) == 1);
    cout << "compare duration test(dur1 > dur2) passed" << endl;
    assert(dur2.compare(dur1) == -1);
    cout << "compare duration test(dur1 < dur2) passed" << endl;
    assert(dur3.compare(dur1) == 0);
    cout << "compare duration test(dur1 = dur3) passed" << endl;

}

string menu(){
    return "1. Sehen Sie falls, die assert Funktionen gelingen\n"
           "2. Konvertieren Sie einige Zeitdauern\n"
           "3.Exit\n";
}
int main()
{
    cout << menu();
    bool quit = false;
    while(!quit){
        int option;
        cout << "Wahlen Sie eine Option:";
        cin >> option;
        switch(option){
            case 1:
            {
                Duration dur1(15.6, "h");
                Duration dur2(34.3, "h");
                Duration dur3(45.24, "h");
                double val = 2.9;
                add_duration_test(dur1, dur2);
                subtract_duration_test(dur1, dur2);

                scale_duration_test(dur1, val);
                scale_duration_test_2(dur1, val);
                divide_duration_test(dur2, val);
                divide_duration_test_2(dur2, val);

                text_duration_test(dur1);

                compare_duration_test(dur1, dur2, dur3);
                break;
            }

            case 2:
            {
                Duration dur1(56.3, "h");
                string unit_to_change;
                cout << "wahlen Sie eine Zeiteinheit(sec, min, h): ";
                cin >> unit_to_change;
                cout << dur1.text() << " = ";
                dur1.unit_conversion(unit_to_change);
                cout << dur1.text() << endl;
                break;
            }

            case 3:
                cout << "Das Program wird beendet";
                quit = true;
                break;

            default:
                cout << "Invalid Option! " << endl;
                break;
        }
    }
    return 0;
}

