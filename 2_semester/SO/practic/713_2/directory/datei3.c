#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]){

    if(argc != 2){
        perror("Invalid number of arguments\n");
        return 1;
    }
    printf("Hello world\n");
    return 0;
}