#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[]){
    if(argc != 3){
        perror("Invalid number of arguments\n");
        return 1;
    }
    const char* filename = argv[2];
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
    int vorkommen = 0;
    char line[1000];
    while(fgets(line, sizeof(line), input_file)){
        char *word = strtok(line, " \n");
        while(word != NULL){
            if(strcmp(word, argv[1]) != 0)
                fprintf(output_file, "%s ", word);
            else
                vorkommen++;
            word = strtok(NULL, " \n");
        }
        fprintf(output_file, " \n");
    }

    fclose(input_file);
    fclose(output_file);
    if(rename("temp", filename) != 0){
        printf("cannot rename temp to %s\n", argv[1]);
        return 1;
    }
    printf("%d\n", vorkommen);

    return 0;

}