#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>

int main(void){
    int c2s[2], s2c[2]; //client to server pipe, server to client pipe
    int temperature, modified_temperature = -1;
    char scale[256];

    if(pipe(c2s) == -1){
        perror("An error occured with opening the \"parent to child\"pipe\n");
        return 1;
    }

    if(pipe(s2c) == -1){
        perror("An error occured with opening the \"child to parent\"pipe\n");
        return 1;
    }   
    
    pid_t pid = fork();
    if(pid < 0){
        perror("Fork failed\n");
        return 1;
    }
    else if(pid == 0){
        close(c2s[1]);
        close(s2c[0]);
        if (read(c2s[0], &temperature, sizeof(int)) == -1) {
            perror("Error reading temperature from pipe\n");
            exit(1);
        }

        read(c2s[0], scale, sizeof(scale));

        if(!strcmp(scale, "Celsius")){
            modified_temperature = temperature * 9 / 5  + 32;
            printf("%d\n", modified_temperature);
        }

        else if(!strcmp(scale, "Fahrenheit")){
            modified_temperature = (temperature - 32) * 5 / 9;
            printf("%d\n", modified_temperature);
        }
        else{
            printf("Ungultige Eingabe\n");
        }
        write(s2c[1], &modified_temperature, sizeof(int));

        close(c2s[0]);
        close(s2c[1]);

        exit(1);

    }
    else{

        close(c2s[0]);
        close(s2c[1]);
        
        printf("Temperaturwert: ");
        if (scanf("%d", &temperature) != 1) {
            printf("Bitte geben Sie einen gültigen Wert ein.\n");
            close(c2s[1]);
            close(s2c[0]);
            wait(NULL);
            return 1;
        }

        printf("Skala: ");
        scanf("%s", scale);

        if (write(c2s[1], &temperature, sizeof(int)) == -1) {
            perror("Error writing temperature to pipe\n");
            close(c2s[1]);
            close(s2c[0]);
            wait(NULL);
            return 1;
        }

        write(c2s[1], scale, sizeof(scale));

        read(s2c[0], &modified_temperature, sizeof(int));

        if(modified_temperature != -1){
            if(!strcmp(scale, "Celsius"))
                printf("Empfang vom Server: %d Fahrenheit\n", modified_temperature);
            if(!strcmp(scale, "Fahrenheit"))
                printf("Empfang vom Server: %d Celsius\n", modified_temperature);
        }
        else{
            printf("Bitte geben Sie einen gültigen Wert ein.\n");
        }

        close(c2s[1]);
        close(s2c[0]);
        wait(NULL);
    }
    return 0;
}