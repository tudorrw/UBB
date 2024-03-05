#include <iostream>
#include <exception>
#include "L3_Ciobanu_Tudor_711_DSM.h"

/**
 * erste Konstruktor, erstellt ein DSM Object mit einem leeren string Array und eine mit 0 Werten Matrix
 * @tparam weightType Vorlagenparameter, der den Matrixtyp ersetzt
 * @param elementCount die Anzahl der Elemente den DSM
 */
template<typename weightType>
DSM<weightType>::DSM(int elementCount) {
    this->elementCount = elementCount;
    elementNames = new std::string [elementCount];

    adjacentMatrix = new weightType* [elementCount];
    for(int row = 0; row < elementCount; row++){
        adjacentMatrix[row] = new weightType [elementCount];
        for(int column = 0; column < elementCount; column++){
            adjacentMatrix[row][column] = 0;
        }
    }
}

/**
 * zweite Konstruktor, erstellt sowohl einen string Array mit den Namen den Elementen
 * als auch eine 0 Werten Matrix
 * @tparam weightType
 * @param elemNames dynamische string Array
 * @param elementCount die Anzahl der Elemente den DSM
 */
template<typename weightType>
DSM<weightType>::DSM(std::string* elemNames ,int elementCount) {
    this->elementCount = elementCount;
    elementNames = new std::string [elementCount];
    for(int index = 0; index < elementCount; index++){
        elementNames[index] = elemNames[index];
    }

    adjacentMatrix = new weightType* [elementCount];
    for(int row = 0; row < elementCount; row++){
        adjacentMatrix[row] = new weightType [elementCount];
        for(int column = 0; column < elementCount; column++){
            adjacentMatrix[row][column] = 0;
        }
    }
}

/**
 * Kopierkonstruktor, erstellt eine Kopie einer anderen Instanz des DSM-Typs
 * @tparam weightType Vorlagenparameter, der den Matrixtyp ersetzt
 * @param other andere Instanz des DSM-Typs
 */
template<typename weightType>
DSM<weightType>::DSM(const DSM& other) {
    elementCount = other.elementCount;
    elementNames = new std::string [elementCount];

    for(int index = 0; index < elementCount; index++){
        elementNames[index] = other.elementNames[index];
    }

    adjacentMatrix = new weightType* [elementCount];
    for(int row = 0; row < elementCount; row++){
        adjacentMatrix[row] = new weightType [elementCount];
        for(int column = 0; column < elementCount; column++){
            adjacentMatrix[row][column] = other.adjacentMatrix[row][column];
        }
    }
}

/**
 * gibt die index der Element in der string Array zurück
 * @tparam weightType
 * @param element
 * @return index
 */
template<typename weightType>
int DSM<weightType>::getElemIndex(const std::string& element) {
    for(int index = 0; index < elementCount; index++){
        if(elementNames[index] == element){
            return index;
        }
    }
    return -1;
}

/**
 * geben wir eine neue Element in der dynamisches string Array und machen wir Platz in der
 * 2-dimensionales Array und fügen wir noch ein Spalte und eine Zeile mit 0 Werte hinzu.
 *  allokieren wir zuerst ein dynamische temporare Array und eine dynamische temporare Matrix.
 *  Wir weisen die gemeinsamen Werte der alten Instanz zu und geben danach das Array
 *  und die alte Matrix frei, um ihnen die neuen Werte zuweisen zu können
 * @tparam weightType
 */
template<typename weightType>
void DSM<weightType>::resize(const std::string& elem) {
    elementCount++;
    auto* tempElementNames = new std::string [elementCount];
    for(int index = 0; index < elementCount - 1; index++){
        tempElementNames[index] = elementNames[index];
    }
    tempElementNames[elementCount-1] = elem;

    int **tempMatrix = new weightType *[elementCount];
    for(int row = 0; row < elementCount; row++){
        tempMatrix[row] = new weightType [elementCount];
        for(int column = 0; column < elementCount; column++){
            if(column == elementCount-1 || row == elementCount-1){
                tempMatrix[row][column] = 0;
            }
            else{
                tempMatrix[row][column] = adjacentMatrix[row][column];
            }
        }
    }
    delete[] elementNames;
    elementNames = tempElementNames;

    for(int row = 0; row < elementCount - 1; row++){
        delete[] adjacentMatrix[row];
    }
    delete[] adjacentMatrix;
    adjacentMatrix = tempMatrix;
}

/**
 * gibt die Anzahl der Größe der Matrix zurück
 * @tparam weightType
 * @return elementCount
 */
template<typename weightType>
int DSM<weightType>::size() const {return elementCount; }

/**
 * gibt die Name des Elementes ane der gegebenen Index der string Array
 * @tparam weightType
 * @param index
 * @return elementNames[index] else werden eine Ausnahme verursacht wenn die element ungültig ist
 */
template<typename weightType>
std::string DSM<weightType>::getName(int index) {
    if(0 <= index && index < elementCount){
        return elementNames[index];
    }
    throw std::out_of_range("Invalid Index");
}

/**
 *
 * @tparam weightType
 * @param index
 * @param elementName
 */
template<typename weightType>
void DSM<weightType>::setElementName(int index, std::string elementName) {
    if(0 <= index && index < elementCount){
        elementNames[index] = elementName;
        return;
    }
    throw std::out_of_range("Invalid Index");
}

/**
 *
 * @tparam weightType
 * @param fromElement
 * @param toElement
 * @param weight
 */
template<typename weightType>
void DSM<weightType>::addLink(const std::string& fromElement, const std::string& toElement, weightType weight){
    if(weight < 0 || weight > 5){
        throw std::invalid_argument("Invalid weight");
    }
    if(fromElement == toElement){
        throw std::invalid_argument("The string should be different");
    }

    int fromIndex = getElemIndex(fromElement);
    int toIndex = getElemIndex(toElement);
    if(fromIndex == -1 || toIndex == -1){
        if(fromIndex == -1){
            resize(fromElement);
            fromIndex = elementCount - 1;
        }
        if(toIndex == -1){
            resize(toElement);
            toIndex = elementCount - 1;
        }
    }

    adjacentMatrix[fromIndex][toIndex] = weight;
}

/**
 *
 * @tparam weightType
 * @param fromElement
 * @param toElement
 */
template<typename weightType>
void DSM<weightType>::deleteLink(const std::string& fromElement, const std::string& toElement) {
    int fromIndex = getElemIndex(fromElement);
    int toIndex = getElemIndex(toElement);

    if(fromIndex != -1 && toIndex != -1){
        adjacentMatrix[fromIndex][toIndex] = 0;
        return;
    }
    throw std::invalid_argument("At least one parameter doesn't correspond");
}

/**
 *
 * @tparam weightType
 * @param fromElement
 * @param toElement
 * @return
 */
template<typename weightType>
bool DSM<weightType>::hasLink(const std::string& fromElement, const std::string& toElement) {
    int fromIndex = getElemIndex(fromElement);
    int toIndex = getElemIndex(toElement);

    if(fromIndex != -1 && toIndex != -1){
        return adjacentMatrix[fromIndex][toIndex] > 0;
    }
    throw std::invalid_argument("At least one parameter doesn't correspond");
}

/**
 *
 * @tparam weightType
 * @param fromElement
 * @param toElement
 * @return
 */
template<typename weightType>
weightType DSM<weightType>::linkWeight(const std::string& fromElement, const std::string& toElement) {

    int fromIndex = getElemIndex(fromElement);
    int toIndex = getElemIndex(toElement);
    if(fromIndex != -1 && toIndex != -1){
        return adjacentMatrix[fromIndex][toIndex];
    }
    throw std::invalid_argument("At least one parameter doesn't correspond");
}

/**
 *
 * @tparam weightType
 * @param elementName
 * @return
 */
template<typename weightType>
int DSM<weightType>::countToLinks(const std::string &elementName) {
    int toIndex = getElemIndex(elementName);
    if(toIndex == -1){
        throw std::invalid_argument("The parameter doesn't correspond");
    }
    int toLinks = 0;
    for(int column = 0; column < elementCount; column++){
        if(adjacentMatrix[toIndex][column]){
            toLinks++;
        }
    }
    return toLinks;
}

/**
 *
 * @tparam weightType
 * @param elementName
 * @return
 */
template<typename weightType>
int DSM<weightType>::countFromLinks(const std::string &elementName) {
    int fromIndex = getElemIndex(elementName);
    if(fromIndex == -1){
        throw std::invalid_argument("The parameter doesn't correspond");
    }
    int fromLinks = 0;
    for(int row = 0; row < elementCount; row++){
        if(adjacentMatrix[row][fromIndex]){
            fromLinks++;
        }
    }
    return fromLinks;
}

/**
 *
 * @tparam weightType
 * @return
 */
template<typename weightType>
int DSM<weightType>::countAllLinks() {
    int allLinks = 0;
    for(int i = 0; i < elementCount; i++){
        for(int j = 0; j < elementCount; j++){
            if(adjacentMatrix[i][j]){
                allLinks++;
            }
        }
    }
    return allLinks;
}

/**
 *
 * @tparam weightType
 */
template<typename weightType>
void DSM<weightType>::print() const {
    std::cout << "\t";
    for(int column = 0; column < elementCount; column++){
        std::cout << elementNames[column] << "\t";
    }
    std::cout << std::endl;
    for(int row = 0; row < elementCount; row++){
        std::cout << elementNames[row] << "\t";
        for(int column = 0; column < elementCount; column++){
            std::cout << adjacentMatrix[row][column] << "\t";
        }
        std::cout << std::endl;
    }
}

//Destruktor
/**
 * wird die Destructor aufgerufen und danach der Speicherplatz befreit
 * @tparam weightType
 */
template<typename weightType>
DSM<weightType>::~DSM(){
    for(int row = 0; row < elementCount; row++){
        delete[] adjacentMatrix[row];
    }
    delete[] adjacentMatrix;
    delete[] elementNames;
}
