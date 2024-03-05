#ifndef LABOR_3_L3_CIOBANU_TUDOR_711_DSM_H
#define LABOR_3_L3_CIOBANU_TUDOR_711_DSM_H
template<typename weightType>
class DSM{
private:
    int elementCount{};
    std::string* elementNames;
    weightType** adjacentMatrix;

    int getElemIndex(const std::string& element);

public:
    explicit DSM(int elementCount);

    DSM(std::string* elementNames, int elementCount);

    DSM(const DSM& other);

    void resize(const std::string& elem);

    int size() const;

    std::string getName(int index);

    void setElementName(int index, std::string elementName);

    void addLink(const std::string& fromElement, const std::string& toElement, weightType weight);

    void deleteLink(const std::string& fromElement, const std::string& toElement);

    bool hasLink(const std::string& fromElement, const std::string& toElement);

    weightType linkWeight(const std::string& fromElement, const std::string& toElement);

    int countToLinks(const std::string& elementName);

    int countFromLinks(const std::string& elementName);

    int countAllLinks();

    void print() const;

    ~DSM();

};

#endif //LABOR_3_L3_CIOBANU_TUDOR_711_DSM_H
