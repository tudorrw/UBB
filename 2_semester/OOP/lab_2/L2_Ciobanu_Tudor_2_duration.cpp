#include "L2_Ciobanu_Tudor_2_duration.h"
#include <iostream>
using namespace std;

//Die Implementierung der Klasse Duration
//der Konstruktor wird verwendet, um eine neue Instanz einer Klasse anzulegen
Duration::Duration(double val, string un){
    value = val;
    unit = std::move(un);
}
//getters für die Klassenattribute
double Duration::get_value() const{ return value; }
string Duration::get_unit() const{ return unit; }



//add Methode von Typ 'Duration'
Duration Duration::add(const Duration &other) const {
    //soll ein neues Duration-Objekt zurückgeben um sowohl den korrekten Zahlenwert als auch die korrekte Einheit der Zeitdauer widerspiegelt
    return {value + other.value, unit};

}
//Überladung von + für die Addition zweier Durations
Duration Duration::operator+(const Duration &other) const {
    //gibt auch eine Instanz von Duration Klasse zurück
    return {value + other.value, unit};
}

//subtract Methode von Typ 'Duration'
Duration Duration::subtract(const Duration &other) const {
    return {value - other.value, unit};
}
// Überladung von - für die Differenz zweier Durations
Duration Duration::operator-(const Duration &other) const {
    return {value - other.value, unit};
}
//Skalierung Methode von Typ void. Ändert sie nur die Größe der Instanz.
void Duration::scale(double val) {
    value *= val;
}
// Überladung von * für die Skalierung einer Duration
void Duration::operator*=(double val) {
    value *= val;
}

//Division Methode von Typ void. Ändert sie nur die Größe der Instanz.
void Duration::divide(double val){
    value /= val;
}

// Überladung von / für die Division einer Duration
void Duration::operator/=(double val) {
    value /= val;
}
//Text Methode gibt die Dauer mit Einheit als Zeichenkette zurück
string Duration::text() const{
    return to_string(value) + " " + unit;
}

int Duration::compare(const Duration &other) const {
    if(abs(value - other.value) < 0.0001){
        return 0;
    } else if(value < other.value){
        return -1;
    } else{
        return 1;
    }
}
//eine Konvertierung Methode von Zeitdauern (verwendet Einheiten: sec, min, h)
void Duration::unit_conversion(const string& unit_to_change) {
    if(unit == "min" && unit_to_change == "sec" || unit == "h" && unit_to_change == "min"){
        scale(60);
    }
    else if(unit == "min" && unit_to_change == "h" || unit == "sec" && unit_to_change == "min"){
        divide(60);
    }
    else if(unit == "sec" && unit_to_change == "h"){
        divide(3600);
    }
    else if(unit == "h" && unit_to_change == "sec"){
        scale(3600);
    }
//    die aktuelle Einheit wird mit dem gegebenen Einheit als Parameter ändert
    unit = unit_to_change;
}
