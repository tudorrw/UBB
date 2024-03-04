#include "Graph.h"

Graph::Graph(const std::string &filename) {
    std::ifstream file;
    file.open(filename, std::ios::in);

    std::string stadt1, stadt2;
    int gewicht;

    this->head = nullptr;
    while(file >> stadt1 >> stadt2 >> gewicht){
        Node *newNode = new Node();
        newNode->city1 = stadt1;
        newNode->city2 = stadt2;
        newNode->weight = gewicht;
        if(this->head == nullptr)
            this->head = newNode;
        else{
            newNode->next = this->head;
            this->head = newNode;
        }
    }
}

void Graph::shortestPath(std::string knoten1, std::string knoten2) {
    std::vector<std::string> list_of_cities = listOfCities();
    std::map<std::string, int> key_node_cities;
    for(int i = 0; i < list_of_cities.size(); i++){
        key_node_cities.insert({list_of_cities[i], i});
    }

//    for(auto it: key_node_cities){
//        std::cout << it.first << " " << it.second << std::endl;
//    }
    int V = list_of_cities.size();
    std::vector<int> dist(V, INT_MAX);
    dist[key_node_cities[knoten1]] = 0;
    std::vector<std::string> path;
    path.push_back(knoten1);
    Node *current;
    for(int i = 0; i < V - 1; i++){
        current = this->head;
        while(current != nullptr){
            if(dist[key_node_cities[current->city1]] + current->weight < dist[key_node_cities[current->city2]] && dist[key_node_cities[current->city1]] != INT_MAX){
                dist[key_node_cities[current->city2]] = dist[key_node_cities[current->city1]] + current->weight;
//
//                if(current->city1 == knoten1){
//                    path.push_back(current->city2);
//                }
            }
            if(dist[key_node_cities[current->city2]] + current->weight < dist[key_node_cities[current->city1]] && dist[key_node_cities[current->city2]] != INT_MAX){
                dist[key_node_cities[current->city1]] = dist[key_node_cities[current->city2]] + current->weight;
            }
            current = current->next;
        }
    }

    current = this->head;
    while(current != nullptr){
        if(dist[key_node_cities[current->city1]] + current->weight < dist[key_node_cities[current->city2]] && dist[key_node_cities[current->city1]] != INT_MAX){
            std::cout << "Negative cycle" << std::endl;
            return;
        }
        if(dist[key_node_cities[current->city2]] + current->weight < dist[key_node_cities[current->city1]] && dist[key_node_cities[current->city2]] != INT_MAX){
            std::cout << "Negative cycle" << std::endl;
            return;
        }

        current = current->next;
    }


    std::cout << "Cost: " << dist[key_node_cities[knoten2]] << std::endl;

}

std::vector<std::string> Graph::listOfCities(){
    std::vector<std::string> list_of_cities;
    Node *current = this->head;
    while(current != nullptr){
        bool ok1 = true, ok2 = true;
        for(auto it : list_of_cities){
            if(current->city1 == it){
                ok1 = false;
            }
            if(current->city2 == it){
                ok2 = false;
            }
        }
        if(ok1){
            list_of_cities.push_back(current->city1);
        }
        if(ok2){
            list_of_cities.push_back(current->city2);
        }
        current = current->next;
    }
    return list_of_cities;

}



void Graph::print_graph(){
    Node *current = this->head;
    while(current != nullptr){
        std::cout << current->city1 << " " << current->city2 << " " << current->weight << std::endl;
        current = current->next;
    }
}