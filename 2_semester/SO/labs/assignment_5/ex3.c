#include <stdio.h>
#include <string.h>
int main(int argc, char *argv[]){

    if(argc != 2){
        perror("Invalid number of arguments\n");
        return 1;
    }
    const char* filename = argv[1];
    FILE *file = fopen(filename, "r");
    if(file == NULL){
        perror("cannot open file\n");
        return 1;
    }

    int words = 0;
    char c;
    while((c = fgetc(file)) != EOF){
        if(c == ' ' || c == '\n'){
            words++;
        }
    }
    printf("%d words in the file %s\n", words, filename);

    fseek(file, 0L, SEEK_SET);

    words = 0;
    char line[1000];
    while(fgets(line, 1000, file)){
        char *word = strtok(line, " \n");
        while(word != NULL){
            words++;
            word = strtok(NULL, " \n");
        }
    }
    printf("%d words in the file %s\n", words, filename);

    return 0;
}