#ifndef L2_Ciobanu_Tudor_2_duration_H
#define L2_Ciobanu_Tudor_2_duration_H

#include <iostream>
using namespace std;
class Duration {
//   2 privaten Datenkomponenten: value(Fliesskommazahl für die Größe) und unit(Einheit)
//    Felder können nur innerhalb der Klasse zugegriffen werden
private:
    double value;
    string unit;
public:
//    Klassendefinitionen und Funktionen
//    Methoden können von überall zugegriffen werden
    Duration(double val, string unit);  // der Konstruktor
    //Zugriffsmethoden
    double get_value() const;
    string get_unit() const;

    Duration add(const Duration& other) const;
    Duration operator+(const Duration& other) const;

    Duration subtract(const Duration& other) const;
    Duration operator-(const Duration& other) const;

    void scale(double val);
    void operator*=(double val);

    void divide(double val);
    void operator/=(double val);

    string text() const;

    int compare(const Duration& other) const;

    void unit_conversion(const string& unit_to_change);
};

#endif