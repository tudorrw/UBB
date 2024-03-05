#include <stdio.h>

#define MAX_LINE_LENGTH 1000
#define NEWLINE '\n'
int main(int argc, char *argv[]){

    if(argc == 1){
        perror("Error: Insufficient number of arguments.\n");
        return 1;
    }
    const char* filename = argv[1];
    FILE *file = fopen(filename, "r");
    if(file == NULL){
        perror("File not found!\n");
        return 1;
    }

    char line[MAX_LINE_LENGTH];
    while (fgets(line, sizeof(line), file)){
        printf("%s\n", line);  
    }

    // char c;
    // while(c = fgetc(file) != EOF) {
    //     if(c == '\n')
    //         printf(NEWLINE);
    //     else 
    //         printf("%c", c);
    // }

    fclose(file);
    return 0;
}