#include <stdio.h>
#include <string.h>
int main(int argc, char *argv[]){
    if(argc != 4){
        perror("invalid numer of arguments!\n");
        return 1;
    }

    const char* word1 = argv[1];
    const char* word2 = argv[2];
    const char* filename = argv[3];
    FILE *file = fopen(filename, "r");
    if(file == NULL){
        perror("cannot open file\n");
        return 1;
    }

    printf("%s, %s ", word1, word2);
    char line[1000];
    while(fgets(line, sizeof(line), file)){
        char *word = strtok(line, " \n");
        while(word != NULL){
            if(strcmp(word, word1) == 0){
                printf("%s ", word2);
            }
            else{
                printf("%s ", word);
            }
            word = strtok(NULL, " \n");
        }
        printf("\n");
    }
    fclose(file);
    return 0;
}