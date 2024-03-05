#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
int is_number(const char *str){
    for(int i = 0; str[i] != '\0'; i++){
        if(!isdigit(str[i])){
            return 0;
        }
    }
    return 1;
}

int main(int argc, char *argv[]){
    if(argc != 2){
        perror("Invalid number of arguments\n");
        return 1;
    }
    const char* filename = argv[1];
    FILE *input_file = fopen(filename, "r");
    if(input_file == NULL){
        perror("cannot open input file\n");
        return 1;
    }

    FILE *output_file = fopen("temp", "w");
    if(output_file == NULL){
        perror("cannot create output file\n");
        return 1;
    }

    char line[1000];
    while(fgets(line, sizeof(line), input_file)){
        char *word = strtok(line, "\n");
        while(word != NULL){
            if(is_number(word)){
                int num = atoi(word);
                printf("%d\n", num);
            }
            word = strtok(NULL, " \n");
        }
    }
    fclose(input_file);
    fclose(output_file);
    return 0;
}