#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]){

    if(argc != 2){
        perror("Invalid number of arguments\n");
        return 1;
    }
    const char* filename = argv[1];
    FILE *input_file = fopen(filename, "rb");
    if(input_file == NULL){
        perror("cannot open input file\n");
        return 1;
    }

    FILE *output_file = fopen("temp", "wb");
    if(output_file == NULL){
        perror("cannot create output file temp\n");
        return 1;
    }

    int i = 0, byte;
    while((byte = fgetc(input_file))!= EOF) {
        if(i % 2 == 0){
            fputc(byte, output_file);
        }
        i++;
    }
    fclose(input_file);
    fclose(output_file);
    if(rename("temp", filename) != 0){
        printf("cannot rename temp to %s\n", argv[1]);
        return 1;
    }
    return 0;
}