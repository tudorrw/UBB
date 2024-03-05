#include <iostream>

double schnellesPotenzieren(double x, int n){
    if(n == 0){   // wenn n gleich null is, dann hört der Algorithmus auf und gibt 1 zurück
        return 1;
    }
    if(n % 2 != 0){   //wenn der Exponent ungerade ist, dann wird eine Multiplikation zwischen die reelle Zahl x und den rekursiven Aufruf der Funktion durchgeführtm
        return x * schnellesPotenzieren(x, n-1);  //als Parameter ist der Exponent n mit 1 dekrementiert
    }
    return schnellesPotenzieren(x, n / 2) * schnellesPotenzieren(x, n / 2);  //ansonsten wird eine Multiplikation zwischen 2 rekursive Aufrufen
                                                                                    //die Exponenten wird durch 2 geteilt
}

void entgegenVorzeichen(int arr[], int n){
    int global_left = 0, global_right = 0;  //die Indexe, wo die längste aufeinanderfolgende Teilfolge beginnt und endet
    int left = 0, right;  //die Indexe, die die linke und rechte Seite während der Schleife halten
    int i = 0; //der Index, mit dem man in einer Schleife verwendet um die

    while(i < n - 1){
        if(arr[i] >= 0 and arr[i + 1] < 0 or arr[i] < 0 and arr[i + 1] >= 0){  //wird die if Bedingung geprüft
            if(!left)  //beginnt eine neue Teilfolge
                left = right = i; //die linke und rechte Seiten werden beide mit dem ursprünglichen Index gleich
            right++;  //wenn die Teilfolge weitergeht, wird der lokale rechte Index inkrementiert
        }
        else{  //die Bedingung ist falsch, so vergleicht die neue Teilfolge größer und die frühere größte Teilfolge
            if(global_right - global_left <= right - left){
                global_left = left, global_right = right;
            }
            left = 0;  //wird der lokal linker Index auf 0 zurückgesetzt
        }
        i++;
    }
    if(global_right - global_left <= right - left){
        //vergleicht die neue Teilfolge größer und die frühere größte Teilfolge
        global_left = left, global_right = right;
    }

    for(int poz = global_left; poz <= global_right; poz++){
        std::cout << arr[poz] << " ";  //die gefundene Teilfolge wird angezeigt
    }

}

int main(){
//    5a.
    double x;
    int n;    //x und n wird deklariert
    std::cout << "Geben Sie ein Basis (reelle Zahl) und ein Exponent(naturliche Zahl) ein:";
    std::cin >> x >> n;   //man liest x und n
    std::cout << x << " ^ " << n << " = " << schnellesPotenzieren(x, n) << "\n";

//    5b
    int *arr, nr_elements;  //arr - ein Pointer(die Adresse eines Speicherplatzes)
    std::cout << "Geben Sie ein Nummer von Elemente: ";
    std::cin >> nr_elements;
    arr = new int[nr_elements];  //wird genügend Speicherplatz(nr_elements) reserviert
    for(int poz = 0; poz < nr_elements; poz++){
        std::cin >> arr[poz];   //wird die Zahlen eingegeben
    }
    entgegenVorzeichen(arr, nr_elements);  //wird die Funktion aufgerufen
    //    Beispiel: 16: 5 -2 4 5 -8 9 -4 9 -4 -534 354 -58 5 -12 98 -4
    delete[] arr;  // löscht den Array, wird der Speicherplatz befreit

    return 0;
}